package com.wjx.android.wanandroidmvp.ui.fragment;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/07
 * Time: 20:30
 */
public class ParentSquareFragment extends BaseFragment {
    @BindView(R.id.square_tab)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.square_divider)
    View mDivider;

    @BindView(R.id.square_viewpager)
    ViewPager mViewPager;

    private int mCurTab;

    private List<String> mTabNames = new ArrayList<>();

    private ArrayList<Fragment> mFragmentSparseArray = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.parentsquare_fragment;
    }

    @Override
    protected void init() {
        setChildViewVisibility(View.VISIBLE);
        mCurTab = 0;
        initFragment();
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragmentSparseArray.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentSparseArray == null ? 0 : mFragmentSparseArray.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabNames.get(position);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurTab = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private void initFragment() {
        mTabNames.add(0, "体系");
        mTabNames.add(1, "导航");
        mFragmentSparseArray.add(0, new TreeFragment());
        mFragmentSparseArray.add(1, new SquareFragment());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void setChildViewVisibility(int visibility) {
        mSlidingTabLayout.setVisibility(visibility);
        mDivider.setVisibility(visibility);
        mViewPager.setVisibility(visibility);
    }
}
