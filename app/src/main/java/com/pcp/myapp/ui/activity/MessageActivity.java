package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.utils.LogUtils;

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
        initRecycleView();
        // TODO: 2021/4/28 模拟名称
        //loadMessageList(MmkvUtil.getUserName());
        loadMessageList("方玉龙");
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

    private void initRecycleView() {
        rcMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageListAdapter(R.layout.item_home_list, dataList);
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
        protected void convert(@NotNull BaseViewHolder holder, MessageListBo msgBo) {
            //holder.setText(R.id.tvHomeName, searchBo.username);
            //holder.setText(R.id.tvHomeTime, searchBo.time);
            holder.setText(R.id.tvHomeTitle, msgBo.teacher);
            holder.setText(R.id.tvHomeContent, msgBo.student);
            holder.itemView.setOnClickListener(v -> {
                //NewsActivity.launchActivity(activity, searchBo.id + "");
            });
        }
    }
}
