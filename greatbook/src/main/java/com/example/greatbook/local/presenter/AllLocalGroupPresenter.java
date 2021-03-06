package com.example.greatbook.local.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.local.presenter.contract.AllLocalGroupContract;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.utils.FileUtils;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.RxUtil;
import com.example.greatbook.utils.StringUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MDove on 2017/8/16.
 */

public class AllLocalGroupPresenter extends RxPresenter<AllLocalGroupContract.View> implements AllLocalGroupContract.Presenter {
    private LocalGroupDao localGroupDao;
    private long id;

    public AllLocalGroupPresenter(AllLocalGroupContract.View view) {
        mView = view;
        localGroupDao = App.getDaoSession().getLocalGroupDao();
    }

    @Override
    public void addLocalGroup(LocalGroup localGroup) {
        //统计累计书写字数
        MySharedPreferences.putCurWords(StringUtils.isEmpty(localGroup.content) ? 0 : localGroup.content.length());
        MySharedPreferences.putCurWords(StringUtils.isEmpty(localGroup.title) ? 0 : localGroup.title.length());
        id = localGroupDao.insert(localGroup);
        if (id > 0) {
            mView.addLocalGroupSuc("新建成功");
            addLocalGroupToNet(localGroup);
        } else {
            mView.addLocalGroupErr("新建失败，请重试");
        }
    }

    @Override
    public void addLocalGroupToNet(final LocalGroup localGroup) {
        if (NetUtil.isNetworkAvailable()) {
            Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(final Subscriber<? super String> subscriber) {
                    LLocalGroup lLocalGroup = new LLocalGroup();
                    lLocalGroup.setBelongId(localGroup.belongId);
                    lLocalGroup.setAttentionNum(0);
                    lLocalGroup.setContent(localGroup.content);
                    lLocalGroup.setGroupId(localGroup.id);
                    lLocalGroup.setGroupTitle(localGroup.title);
                    lLocalGroup.setUserd(localGroup.isUserd);
                    lLocalGroup.setTime(localGroup.time);
                    if (!StringUtils.isEmpty(localGroup.groupPhotoPath)) {
                        lLocalGroup.setGroupPhoto(new AVFile(FileUtils.getFileName(localGroup.groupPhotoPath),
                                FileUtils.getByteFromPath(localGroup.groupPhotoPath)));
                    } else {
                        lLocalGroup.setGroupPhoto(null);
                    }
                    lLocalGroup.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                subscriber.onNext("服务器上传成功");
                            } else {
                                subscriber.onNext("服务器上传失败：" + e.getMessage());
                            }
                        }
                    });
                }
            }).compose(RxUtil.<String>rxSchedulerHelper())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            mView.addLocalGroupToNetReturn(s);
                        }
                    });
            addSubscrebe(subscription);
        } else {
            mView.addLocalGroupToNetReturn("无网络");
        }
    }

}
