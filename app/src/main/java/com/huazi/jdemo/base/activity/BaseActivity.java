package com.huazi.jdemo.base.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created with Android Studio.
 * Description: Base Activity
 *
 * @author: Wangjianxian
 * @date: 2019/12/18
 * Time: 21:26
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 使用ButterKnife
     */
    protected Unbinder unbinder;
    protected Activity activity;
    protected Context context;

    /**
     * 获取布局id
     * @return 当前需要加载的布局
     */
    protected abstract int getContentViewId();

    /**
     * 初始化
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        activity = this;
        context = this;
        init(savedInstanceState);
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
