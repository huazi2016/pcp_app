package com.pcp.myapp.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.pcp.myapp.R;
import com.pcp.myapp.base.fragment.BaseFragment;
import com.pcp.myapp.bean.ChatListBo;
import com.pcp.myapp.bean.EventBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.ui.activity.ChatActivity;
import com.pcp.myapp.ui.activity.InfoActivity;
import com.pcp.myapp.ui.activity.MessageActivity;
import com.pcp.myapp.utils.LogUtils;
import com.pcp.myapp.utils.MmkvUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ChatFragment extends BaseFragment {

    @BindView(R.id.tvChatTitle)
    AppCompatTextView tvChatTitle;
    @BindView(R.id.rcChatList)
    RecyclerView rcChatList;
    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;
    @BindView(R.id.tvMessageBtn)
    AppCompatTextView tvMessageBtn;

    private MainPresenter chatPresenter;
    private final List<ChatListBo> dataList = new ArrayList();
    private ChatListAdapter chatAdapter;
    private boolean isTeacher = false;

    public static ChatFragment getInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.chat_fragment;
    }

    @Override
    protected void initPresenter() {
        chatPresenter = new MainPresenter(new DataManager());
    }

    @Override
    protected void init() {
        initStatusBar();
        isTeacher = MmkvUtil.isTeacher();
        if (isTeacher) {
            tvMessageBtn.setVisibility(View.VISIBLE);
        }
        initRecycleView();
        String role = MmkvUtil.TEACHER;
        if (MmkvUtil.isTeacher()) {
            role = MmkvUtil.STUDENT;
        }
        loadChatList(role);
    }

    private void initRecycleView() {
        rcChatList.setLayoutManager(new LinearLayoutManager(getContext()));
        chatAdapter = new ChatListAdapter(R.layout.item_chat_list, dataList);
        rcChatList.setAdapter(chatAdapter);
    }

    @OnClick({R.id.tvMessageBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvMessageBtn: {
                MessageActivity.launchActivity(activity);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void loadChatList(String role) {
        chatPresenter.loadChatList(role, new NetCallBack<List<ChatListBo>>() {
            @Override
            public void onLoadSuccess(List<ChatListBo> resultList) {
                dataList.clear();
                dataList.addAll(resultList);
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }

    private class ChatListAdapter extends BaseQuickAdapter<ChatListBo, BaseViewHolder> {

        public ChatListAdapter(int layoutResId, @Nullable List<ChatListBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, ChatListBo chatBo) {
            holder.setText(R.id.tvChatName, chatBo.username);
            String text01 = "离线";
            if (!TextUtils.isEmpty(chatBo.onlineTime)) {
                text01 = "在线";
            }
            holder.setText(R.id.tvChatStatus, text01);
            holder.getView(R.id.tvChatBtn01).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //聊天
                    ChatActivity.launchActivity(activity, chatBo.id + "", chatBo.username);
                }
            });
            int visibility = View.VISIBLE;
            if (isTeacher) {
                visibility = View.GONE;
            }
            holder.getView(R.id.tvChatBtn02).setVisibility(visibility);
            holder.getView(R.id.tvChatBtn02).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //留言
                    InfoActivity.launchActivity(activity, 1, chatBo.id + "", chatBo.username, "");
                }
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