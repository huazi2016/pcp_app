package com.pcp.myapp.ui.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.pcp.myapp.R;
import com.pcp.myapp.base.utils.Utils;
import com.pcp.myapp.bean.EventBo;
import com.pcp.myapp.utils.MmkvUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 启动动画
 */
public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView mLottieAnimationView;

    private ViewGroup mSplashContainer;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        setContentView(R.layout.activity_splash);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mSplashContainer = findViewById(R.id.splash_container);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化进场动画
     */
    private void initView() {
        mSplashContainer.setBackgroundColor(Utils.getColor(mContext));
        mLottieAnimationView = findViewById(R.id.splash_animation);
        mLottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                String userName = MmkvUtil.getUserName();
                if (!TextUtils.isEmpty(userName)) {
                    MainActivity.launchActivity(SplashActivity.this);
                } else {
                    LoginActivity.launchActivity(SplashActivity.this);
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBo event) {
        if (event.target == EventBo.TARGET_SPLASH) {
            if (event.type == EventBo.TYPE_REFRESH_COLOR) {
                mSplashContainer.setBackgroundColor(Utils.getColor(mContext));
            }
        }
    }
}
