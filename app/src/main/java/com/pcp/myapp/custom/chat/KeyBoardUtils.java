package com.pcp.myapp.custom.chat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 软键盘工具类
 *
 * @author liuhuaxi 2017-7-30
 */
public class KeyBoardUtils {
    private static final String EXTRA_ISINITDB = "ISINITDB";
    private static final String EXTRA_DEF_KEYBOARDHEIGHT = "DEF_KEYBOARDHEIGHT";
    private static int sDefKeyboardHeight = 0;

    public static boolean isInitDb(Context context) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(EXTRA_ISINITDB, false);
    }

    public static void setIsInitDb(Context context, boolean b) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putBoolean(EXTRA_ISINITDB, b).apply();
    }

    public static int getDefKeyboardHeight(Context context) {
        if (sDefKeyboardHeight == 0) {   //evaluate keyboard height
            sDefKeyboardHeight = getDisplayHeightPixels(context) * 3 / 7;
        }
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        int height = settings.getInt(EXTRA_DEF_KEYBOARDHEIGHT, 0);
        if (height > 0 && sDefKeyboardHeight != height) {
            KeyBoardUtils.setDefKeyboardHeight(context, height);
        }
        return sDefKeyboardHeight;
    }

    public static void setDefKeyboardHeight(Context context, int height) {
        if (sDefKeyboardHeight != height) {
            final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            settings.edit().putInt(EXTRA_DEF_KEYBOARDHEIGHT, height).apply();
        }
        KeyBoardUtils.sDefKeyboardHeight = height;
    }

    private static int mDisplayWidthPixels = 0;
    private static int mDisplayHeightPixels = 0;

    private static void getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.
                getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            mDisplayWidthPixels = dm.widthPixels;
            mDisplayHeightPixels = dm.heightPixels;
        }
    }

    public static int getDisplayHeightPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (mDisplayHeightPixels == 0) {
            getDisplayMetrics(context);
        }
        return mDisplayHeightPixels;
    }

    public static int getDisplayWidthPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (mDisplayWidthPixels == 0) {
            getDisplayMetrics(context);
        }
        return mDisplayWidthPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static int getFontSize(float textSize) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) (Math.ceil(fm.bottom - fm.top) + 0.5);
    }
}
