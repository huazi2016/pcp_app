package com.wjx.android.wanandroidmvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ArticleAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.GlideImageLoader;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.contract.home.Contract;
import com.wjx.android.wanandroidmvp.presenter.home.HomePresenter;

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


/**
 * Created with Android Studio.
 * Description: 通过实现View接口，Presenter在初始化时调用发送网络请求返回的数据，直接在Fragment中处理加载
 *
 * @author: 王拣贤
 * @date: 2019/12/14
 * Time: 16:33
 */
public class HomeFragment extends BaseFragment<Contract.IHomeView, HomePresenter> implements Contract.IHomeView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private Context mContext;

    private ArticleAdapter mArticleAdapter;

    private int mCurpage = 0;

    private Banner mBanner;

    private List<Article> mArticleList = new ArrayList<>();

    private int articleId = 0;

    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.nest_scroll)
    NestedScrollView mNestedScrollView;

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
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        initAdapter();
        initBanner();
        mPresenter.loadBanner();
        mPresenter.loadArticle(mCurpage);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) {
                    getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                } else if (scrollY > oldScrollY) {
                    getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
            }
        });
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initBanner() {
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setBannerAnimation(Transformer.Tablet);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mArticleAdapter = new ArticleAdapter(mContext, mArticleList);
        View header = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.home_header_view, null, false);
        mBanner = header.findViewById(R.id.banner);
        mArticleAdapter.setHeaderView(header);
        mRecyclerView.setAdapter(mArticleAdapter);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void loadBanner(List<com.wjx.android.wanandroidmvp.bean.db.Banner> bannerList) {
        if (bannerList != null) {
            List<String> imageUrls = bannerList
                    .stream()
                    .map(banner -> banner.imagePath)
                    .collect(Collectors.toList());
            mBanner.setImages(imageUrls);
            List<String> titles = bannerList
                    .stream()
                    .map(banner -> banner.title)
                    .collect(Collectors.toList());
            mBanner.setBannerTitles(titles);
            mBanner.start();
            mBanner.setOnBannerListener(new OnBannerListener(){
                @Override
                public void OnBannerClick(int position) {
                    JumpWebUtils.startWebView(mContext,
                            bannerList.get(position).title,
                            bannerList.get(position).url);
                }
            });
        }
    }

    @Override
    public void refreshBanner(List<com.wjx.android.wanandroidmvp.bean.db.Banner> bannerList) {
        loadBanner(bannerList);
    }

    @Override
    public void loadArticle(List<Article> articleList) {
        mArticleList.addAll(articleList);
        mArticleAdapter.setArticleList(mArticleList);
    }

    @Override
    public void refreshArticle(List<Article> articleList) {
        mArticleList.clear();
        mArticleList.addAll(0, articleList);
        mArticleAdapter.setArticleList(mArticleList);
    }

    @Override
    public void onCollect(Collect collect, int articleId) {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                mArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mArticleAdapter.setArticleList(mArticleList);
            } else {
                ToastUtils.showShort("收藏失败");
            }
        }
    }

    @Override
    public void onUnCollect(Collect collect, int articleId) {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        if (collect != null) {
            if (collect.getErrorCode() == 0) {
                mArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mArticleAdapter.setArticleList(mArticleList);
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        ToastUtils.showShort("加载失败");
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadSuccess() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_HOME) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.collect(articleId);
                Event e = new Event();
                e.target = Event.TARGET_MAIN;
                e.type = Event.TYPE_START_ANIMATION;
                EventBus.getDefault().post(e);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.unCollect(articleId);
                Event e = new Event();
                e.target = Event.TARGET_MAIN;
                e.type = Event.TYPE_START_ANIMATION;
                EventBus.getDefault().post(e);
            } else if (event.type == Event.TYPE_LOGIN) {
                mArticleList.clear();
                mPresenter.refreshArticle(0);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mArticleList.clear();
                mPresenter.refreshArticle(0);
            }
        }
    }

    /**
     * 加载新文章从底部开始
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurpage++;
        mPresenter.loadArticle(mCurpage);
    }

    /**
     * 刷新文章从首页开始
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.refreshBanner();
        mCurpage = 0;
        mPresenter.refreshArticle(mCurpage);
    }
}