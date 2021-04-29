package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.bean.MessageListBo;
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

public class MessageActivity extends BaseActivity {

    @BindView(R.id.rcMessageList)
    RecyclerView rcMessageList;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private MainPresenter messagePresenter;
    private final List<MessageListBo> dataList = new ArrayList();
    private MessageListAdapter messageAdapter;
    private boolean isStudent = false;

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, MessageActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvCommonTitle.setText("留言");
        isStudent = MmkvUtil.isStudent();
        initRecycleView();
        //loadMessageList("方玉龙");
        loadMessageList(MmkvUtil.getUserName());
    }

    @Override
    protected void initPresenter() {
        messagePresenter = new MainPresenter(new DataManager());
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
            loadMessageList(MmkvUtil.getUserName());
        }
    }

    private void initRecycleView() {
        rcMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageListAdapter(R.layout.item_msg_list, dataList);
        rcMessageList.setAdapter(messageAdapter);
    }

    private void loadMessageList(String teacher) {
        messagePresenter.loadMessageList(teacher, new NetCallBack<List<MessageListBo>>() {
            @Override
            public void onLoadSuccess(List<MessageListBo> resultList) {
                dataList.clear();
                dataList.addAll(resultList);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }

    private class MessageListAdapter extends BaseQuickAdapter<MessageListBo, BaseViewHolder> {

        public MessageListAdapter(int layoutResId, @Nullable List<MessageListBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, final MessageListBo msgBo) {
            AppCompatTextView tvMsgReply = holder.getView(R.id.tvMsgReply);
            holder.setText(R.id.tvMsgContent, msgBo.content);
            String name = msgBo.student;
            if (isStudent) {
                name = msgBo.teacher + "老师";
            }
            holder.setText(R.id.tvMsgName, name);
            holder.setText(R.id.tvMsgTime, msgBo.time);
            if (TextUtils.isEmpty(msgBo.reply)) {
                tvMsgReply.setText("回复");
                tvMsgReply.setBackgroundColor(activity.getResources().getColor(R.color.c_D4E8BD));
            } else {
                tvMsgReply.setText("已回复");
                tvMsgReply.setBackgroundColor(activity.getResources().getColor(R.color.c_EEEEEE));
            }
            final String fName = name;
            holder.itemView.setOnClickListener(v -> {
                if (TextUtils.isEmpty(msgBo.reply)) {
                    InfoActivity.launchActivity(activity, 0, msgBo.id + "", fName, msgBo.content, 9001);
                }
            });
        }
    }
}
