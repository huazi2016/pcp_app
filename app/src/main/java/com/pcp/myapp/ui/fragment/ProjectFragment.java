package com.pcp.myapp.ui.fragment;

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

import com.pcp.myapp.R;
import com.pcp.myapp.base.fragment.BaseFragment;
import com.pcp.myapp.bean.EventBo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class ProjectFragment extends BaseFragment {

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
    protected void initPresenter() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        initStatusBar();
        //mPresenter.loadProjectClassify();

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBo event) {
        if (event.target == EventBo.TARGET_PROJECT) {

        }
    }
}
