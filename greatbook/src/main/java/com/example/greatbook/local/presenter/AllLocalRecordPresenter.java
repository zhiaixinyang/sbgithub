package com.example.greatbook.local.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.App;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.local.presenter.contract.AllLocalRecordContract;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MDove on 2017/8/13.
 */

public class AllLocalRecordPresenter extends RxPresenter<AllLocalRecordContract.View> implements AllLocalRecordContract.Presenter {
    private LocalRecordDao localRecordDao;

    public AllLocalRecordPresenter(AllLocalRecordContract.View view) {
        mView = view;
        localRecordDao = App.getDaoSession().getLocalRecordDao();
    }

    @Override
    public void initLocalRecords() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<LocalRecordRLV>>() {
            @Override
            public void call(Subscriber<? super List<LocalRecordRLV>> subscriber) {
                List<LocalRecord> records = localRecordDao.loadAll();
                List<LocalRecordRLV> data = new ArrayList<>();

                if (records != null && !records.isEmpty()) {
                    for (LocalRecord local : records) {
                        LocalRecordRLV localRLV = new LocalRecordRLV();
                        localRLV.belongId = local.getBelongId();
                        localRLV.content = local.getContent();
                        localRLV.groupId = Long.valueOf(local.getGroupId());
                        localRLV.groupTitle = local.getGroupTitle();
                        localRLV.id = local.getId();
                        localRLV.title = local.getTitle();
                        localRLV.time = local.getTimeDate();
                        localRLV.type = local.getType();

                        data.add(localRLV);
                    }
                }
                subscriber.onNext(data);
            }
        }).compose(RxUtil.<List<LocalRecordRLV>>rxSchedulerHelper())
                .subscribe(new Action1<List<LocalRecordRLV>>() {
                    @Override
                    public void call(List<LocalRecordRLV> records) {
                        mView.initLocalRecords(records);
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void deleteLocalRecord(LocalRecordRLV localRecordRLV) {
        localRecordDao.deleteByKey(localRecordRLV.id);
        mView.deleteLocalGroupReturn("删除成功");
        deleteLocalRecordToNet(localRecordRLV);
    }

    @Override
    public void deleteLocalRecordToNet(final LocalRecordRLV localRecord) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                AVQuery<LLocalRecord> query = AVQuery.getQuery(LLocalRecord.class);
                query.whereEqualTo("objectId", localRecord.belongId);
                query.findInBackground(new FindCallback<LLocalRecord>() {
                    @Override
                    public void done(List<LLocalRecord> list, AVException e) {
                        if (e == null && !list.isEmpty()) {
                            list.get(0).deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        subscriber.onNext("服务器删除成功");
                                    } else {
                                        subscriber.onNext("删除失败：" + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }).compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mView.deleteLocalGroupToNetReturn(s);
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void updateLocalRecord(LocalRecordRLV localRecord, String title, String content) {
        LocalRecord record = new LocalRecord();
        record.setId(localRecord.id);
        record.setContent(content);
        record.setTitle(title);
        localRecordDao.update(record);
        mView.updateLocalRecordReturn("修改成功");
        updateLocalRecordToNet(record);
    }

    @Override
    public void updateLocalRecordToNet(final LocalRecord localRecord) {
        if (NetUtil.isNetworkAvailable()) {
            Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(final Subscriber<? super String> subscriber) {
                    AVQuery<LLocalRecord> query = AVQuery.getQuery(LLocalRecord.class);
                    query.whereEqualTo("belongLocalId", localRecord.getId());
                    query.findInBackground(new FindCallback<LLocalRecord>() {
                        @Override
                        public void done(List<LLocalRecord> list, AVException e) {
                            if (e == null && !list.isEmpty()) {
                                LLocalRecord recordL = list.get(0);
                                AVObject lLocalRecord = AVObject.createWithoutData("LLocalRecord", recordL.getObjectId());

                                lLocalRecord.put("title", localRecord.getTitle());
                                lLocalRecord.put("content", localRecord.getContent());
                                lLocalRecord.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            subscriber.onNext("服务器内容修改成功");
                                        } else {
                                            subscriber.onNext("服务器修改失败：" + e.getMessage());
                                        }
                                    }
                                });
                            } else {
                                subscriber.onNext("服务器修改失败：" + e.getMessage());
                            }
                        }
                    });
                }
            }).compose(RxUtil.<String>rxSchedulerHelper())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            mView.deleteLocalGroupToNetReturn(s);
                        }
                    });
            addSubscrebe(subscription);
        }
    }
}
