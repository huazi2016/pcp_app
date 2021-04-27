package com.pcp.myapp.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.base.utils.Utils;
import com.pcp.myapp.bean.LoginBo;
import com.pcp.myapp.custom.CustomEditText;
import com.pcp.myapp.custom.loading.LoadingView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;

import java.util.logging.Level;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/26
 * Time: 15:26
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    CustomEditText mPassword;

    @BindView(R.id.repassword)
    CustomEditText mRePassword;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    @BindView(R.id.register)
    Button mRegisterButton;

    private Context mContext;
    private MainPresenter loginPresenter;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initToolbar();
        mRegisterButton.getBackground().setColorFilter(
                Utils.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void initPresenter() {
        loginPresenter = new MainPresenter(new DataManager());
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle(R.string.register);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    //@Override
    //public void onRegister(RegisterData registerData) {
    //    stopAnim();
    //    if (registerData != null) {
    //        if (registerData.getErrorCode() == Constant.SUCCESS) {
    //            ToastUtils.showShort(mContext.getString(R.string.register_success));
    //            Intent intent = new Intent(mContext, LoginActivity.class);
    //            intent.putExtra(Constant.EXTRA_KEY_USERNAME, mUsername.getText().toString());
    //            intent.putExtra(Constant.EXTRA_VALUE_PASSWORD, mPassword.getText().toString());
    //            startActivity(intent);
    //            finish();
    //        } else {
    //            ToastUtils.showShort(registerData.getErrorMsg());
    //        }
    //    }
    //}

    @OnClick(R.id.register)
    public void register() {
        String userNameText = mUsername.getText().toString();
        String passWordText = mPassword.getText().toString();
        String rePassWordText = mRePassword.getText().toString();

        if (TextUtils.isEmpty(userNameText) || TextUtils.isEmpty(passWordText) ||
                TextUtils.isEmpty(rePassWordText)) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        if (!TextUtils.equals(passWordText, rePassWordText)) {
            ToastUtils.showShort(mContext.getString(R.string.password_not_match));
            return;
        }
        showSelectPop(userNameText, passWordText);
    }

    private void showSelectPop(String userName, String pwd) {
       new XPopup.Builder(activity)
                .isDarkTheme(false)
                .isDestroyOnDismiss(true)
                .asCenterList("请选择需要注册的角色", new String[]{"学生", "老师", "管理员", "关闭弹窗"},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                if (position != 3) {
                                    startAnim();
                                    loginPresenter.register(userName, pwd, new NetCallBack<LoginBo>() {
                                        @Override
                                        public void onLoadSuccess(LoginBo data) {
                                            stopAnim();
                                            ToastUtils.showShort("注册成功");
                                            finish();
                                        }

                                        @Override
                                        public void onLoadFailed(String errMsg) {
                                            stopAnim();
                                            ToastUtils.showShort(errMsg);
                                        }
                                    });
                                }
                            }
                        }).show();
    }

    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.startTranglesAnimation();
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
    }

}
