package com.example.greatbook.local.presenter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.local.fragment.MiddleMainFragment;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.local.model.MainMenuItemBean;
import com.example.greatbook.local.presenter.contract.MiddleLocalAddContract;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by MDove on 2017/8/12.
 */

public class MiddleLocalAddPresenter extends RxPresenter<MiddleLocalAddContract.View> implements MiddleLocalAddContract.Presenter {

    @Override
    public void initMenu(Context context) {
        List<MainMenuItemBean> menuData = new ArrayList<>();

        MainMenuItemBean chatSelf = new MainMenuItemBean();
        chatSelf.bgColor = ContextCompat.getColor(context, R.color.purple);
        chatSelf.inColor = ContextCompat.getColor(context, R.color.white);
        chatSelf.outColor = ContextCompat.getColor(context, R.color.black);
        chatSelf.inText = "自";
        chatSelf.outText = "自言自语";
        chatSelf.menuType = MiddleMainFragment.CHAT_SELF;
        menuData.add(chatSelf);

        MainMenuItemBean myAll = new MainMenuItemBean();
        myAll.bgColor = ContextCompat.getColor(context, R.color.blue);
        myAll.inColor = ContextCompat.getColor(context, R.color.white);
        myAll.outColor = ContextCompat.getColor(context, R.color.black);
        myAll.inText = "段";
        myAll.outText = "我的段子库";
        myAll.menuType = MiddleMainFragment.MY_ALL_CONTENT;
        menuData.add(myAll);

        MainMenuItemBean myGroup = new MainMenuItemBean();
        myGroup.bgColor = ContextCompat.getColor(context, R.color.red);
        myGroup.inColor = ContextCompat.getColor(context, R.color.white);
        myGroup.outColor = ContextCompat.getColor(context, R.color.black);
        myGroup.inText = "藏";
        myGroup.outText = "我的收藏";
        myGroup.menuType = MiddleMainFragment.MY_COLLECTS;
        menuData.add(myGroup);


        MainMenuItemBean myCooperateTopic = new MainMenuItemBean();
        myCooperateTopic.bgColor = ContextCompat.getColor(context, R.color.orange);
        myCooperateTopic.inColor = ContextCompat.getColor(context, R.color.white);
        myCooperateTopic.outColor = ContextCompat.getColor(context, R.color.black);
        myCooperateTopic.inText = "精";
        myCooperateTopic.outText = "精雕细琢";
        myCooperateTopic.menuType = MiddleMainFragment.MY_COOPATER_TOPIC;
        menuData.add(myCooperateTopic);

        MainMenuItemBean myPlan = new MainMenuItemBean();
        myPlan.bgColor = ContextCompat.getColor(context, R.color.pink);
        myPlan.inColor = ContextCompat.getColor(context, R.color.white);
        myPlan.outColor = ContextCompat.getColor(context, R.color.black);
        myPlan.inText = "标";
        myPlan.outText = "我的目标";
        myPlan.menuType = MiddleMainFragment.MY_PLAN;
        menuData.add(myPlan);

        mView.showMenu(menuData);
    }
}
