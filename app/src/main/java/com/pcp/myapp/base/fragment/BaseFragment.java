package com.pcp.myapp.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kingja.loadsir.core.LoadService;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created with Android Studio.
 * Description: Base Fragment
 *
 * @author: Wangjianxian
 * @date: 2019/12/18
 * Time: 21:26
 */
public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;
    protected View mRootView;
    protected LoadService mLoadService;
    protected Activity activity;

    protected abstract void init();

    protected abstract int getContentViewId();

    protected abstract void initPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getContentViewId(), container, false);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        initPresenter();
        init();
        return mRootView;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
