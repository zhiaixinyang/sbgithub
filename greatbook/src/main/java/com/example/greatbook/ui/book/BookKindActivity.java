package com.example.greatbook.ui.book;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.NewBaseActivity;
import com.example.greatbook.base.OnItemClickListenerInAdapter;
import com.example.greatbook.model.BookKindBean;
import com.example.greatbook.model.BookKindListBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.model.leancloud.LBookKindBean;
import com.example.greatbook.presenter.BookKindPresenter;
import com.example.greatbook.presenter.contract.BookKindContract;
import com.example.greatbook.ui.book.adapter.BookKindAdapter;
import com.example.greatbook.ui.main.activity.MainActivity;
import com.example.greatbook.utils.FileUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/20.
 * 此类为各种类下的具体书籍展示
 */

public class BookKindActivity extends NewBaseActivity<BookKindPresenter> implements BookKindContract.View,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rlv_bookkind) RecyclerView rlvBookKind;
    @BindView(R.id.srf_bookkind) SwipeRefreshLayout srfBookLind;
    @BindView(R.id.tv_title_text) TextView tvTitleText;
    @BindView(R.id.btn_back) TextView btnBack;


    private BookKindAdapter bookKindAdapter=null;

    private BookKindPresenter bookKindPresenter=null;

    private BookKindListBean bookKindListBean=null;
    private String title;
    private String path;


    @Override
    public int getLayoutId() {
        return R.layout.activity_book_kind;
    }
    @Override
    public void init() {

    }



    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void initDatasSuccess(List<LBookKindBean> datas) {

    }

    @Override
    public void initDatasError(String error) {

    }

    @Override
    public void showLoading() {
        srfBookLind.setRefreshing(true);
    }

    @Override
    public void showLoaded() {
        srfBookLind.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (StringUtils.isEmpty(path)){
            ToastUtil.toastShort("数据获取异常。");
        }else{
            //默认开始传0，此变量只会影响武侠类的解析模式
            bookKindPresenter.setOnLoadBookKind(path,0);
        }
    }

    private void back(){
        Intent back=new Intent(BookKindActivity.this, MainActivity.class);
        startActivity(back);
        finish();
    }

}
