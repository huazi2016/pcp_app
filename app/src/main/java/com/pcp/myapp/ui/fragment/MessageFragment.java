package com.pcp.myapp.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.pcp.myapp.R;
import com.pcp.myapp.base.fragment.BaseFragment;
import com.pcp.myapp.bean.EventBo;
import com.pcp.myapp.bean.MessageListBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MessageFragment extends BaseFragment {

    @BindView(R.id.rcMessageList)
    RecyclerView rcMessageList;
    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private MainPresenter chatPresenter;
    private final List<MessageListBo> dataList = new ArrayList();
    private MessageListAdapter messageAdapter;

    public static MessageFragment getInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.message_fragment;
    }

    @Override
    protected void initPresenter() {
        chatPresenter = new MainPresenter(new DataManager());
    }

    @Override
    protected void init() {
        initStatusBar();
        initRecycleView();
        // TODO: 2021/4/28 模拟名称
        //loadMessageList(MmkvUtil.getUserName());
        loadMessageList("方玉龙");
    }

    private void initRecycleView() {
        rcMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageListAdapter(R.layout.item_home_list, dataList);
        rcMessageList.setAdapter(messageAdapter);
    }

    private void loadMessageList(String teacher) {
        chatPresenter.loadMessageList(teacher, new NetCallBack<List<MessageListBo>>() {
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

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        initStatusBar();
    }

    public void startLoadingView() {
        EventBo e = new EventBo();
        e.target = EventBo.TARGET_MAIN;
        e.type = EventBo.TYPE_START_ANIMATION;
        EventBus.getDefault().post(e);
    }

    public void stopLoadingView() {
        EventBo e = new EventBo();
        e.target = EventBo.TARGET_MAIN;
        e.type = EventBo.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}