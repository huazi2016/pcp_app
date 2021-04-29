package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

public class InfoActivity extends BaseActivity {

    @BindView(R.id.ivCommonBack)
    AppCompatImageView ivCommonBack;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.rcInfoMsg)
    RecyclerView rcInfoMsg;
    @BindView(R.id.keyboardLayout)
    ChatKeyboardLayout keyboardLayout;

    private MainPresenter msgPresenter;
    private final static String CHAT_ID = "chat_id";
    private final static String PAGE_TYPE = "page_type";
    private final static String INFO_NAME = "info_name";
    private final static String INFO_CONTENT = "info_content";
    private MsgListAdapter msgAdapter;
    private final List<ChatMsgBo> dataList = new ArrayList();
    private String userName = "";
    private String name;
    private String id;
    private int pageType = 0; //0: 老师回复留言 1:学生留言
    private boolean isTeacher;

    public static void launchActivity(Activity activity, int type, String id, String name, String content) {
        Intent intent = new Intent(activity, InfoActivity.class);
        intent.putExtra(CHAT_ID, id);
        intent.putExtra(PAGE_TYPE, type);
        intent.putExtra(INFO_NAME, name);
        intent.putExtra(INFO_CONTENT, content);
        activity.startActivity(intent);
    }

    public static void launchActivity(Activity activity, int type, String id, String name, String content, int code) {
        Intent intent = new Intent(activity, InfoActivity.class);
        intent.putExtra(CHAT_ID, id);
        intent.putExtra(PAGE_TYPE, type);
        intent.putExtra(INFO_NAME, name);
        intent.putExtra(INFO_CONTENT, content);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_info01;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (getIntent() != null) {
            id = getIntent().getStringExtra(CHAT_ID);
            pageType = getIntent().getIntExtra(PAGE_TYPE, 0);
            name = getIntent().getStringExtra(INFO_NAME);
            String content = getIntent().getStringExtra(INFO_CONTENT);
            tvCommonTitle.setText(name);

            if (pageType == 0) {
                dataList.clear();
                ChatMsgBo msgBo = new ChatMsgBo();
                msgBo.content = content;
                msgBo.msgType = 0;
                dataList.add(msgBo);
            }
        }
        initRecycleView();
        userName = MmkvUtil.getUserName();
        isTeacher = MmkvUtil.isTeacher();
        ivCommonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String hint = "请回复留言";
        if (pageType == 1) {
            hint = "请输入留言内容";
        }
        keyboardLayout.getInputEditText().setHint(hint);
        keyboardLayout.popKeyboard();
        setKeyBoardListener();
    }

    private void setKeyBoardListener() {
        keyboardLayout.setOnChatKeyBoardListener(new ChatKeyboardLayout.OnChatKeyBoardListener() {
            @Override
            public void onSendButtonClicked(ImageView sendBtn, HadEditText editText, ProgressBar progressBar, String text) {
                //点击发送回调
                if (TextUtils.isEmpty(text)) {
                    ToastUtils.showShort("输入内容不能为空");
                } else {
                    editText.setText("");
                    if (pageType == 1) {
                        replyTeacherMsg(text, userName, name);
                    } else {
                        replyMsg(text);
                    }
                }
            }

            @Override
            public void onInputTextChanged(String text) {

            }

            @Override
            public void onKeyboardHeightChanged(int height) {
                //键盘弹起回调
                rcInfoMsg.scrollToPosition(dataList.size() - 1);
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
        rcInfoMsg.setLayoutManager(new LinearLayoutManager(getContext()));
        msgAdapter = new MsgListAdapter(R.layout.item_info_msg, dataList);
        rcInfoMsg.setAdapter(msgAdapter);
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
            if (msgBo.msgType == 0) {
                llLeftMsg.setVisibility(View.VISIBLE);
                String receiveText = "【留言】 " + msgBo.content;
                tvLeftContent.setText(receiveText);
                llRightMsg.setVisibility(View.GONE);
            } else {
                llLeftMsg.setVisibility(View.GONE);
                llRightMsg.setVisibility(View.VISIBLE);
                String sendText = "【回复】 " + msgBo.content;
                tvRightContent.setText(sendText);
            }
        }
    }

    private void replyMsg(String reply) {
        msgPresenter.replyMsg(id, reply, new NetCallBack<ChatMsgBo>() {
            @Override
            public void onLoadSuccess(ChatMsgBo msgBo) {
                ChatMsgBo msgBo01 = new ChatMsgBo();
                msgBo01.content = reply;
                msgBo01.msgType = 1;
                dataList.add(msgBo01);
                msgAdapter.notifyDataSetChanged();
                rcInfoMsg.scrollToPosition(dataList.size() - 1);
                ToastUtils.showShort("已回复留言");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("getMsgList_err==" + errMsg);
            }
        });
    }

    private void replyTeacherMsg(String reply, String student, String teacher) {
        msgPresenter.replyTeacherMsg(reply, student, teacher, new NetCallBack<ChatMsgBo>() {
            @Override
            public void onLoadSuccess(ChatMsgBo msgBo) {
                ChatMsgBo msgBo01 = new ChatMsgBo();
                msgBo01.content = reply;
                msgBo01.msgType = 1;
                dataList.add(msgBo01);
                msgAdapter.notifyDataSetChanged();
                rcInfoMsg.scrollToPosition(dataList.size() - 1);
                ToastUtils.showShort("留言完成");
                finish();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("getMsgList_err==" + errMsg);
            }
        });
    }
}
