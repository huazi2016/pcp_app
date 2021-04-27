package com.pcp.myapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.base.utils.Utils;
import com.pcp.myapp.custom.CustomEditText;
import com.pcp.myapp.custom.loading.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    private String mUserNameText;
    private String mPassWordText;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    CustomEditText mPassword;

    @BindView(R.id.login)
    Button mLoginButton;

    @BindView(R.id.go_register)
    Button mRegister;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;

    private Context mContext;

    private String mRegisterName;

    private String mRegisterPassword;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        try {
            mRegisterName = getIntent().getExtras().getString("name");
            mRegisterPassword = getIntent().getExtras().getString("pwd");
            mUsername.setText(mRegisterName);
            mPassword.setText(mRegisterPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initToolbar();
        mLoginButton.getBackground().setColorFilter(
                Utils.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void initPresenter() {

    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle("欢迎登录");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.login)
    public void login() {
        //if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText())) {
        //    ToastUtils.showShort(mContext.getString(R.string.complete_info));
        //    return;
        //}
        //startAnim();
        //mUserNameText = mUsername.getText().toString();
        //mPassWordText = mPassword.getText().toString();
        //mPresenter.login(mUserNameText, mPassWordText);
        MainActivity.launchActivity(activity);
    }

    @OnClick(R.id.go_register)
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.startTranglesAnimation();
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
    }
}
