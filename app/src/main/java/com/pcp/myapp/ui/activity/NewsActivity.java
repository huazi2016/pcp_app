package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.bean.EditEventBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.utils.LogUtils;
import com.pcp.myapp.utils.MmkvUtil;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.tvDeleteBtn)
    AppCompatTextView tvDeleteBtn;

    private MainPresenter newPresenter;
    private final static String NEWS_ID = "news_id";
    private String id = "";

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
            id = getIntent().getStringExtra(NEWS_ID);
            loadNewsDetail(id);
        }
        if (MmkvUtil.isAdmin()) {
            tvDeleteBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ivCommonBack, R.id.tvDeleteBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCommonBack: {
                finish();
                break;
            }
            case R.id.tvDeleteBtn: {
                deleteNews(id);
                //CommentActivity.launchActivity(activity, "");
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
                tvNewsCategory.setVisibility(View.VISIBLE);
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

    private void deleteNews(String id) {
        newPresenter.deleteNews(id, new NetCallBack<SearchBo>() {
            @Override
            public void onLoadSuccess(SearchBo newsBo) {
                EventBus.getDefault().post(new EditEventBo());
                ToastUtils.showShort("删除成功");
                finish();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtils.showShort("删除失败, 稍后重试!");
                LogUtils.e("deleteNews_err==" + errMsg);
            }
        });
    }
}
