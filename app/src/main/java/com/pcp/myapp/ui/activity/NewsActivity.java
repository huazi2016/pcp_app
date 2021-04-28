package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity {

    @BindView(R.id.ivCommonBack)
    AppCompatImageView ivCommonBack;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.tvNewsCategory)
    AppCompatTextView tvNewsCategory;
    @BindView(R.id.tvNewsName)
    AppCompatTextView tvNewsName;
    @BindView(R.id.tvNewsTime)
    AppCompatTextView tvNewsTime;
    @BindView(R.id.tvNewsContent)
    AppCompatTextView tvNewsContent;
    private MainPresenter newPresenter;
    private final static String NEWS_ID = "news_id";

    public static void launchActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, NewsActivity.class);
        intent.putExtra(NEWS_ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (getIntent() != null) {
            String id = getIntent().getStringExtra(NEWS_ID);
            loadNewsDetail(id);
        }
    }

    @OnClick({R.id.ivCommonBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCommonBack: {
                finish();
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    protected void initPresenter() {
        newPresenter = new MainPresenter(new DataManager());
    }

    private void loadNewsDetail(String id) {
        newPresenter.loadNewsDetail(id, new NetCallBack<SearchBo>() {
            @Override
            public void onLoadSuccess(SearchBo newsBo) {
                tvCommonTitle.setText(newsBo.title);
                tvNewsCategory.setText(newsBo.category);
                tvNewsName.setText(newsBo.username);
                tvNewsTime.setText(newsBo.time);
                tvNewsContent.setText(newsBo.content);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
