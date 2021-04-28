package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.utils.LogUtils;

public class TestActivity extends BaseActivity {

    private MainPresenter newPresenter;
    private final static String NEWS_ID = "news_id";

    public static void launchActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, TestActivity.class);
        intent.putExtra(NEWS_ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (getIntent() != null) {
            String id = getIntent().getStringExtra("NEWS_ID");
            
        }
    }

    @Override
    protected void initPresenter() {
        newPresenter = new MainPresenter(new DataManager());
    }

    private void loadNewsDetail(String id) {
        newPresenter.loadNewsDetail(id, new NetCallBack<SearchBo>() {
            @Override
            public void onLoadSuccess(SearchBo resultList) {

            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }
}
