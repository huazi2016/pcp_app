package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.bean.MessageListBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.utils.LogUtils;
import com.pcp.myapp.utils.MmkvUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pcp.myapp.base.application.MyApp.getContext;

public class CommentActivity extends BaseActivity {

    @BindView(R.id.rcMessageList)
    RecyclerView rcMessageList;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;

    private MainPresenter commentPresenter;
    private final List<SearchBo> dataList = new ArrayList();
    private CommentListAdapter commentAdapter;
    private boolean isStudent = false;
    private String id = "";

    public static void launchActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CommentActivity.class);
        intent.putExtra("", id);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvCommonTitle.setText("评论");
        isStudent = MmkvUtil.isStudent();
        if (getIntent() != null) {
            //id = getIntent().getStringArrayExtra();
        }
        initRecycleView();
        //loadMessageList("方玉龙");
        getCommentsList();
    }

    @Override
    protected void initPresenter() {
        commentPresenter = new MainPresenter(new DataManager());
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9001) {
            getCommentsList();
        }
    }

    private void initRecycleView() {
        rcMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        commentAdapter = new CommentListAdapter(R.layout.item_msg_list, dataList);
        rcMessageList.setAdapter(commentAdapter);
    }

    private void getCommentsList() {
        // TODO: 2021/4/29 模拟id
        id = "1";
        commentPresenter.getCommentsList(id, new NetCallBack<List<SearchBo>>() {
            @Override
            public void onLoadSuccess(List<SearchBo> resultList) {
                dataList.clear();
                dataList.addAll(resultList);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("getCommentsList_err==" + errMsg);
            }
        });
    }

    private class CommentListAdapter extends BaseQuickAdapter<SearchBo, BaseViewHolder> {

        public CommentListAdapter(int layoutResId, @Nullable List<SearchBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, final SearchBo msgBo) {
            //AppCompatTextView tvMsgReply = holder.getView(R.id.tvMsgReply);
            holder.setText(R.id.tvMsgContent, msgBo.content);
        }
    }
}
