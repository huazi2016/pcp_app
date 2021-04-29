package com.pcp.myapp.custom.chat;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author chris
 */
public class HadEditText extends AppCompatEditText {
    private Context mContext;

    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String inputAfterText;
    //是否重置了EditText的内容
    private boolean resetText;

    public HadEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
//        initEditText();
    }

    public HadEditText(Context context) {
        super(context);
        mContext = context;
//        initEditText();
    }

    public HadEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
//        initEditText();
    }

//    private void initEditText() {
//        addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
//                if (!resetText) {
//                    cursorPos = getSelectionEnd();
//                    // 这里用s.toString()而不直接用s是因为如果用s，
//                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
//                    // inputAfterText也就改变了，那么表情过滤就失败了
//                    inputAfterText = s.toString();
//                }
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!resetText) {
//                    if (count >= 2) {//表情符号的字符长度最小为2
//                        CharSequence input = s.subSequence(cursorPos, cursorPos + count);
//                        if (FilterEmojiTextWatcher.containsEmoji(input.toString())) {
//                            resetText = true;
//                            //是表情符号就将文本还原为输入表情符号之前的内容
//                            setText(inputAfterText);
//                            CharSequence text = getText();
//                            if (text instanceof Spannable) {
//                                Spannable spanText = (Spannable) text;
//                                Selection.setSelection(spanText, text.length());
//                            }
//                        }
//                    }
//                } else {
//                    resetText = false;
//                }
//
//                if (onTextChangedInterface != null) {
//                    onTextChangedInterface.onTextChanged(s,start,before,count);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }

    //若限制输入文本内容, 可以放开此方法
    @Override
    protected void onTextChanged(CharSequence arg0, int start, int before, int count) {
        super.onTextChanged(arg0, start, before, count);
        if (onTextChangedInterface != null) {
            onTextChangedInterface.onTextChanged(arg0, start, before, count);
        }
        //String content = arg0.subSequence(0, start + after).toString();
        //EmoticonHandler.getInstance(mContext).setTextFace(content, getText(), start, KeyBoardUtils
        //        .getFontSize(getTextSize()));
    }

    public interface OnTextChangedInterface {
        void onTextChanged(CharSequence argo, int start, int lengthBefore, int after);
    }

    OnTextChangedInterface onTextChangedInterface;

    public void setOnTextChangedInterface(OnTextChangedInterface i) {
        onTextChangedInterface = i;
    }
}
