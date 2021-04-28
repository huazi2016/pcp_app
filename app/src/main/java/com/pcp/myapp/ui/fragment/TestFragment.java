package com.pcp.myapp.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.pcp.myapp.R;
import com.pcp.myapp.base.fragment.BaseFragment;
import com.pcp.myapp.bean.EventBo;
import com.pcp.myapp.bean.SearchTestBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.ui.activity.NewsActivity;
import com.pcp.myapp.ui.activity.TestActivity;
import com.pcp.myapp.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class TestFragment extends BaseFragment {

    @BindView(R.id.tvTestAllBtn)
    AppCompatTextView tvTestAllBtn;

    @BindView(R.id.etTest)
    AppCompatEditText etTest;

    @BindView(R.id.rcTestList)
    RecyclerView rcTestList;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private MainPresenter mainPresenter;
    private final List<String> categoryList = new ArrayList<>();
    private final List<SearchTestBo> dataList = new ArrayList();
    private TestListAdapter testAdapter;
    private String curCategory = "";

    public static TestFragment getInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initPresenter() {
        mainPresenter = new MainPresenter(new DataManager());
    }

    @Override
    protected void init() {
        initStatusBar();
        initRecycleView();
        loadCategoryList();
        search("", "");
        //跟随光标搜索
        etTest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtils.d("okHttp==" + s.toString());
                search(curCategory, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRecycleView() {
        rcTestList.setLayoutManager(new LinearLayoutManager(getContext()));
        testAdapter = new TestListAdapter(R.layout.item_test_list, dataList);
        rcTestList.setAdapter(testAdapter);
    }

    @OnClick({R.id.tvTestAllBtn, R.id.etTest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTestAllBtn: {
                //分类按钮
                showSelectPop();
                break;
            }
            case R.id.etTest: {
                //分类按钮
                loadCategoryList();
                break;
            }
            default: {
                break;
            }
        }
    }

    private void showSelectPop() {
        if (CollectionUtils.isNotEmpty(categoryList)) {
            String[] strings = new String[categoryList.size()];
            categoryList.toArray(strings);
            new XPopup.Builder(activity)
                    .isDarkTheme(false)
                    .isDestroyOnDismiss(true)
                    .asCenterList("请选择分类", strings,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    tvTestAllBtn.setText("分类：" + text);
                                    curCategory = text;
                                    etTest.setText("");
                                    search(text, "");
                                }
                            }).show();
        }
    }

    private void loadCategoryList() {
        mainPresenter.getTestList(new NetCallBack<List<String>>() {
            @Override
            public void onLoadSuccess(List<String> data) {
                categoryList.clear();
                categoryList.addAll(data);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }

    private void search(String category, String keyword) {
        mainPresenter.searchTest(category, keyword, new NetCallBack<List<SearchTestBo>>() {
            @Override
            public void onLoadSuccess(List<SearchTestBo> resultList) {
                dataList.clear();
                dataList.addAll(resultList);
                testAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }
    
    private class TestListAdapter extends BaseQuickAdapter<SearchTestBo, BaseViewHolder> {

        public TestListAdapter(int layoutResId, @Nullable List<SearchTestBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, SearchTestBo searchBo) {
            holder.setText(R.id.tvHomeCategory, searchBo.category);
            holder.setText(R.id.tvHomeTitle, searchBo.title);
            holder.setText(R.id.tvHomeContent, searchBo.content);
            holder.itemView.setOnClickListener(v -> {
                TestActivity.launchActivity(activity, searchBo.id + "", searchBo.title, searchBo.content);
            });
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}