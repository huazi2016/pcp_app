package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.bean.EditEventBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.custom.chat.ChatKeyboardLayout;
import com.pcp.myapp.custom.chat.HadEditText;
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
    @BindView(R.id.tvCommRightBtn)
    AppCompatTextView tvCommRightBtn;
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
    @BindView(R.id.keyboardLayout)
    ChatKeyboardLayout keyboardLayout;

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
        tvCommRightBtn.setVisibility(View.VISIBLE);
        keyboardLayout.getInputEditText().setHint("请输入评论");
        if (getIntent() != null) {
            id = getIntent().getStringExtra(NEWS_ID);
            loadNewsDetail(id);
        }
        if (MmkvUtil.isAdmin()) {
            tvDeleteBtn.setVisibility(View.VISIBLE);
        }
        if (MmkvUtil.isAdmin()) {
            tvDeleteBtn.setVisibility(View.VISIBLE);
        } else {
            keyboardLayout.setVisibility(View.VISIBLE);
        }
        keyboardLayout.setOnChatKeyBoardListener(new ChatKeyboardLayout.OnChatKeyBoardListener() {
            @Override
            public void onSendButtonClicked(ImageView sendBtn, HadEditText editText, ProgressBar progressBar, String text) {
                if (!TextUtils.isEmpty(text)) {
                    addComments(id, text, MmkvUtil.getUserName());
                } else {
                    ToastUtils.showShort("评论内容不能为空");
                }
            }

            @Override
            public void onInputTextChanged(String text) {

            }

            @Override
            public void onKeyboardHeightChanged(int height) {

            }

            @Override
            public boolean onLeftIconClicked(View view) {
                return false;
            }

            @Override
            public boolean onRightIconClicked(View view) {
                return false;
            }
        });
    }

    @OnClick({R.id.ivCommonBack, R.id.tvDeleteBtn, R.id.tvCommRightBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCommonBack: {
                finish();
                break;
            }
            case R.id.tvDeleteBtn: {
                showConfirmDialog();
                break;
            }
            case R.id.tvCommRightBtn: {
                CommentActivity.launchActivity(activity, id);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void showConfirmDialog() {
        new XPopup.Builder(activity).asConfirm("", "确定删除吗?", "取消", "确定",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        deleteNews(id);
                    }
                }, new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                }, false).show();
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

    private void addComments(String id, String content, String username) {
        newPresenter.addComments(id, content, username, new NetCallBack<SearchBo>() {
            @Override
            public void onLoadSuccess(SearchBo newsBo) {
                //ToastUtils.showShort("已完成评论");
                keyboardLayout.getInputEditText().setText("");
                CommentActivity.launchActivity(activity, id);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtils.showShort("评论失败, 请稍后重试");
                LogUtils.e("deleteNews_err==" + errMsg);
            }
        });
    }
}
