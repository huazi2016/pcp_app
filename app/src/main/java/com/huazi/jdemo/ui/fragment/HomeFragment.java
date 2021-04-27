package com.huazi.jdemo.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.huazi.jdemo.R;
import com.huazi.jdemo.base.fragment.BaseFragment;
import com.huazi.jdemo.base.utils.Constant;
import com.huazi.jdemo.base.utils.GlideImageLoader;
import com.huazi.jdemo.base.utils.JumpWebUtils;
import com.huazi.jdemo.base.utils.Utils;
import com.huazi.jdemo.bean.base.Event;
import com.huazi.jdemo.bean.collect.Collect;
import com.huazi.jdemo.bean.db.Article;
import com.huazi.jdemo.contract.home.Contract;
import com.huazi.jdemo.custom.interpolator.ElasticScaleInterpolator;
import com.huazi.jdemo.presenter.home.HomePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;


/**
 * Created with Android Studio.
 * Description: 通过实现View接口，Presenter在初始化时调用发送网络请求返回的数据，直接在Fragment中处理加载
 *
 * @author: Wangjianxian
 * @date: 2019/12/14
 * Time: 16:33
 */
public class HomeFragment extends BaseFragment<Contract.IHomeView, HomePresenter> implements Contract.IHomeView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private Context mContext;

    //private ArticleAdapter mArticleAdapter;

    private int mCurrentPage = 0;

    private Banner mBanner;

    private List<Article> mArticleList = new ArrayList<>();

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

   // @BindView(R.id.normal_view)
   // ViewGroup mNormalView;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.toolbar_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        //if (item.getItemId() == R.id.top_search) {
//        //    Intent intent = new Intent(mContext, SearchWordActivity.class);
//        //    startActivity(intent);
//        //}
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        mPresenter.loadArticle(mCurrentPage);
        initStatusBar();
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

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void loadBanner(List<com.huazi.jdemo.bean.db.Banner> bannerList) {

    }

    @Override
    public void refreshBanner(List<com.huazi.jdemo.bean.db.Banner> bannerList) {
        loadBanner(bannerList);
    }


    @Override
    public void loadArticle(List<Article> articleList) {
        mArticleList.addAll(articleList);
        //mArticleAdapter.setArticleList(mArticleList);
    }

    @Override
    public void refreshArticle(List<Article> articleList) {
        mArticleList.clear();
        mArticleList.addAll(0, articleList);
        //mArticleAdapter.setArticleList(mArticleList);
    }

    @Override
    public void onCollect(Collect collect, int articleId) {

    }

    @Override
    public void onUnCollect(Collect collect, int articleId) {

    }

    @Override
    public void onLoading() {
        startLoadingView();
    }

    @Override
    public void onLoadFailed() {
        mLoadService.showSuccess();
        stopLoadingView();
        //setNetWorkError(false);
        ToastUtils.showShort("网络未连接请重试");
        //mSmartRefreshLayout.finishRefresh();
        //mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadSuccess() {
        stopLoadingView();
        //setNetWorkError(true);
        //mSmartRefreshLayout.finishRefresh();
        //mSmartRefreshLayout.finishLoadMore();
    }

    public void startLoadingView() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_START_ANIMATION;
        EventBus.getDefault().post(e);
    }

    public void stopLoadingView() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
    }

    @OnClick(R.id.layout_error)
    public void onReTry() {
        //setNetWorkError(true);
        //mPresenter.loadBanner();
        mPresenter.loadArticle(0);
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
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_HOME) {

        }
    }

    /**
     * 加载新文章从底部开始
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadArticle(mCurrentPage);
    }

    /**
     * 刷新文章从首页开始
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //mPresenter.refreshBanner();
        mCurrentPage = 0;
        mPresenter.refreshArticle(mCurrentPage);
    }
}