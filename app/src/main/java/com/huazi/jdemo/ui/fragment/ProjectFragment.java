package com.huazi.jdemo.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;

import com.huazi.jdemo.R;
import com.huazi.jdemo.base.fragment.BaseFragment;
import com.huazi.jdemo.bean.base.Event;
import com.huazi.jdemo.bean.db.ProjectClassify;
import com.huazi.jdemo.contract.project.Contract;
import com.huazi.jdemo.presenter.project.ProjectPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

import static com.blankj.utilcode.util.ColorUtils.getColor;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/19
 * Time: 17:17
 */
public class ProjectFragment extends BaseFragment<Contract.IProjectView, ProjectPresenter> implements Contract.IProjectView {

    @BindView(R.id.tvText)
    AppCompatTextView tvText;

    public static ProjectFragment getInstance() {
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.project_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        initStatusBar();
        mPresenter.loadProjectClassify();

        tvText.setText("黄油刀");
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (ColorUtils.calculateLuminance(getColor(R.color.white)) >= 0.5) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        initStatusBar();
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    public void onLoadProjectClassify(List<ProjectClassify> projectClassifies) {

    }

    @Override
    public void onRefreshProjectClassify(List<ProjectClassify> projectClassifies) {
        onLoadProjectClassify(projectClassifies);
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed() {
    }

    @Override
    public void onLoadSuccess() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_PROJECT) {

        }
    }
}
