package com.huazi.jdemo.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;

import com.huazi.jdemo.R;
import com.huazi.jdemo.base.fragment.BaseFragment;
import com.huazi.jdemo.bean.EventBo;
import com.huazi.jdemo.net.ApiService;
import com.huazi.jdemo.net.DataManager;
import com.huazi.jdemo.net.MainContract;
import com.huazi.jdemo.net.MainPresenter;
import com.huazi.jdemo.net.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created with Android Studio.
 * Description: 通过实现View接口，Presenter在初始化时调用发送网络请求返回的数据，直接在Fragment中处理加载
 *
 * @author: Wangjianxian
 * @date: 2019/12/14
 * Time: 16:33
 */
public class HomeFragment extends BaseFragment implements MainContract.View {

    @BindView(R.id.tvNetLoad)
    AppCompatTextView tvNetLoad;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private MainPresenter loginPresenter;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        loginPresenter = new MainPresenter(new DataManager());
        loginPresenter.attachView(this);
    }

    @Override
    public void showCategoryList(List<String> dataList) {
        Toast.makeText(activity, dataList.get(0) + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        initStatusBar();

        tvNetLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPresenter.loadArticle(0);
                loginPresenter.getCategoryList();
            }
        });
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        initStatusBar();
    }

    public void startLoadingView() {
        EventBo e = new EventBo();
        e.target = EventBo.TARGET_MAIN;
        e.type = EventBo.TYPE_START_ANIMATION;
        EventBus.getDefault().post(e);
    }

    public void stopLoadingView() {
        EventBo e = new EventBo();
        e.target = EventBo.TARGET_MAIN;
        e.type = EventBo.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
    }

    @OnClick(R.id.layout_error)
    public void onReTry() {
        //setNetWorkError(true);
        //mPresenter.loadBanner();
        //mPresenter.loadArticle(0);
    }

    //private void setNetWorkError(boolean isSuccess) {
    //    if (isSuccess) {
    //        mNormalView.setVisibility(View.VISIBLE);
    //        mLayoutError.setVisibility(View.GONE);
    //    } else {
    //        mNormalView.setVisibility(View.GONE);
    //        mLayoutError.setVisibility(View.VISIBLE);
    //    }
    //}

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBo event) {
        if (event.target == EventBo.TARGET_HOME) {

        }
    }
}