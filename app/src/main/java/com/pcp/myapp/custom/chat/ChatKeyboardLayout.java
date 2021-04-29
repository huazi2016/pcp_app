package com.pcp.myapp.custom.chat;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.IntDef;

import com.pcp.myapp.R;
import com.pcp.myapp.utils.TextUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author chris
 */
public class ChatKeyboardLayout extends SoftHandleLayout {
    public static class Style {
        private Style() {
        }

        public static final int NONE = 0;
        public static final int TEXT_ONLY = 1;
        public static final int TEXT_EMOTICON = 2;
        public static final int CHAT_STYLE = 3;
    }

    @IntDef({Style.TEXT_ONLY, Style.TEXT_EMOTICON, Style.CHAT_STYLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyboardStyle {
    }

    private HadEditText mEtInputFrame;
    private RelativeLayout mInputLayout;
    private LinearLayout lyBottomLayout;
    private ImageView mIvSendBtn;
    private ProgressBar mProgress;
    @KeyboardStyle
    int mCurrentStyle = Style.NONE;

    public ChatKeyboardLayout(Context context) {
        super(context, null);
        initView(context);
    }

    public ChatKeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChatKeyboardLayout);
        @KeyboardStyle int keyboardStyle = typedArray.getInt(R.styleable.ChatKeyboardLayout_keyboardStyle, Style.TEXT_ONLY);
        setKeyboardStyle(keyboardStyle);
        typedArray.recycle();
    }

    private void initView(Context context) {

        LayoutInflater.from(context).inflate(R.layout.keyboard_bar_layout, this);
        mInputLayout = (RelativeLayout) findViewById(R.id.view_keyboard_input_layout);
        lyBottomLayout = (LinearLayout) findViewById(R.id.view_keyboard_bottom);
        mProgress = (ProgressBar) findViewById(R.id.pb_keyboard_right_icon);
        mIvSendBtn = (ImageView) findViewById(R.id.iv_keyboard_send_button);
        mEtInputFrame = (HadEditText) findViewById(R.id.et_chat);
        mEtInputFrame.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});

        setAutoHeightLayoutView(lyBottomLayout);
        mProgress.setVisibility(GONE);
        mIvSendBtn.setOnClickListener(new SendClickListener());

        mEtInputFrame.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mEtInputFrame.isFocused()) {
                    mEtInputFrame.setFocusable(true);
                    mEtInputFrame.setFocusableInTouchMode(true);
                }
                return false;
            }
        });
        mEtInputFrame.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    setEditableState(true);
                } else {
                    setEditableState(false);
                }
            }
        });
        mEtInputFrame.setOnTextChangedInterface(new HadEditText.OnTextChangedInterface() {
            @Override
            public void onTextChanged(CharSequence charStr, int start, int before, int count) {

                //限制输入文本范围
                String text = (mEtInputFrame != null && !TextUtils.isEmpty(mEtInputFrame.getText()))?
                        mEtInputFrame.getText().toString() : "";
                String str = TextUtil.stringFilter(text);

                if (mEtInputFrame != null) {
                    if (!text.equals(str)) {
                        mEtInputFrame.setText(str);
                        //设置新的光标所在位置
                        mEtInputFrame.setSelection(str.length());
                    }
                }

                if (mOnChatKeyBoardListener != null) {
                    mOnChatKeyBoardListener.onInputTextChanged(str);
                }

                if (TextUtils.isEmpty(str)) {
                    mIvSendBtn.setImageResource(R.drawable.private_msg_send_nomal);
                    //mIvSendBtn.setEnabled(false);
                } else {
                    //mIvSendBtn.setEnabled(true);
                    mIvSendBtn.setImageResource(R.drawable.private_msg_send_selected);
                }
            }
        });
    }

    private void setEditableState(boolean editableState) {
        if (editableState) {
            mEtInputFrame.setFocusable(true);
            mEtInputFrame.setFocusableInTouchMode(true);
            mEtInputFrame.requestFocus();
        } else {
            mEtInputFrame.setFocusable(false);
            mEtInputFrame.setFocusableInTouchMode(false);
        }
    }

    public ImageView getSendButton() {
        return mIvSendBtn;
    }

    public HadEditText getInputEditText() {
        return mEtInputFrame;
    }

    public void clearInputContent() {
        mEtInputFrame.setText("");
    }

    public void del() {
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        mEtInputFrame.onKeyDown(KeyEvent.KEYCODE_DEL, event);
    }

    /**
     * Set the keyboard style, {@link Style}
     * when this method is called, the keyboard will change it's style immediately
     * if the style is same as current style, it won't change
     *
     * @param style style of keyboard
     */
    public void setKeyboardStyle(@KeyboardStyle int style) {
        // when style changed, restore the keyboard to init state
        if (mCurrentStyle == style) {
            return;
        }
        clearInputContent();
        mEtInputFrame.setVisibility(VISIBLE);
        //hideKeyboard();
        switch (style) {
            case Style.TEXT_ONLY:
                mProgress.setVisibility(GONE);
                mIvSendBtn.setVisibility(VISIBLE);
                break;
            case Style.TEXT_EMOTICON:
                break;
            case Style.CHAT_STYLE:
                break;
        }
        mCurrentStyle = style;
    }

    /**
     * hide whole layout of keyboard
     */
    public void hideLayout() {
        hideKeyboard();
        findViewById(R.id.keyboard_layout_id).setVisibility(GONE);
    }

    /**
     * show keyboard layout
     */
    public void showLayout() {
        findViewById(R.id.keyboard_layout_id).setVisibility(VISIBLE);
        int barHeight = findViewById(R.id.keyboard_layout_id).getHeight();
        if (mOnChatKeyBoardListener != null) {
            mOnChatKeyBoardListener.onKeyboardHeightChanged(barHeight);
        }
    }

    /**
     * judge whether keyboard layout is show
     *
     * @return true or false
     */
    public boolean isLayoutVisible() {
        return VISIBLE == findViewById(R.id.keyboard_layout_id).getVisibility();
    }

    /**
     * hide soft keyboard
     */
    public void hideKeyboard() {
        hideAutoView();
        closeSoftKeyboard(mEtInputFrame);
    }

    /**
     * pop soft keyboard, if the layout is hidden, it will show layout first
     */
    public void popKeyboard() {
        showLayout();
        openSoftKeyboard(mEtInputFrame);
        showAutoView();
    }

    /**
     * judge whether keyboard was popped
     *
     * @return true or false
     */
    public boolean isKeyboardPopped() {
        return mKeyboardState != KEYBOARD_STATE_NONE;
    }

    @Override
    protected void autoViewHeightChanged(final int height) {
        super.autoViewHeightChanged(height);
        if (findViewById(R.id.keyboard_layout_id).getVisibility() != VISIBLE) {
            if (mOnChatKeyBoardListener != null) {
                mOnChatKeyBoardListener.onKeyboardHeightChanged(0);
            }
        } else {
            int barHeight = findViewById(R.id.keyboard_layout_id).getHeight();
            if (mOnChatKeyBoardListener != null) {
                mOnChatKeyBoardListener.onKeyboardHeightChanged(barHeight);
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (lyBottomLayout != null && lyBottomLayout.isShown()) {
                    //hideAutoView();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    private class SendClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            if (mOnChatKeyBoardListener != null) {
                //setEditableState(true);
                //lyBottomLayout.setVisibility(VISIBLE);
                //showAutoView();
                setEditableState(false);
                String inputText = (mEtInputFrame != null && !TextUtils.isEmpty(mEtInputFrame.getText()))? mEtInputFrame.getText().toString() : "";
                mOnChatKeyBoardListener.onSendButtonClicked(mIvSendBtn, mEtInputFrame, mProgress, inputText);
                openSoftKeyboard(mEtInputFrame);
            }
        }
    }

    private OnChatKeyBoardListener mOnChatKeyBoardListener;

    public void setOnChatKeyBoardListener(OnChatKeyBoardListener l) {
        this.mOnChatKeyBoardListener = l;
    }

    public interface OnChatKeyBoardListener {
        /**
         * When send button clicked
         *
         * @param text content in input area
         */
        void onSendButtonClicked(ImageView sendBtn, HadEditText editText, ProgressBar progressBar, String text);

        /**
         * When user input or delete text in input area
         *
         * @param text changing text
         */
        void onInputTextChanged(String text);

        /**
         * when keyboard popped or back, get the pixels of the height include keyboard bar
         *
         * @param height pixel height
         */
        void onKeyboardHeightChanged(int height);

        /**
         * when left icon clicked, this will be called
         *
         * @param view view of clicked
         * @return true, won't execute default actions; false, execute default actions
         */
        boolean onLeftIconClicked(View view);

        /**
         * when right icon clicked, it will be called
         *
         * @param view view of clicked
         * @return true, won't execute default actions; false, execute default actions
         */
        boolean onRightIconClicked(View view);
    }
}