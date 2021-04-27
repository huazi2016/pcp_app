package com.huazi.jdemo.base.application;

import androidx.appcompat.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.kingja.loadsir.core.LoadSir;
import com.huazi.jdemo.base.callback.ErrorCallback;
import com.huazi.jdemo.base.utils.Constant;
import com.tencent.mmkv.MMKV;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created with Android Studio.
 * Description: Base Application
 *
 * @author: Wangjianxian
 * @date: 2019/12/18
 * Time: 21:26
 */
public class MyApp extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Utils.init(this);
        initMode();
        //初始化轻量级存储框架-mmkv
        MMKV.initialize(getApplicationContext());
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .commit();
    }

    private void initMode() {
        boolean isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).getBoolean
                (Constant.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(isNightMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
    }
}
