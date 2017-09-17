package com.example.greatbook.middle.presenter.contract;

import android.content.Context;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.middle.model.LocalRecordRLV;
import com.example.greatbook.middle.model.MainMenuItemBean;

import java.util.List;

/**
 * Created by MDove on 2017/8/12.
 */

public interface MiddleLocalAddContract {
    interface Presenter extends BasePresenter<MiddleLocalAddContract.View> {
        void initLocalRecord();

        void initMenu(Context context);
    }

    interface View extends BaseView {
        void initLocalRecordError(String error);

        void initLocalRecordSuc(List<LocalRecordRLV> records);

        void showMenu(List<MainMenuItemBean> menuData);
    }
}
