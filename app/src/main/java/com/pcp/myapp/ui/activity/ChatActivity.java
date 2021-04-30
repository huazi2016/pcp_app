package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.bean.ChatMsgBo;
import com.pcp.myapp.custom.chat.ChatKeyboardLayout;
import com.pcp.myapp.custom.chat.HadEditText;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.utils.LogUtils;
import com.pcp.myapp.utils.MmkvUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.pcp.myapp.base.application.MyApp.getContext;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.ivCommonBack)
    AppCompatImageView ivCommonBack;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.rcChatMsg)
    RecyclerView rcChatMsg;
    @BindView(R.id.chat_keyboaed_layout)
    ChatKeyboardLayout chatkeyLayout;
    @BindView(R.id.chat_rootview)
    RelativeLayout chatRootview;

    private MainPresenter msgPresenter;
    private final static String CHAT_ID = "chat_id";
    private final static String TEACHER_NAME = "teacher_name";
    private MsgListAdapter msgAdapter;
    private final List<ChatMsgBo> dataList = new ArrayList();
    private String userName = "";
    private String teacherName;
    private String id;
    private boolean isTeacher;

    public static void launchActivity(Activity activity, String id, String name) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(CHAT_ID, id);
        intent.putExtra(TEACHER_NAME, name);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecycleView();
        userName = MmkvUtil.getUserName();
        isTeacher = MmkvUtil.isTeacher();
        ivCommonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chatkeyLayout.getInputEditText().setHint("请输入内容");
        setKeyBoardListener();
        if (getIntent() != null) {
            id = getIntent().getStringExtra(CHAT_ID);
            teacherName = getIntent().getStringExtra(TEACHER_NAME);
            tvCommonTitle.setText(teacherName);
            getMsgList(userName, teacherName, id);
        }
    }

    private void setKeyBoardListener() {
        chatkeyLayout.setOnChatKeyBoardListener(new ChatKeyboardLayout.OnChatKeyBoardListener() {
            @Override
            public void onSendButtonClicked(ImageView sendBtn, HadEditText editText, ProgressBar progressBar, String text) {
                //点击发送回调
                if (TextUtils.isEmpty(text)) {
                    ToastUtils.showShort("输入内容不能为空");
                } else {
                    editText.setText("");
                    sendMessage(userName, teacherName, text);
                }
            }

            @Override
            public void onInputTextChanged(String text) {

            }

            @Override
            public void onKeyboardHeightChanged(int height) {
                //键盘弹起回调
                rcChatMsg.scrollToPosition(dataList.size() - 1);
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

    @Override
    protected void initPresenter() {
        msgPresenter = new MainPresenter(new DataManager());
    }

    private void initRecycleView() {
        rcChatMsg.setLayoutManager(new LinearLayoutManager(getContext()));
        msgAdapter = new MsgListAdapter(R.layout.item_chat_msg, dataList);
        rcChatMsg.setAdapter(msgAdapter);
    }

    private void getMsgList(String student, String teacher, String id) {
        msgPresenter.getMsgList(student, teacher, id, new NetCallBack<List<ChatMsgBo>>() {
            @Override
            public void onLoadSuccess(List<ChatMsgBo> resultList) {
                dataList.addAll(resultList);
                msgAdapter.notifyDataSetChanged();
                rcChatMsg.scrollToPosition(dataList.size() - 1);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("getMsgList_err==" + errMsg);
            }
        });
    }

    private class MsgListAdapter extends BaseQuickAdapter<ChatMsgBo, BaseViewHolder> {

        public MsgListAdapter(int layoutResId, @Nullable List<ChatMsgBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, ChatMsgBo msgBo) {
            LinearLayout llLeftMsg = holder.getView(R.id.llLeftMsg);
            LinearLayout llRightMsg = holder.getView(R.id.llRightMsg);
            AppCompatTextView tvLeftContent = holder.getView(R.id.tvLeftContent);
            AppCompatTextView tvRightContent = holder.getView(R.id.tvRightContent);
            if (isTeacher) {
                //老师
                if (userName.equalsIgnoreCase(msgBo.send)) {
                    //自己发的信息
                    llLeftMsg.setVisibility(View.GONE);
                    llRightMsg.setVisibility(View.VISIBLE);
                    String sendText = msgBo.content + " :【" + msgBo.send + "老师】 ";
                    tvRightContent.setText(sendText);
                } else {
                    //学生发的信息
                    llLeftMsg.setVisibility(View.VISIBLE);
                    llRightMsg.setVisibility(View.GONE);
                    String receiveText = "【" + msgBo.send + "】 " + msgBo.content;
                    tvLeftContent.setText(receiveText);
                }
            } else {
                //学生或其他
                if (userName.equalsIgnoreCase(msgBo.send)) {
                    //自己发的信息
                    llLeftMsg.setVisibility(View.GONE);
                    llRightMsg.setVisibility(View.VISIBLE);
                    String sendText = msgBo.content + " :【" + msgBo.send + "】 ";
                    tvRightContent.setText(sendText);
                } else {
                    //老师发的信息
                    llLeftMsg.setVisibility(View.VISIBLE);
                    llRightMsg.setVisibility(View.GONE);
                    String receiveText = "【" + msgBo.send + "老师】 " + msgBo.content;
                    tvLeftContent.setText(receiveText);
                }
            }
        }
    }

    private void sendMessage(String send, String receive, String content) {
        msgPresenter.sendMessage(send, receive, content, new NetCallBack<ChatMsgBo>() {
            @Override
            public void onLoadSuccess(ChatMsgBo msgBo) {
                dataList.add(msgBo);
                msgAdapter.notifyDataSetChanged();
                rcChatMsg.scrollToPosition(dataList.size() - 1);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("getMsgList_err==" + errMsg);
            }
        });
    }
}
