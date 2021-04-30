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
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.pcp.myapp.R;
import com.pcp.myapp.base.fragment.BaseFragment;
import com.pcp.myapp.base.utils.Utils;
import com.pcp.myapp.bean.EditEventBo;
import com.pcp.myapp.bean.EventBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.ui.activity.EditActivity;
import com.pcp.myapp.ui.activity.NewsActivity;
import com.pcp.myapp.utils.LogUtils;
import com.pcp.myapp.utils.MmkvUtil;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tvAllBtn)
    AppCompatTextView tvAllBtn;
    @BindView(R.id.tvHomeAdd)
    AppCompatTextView tvHomeAdd;
    @BindView(R.id.etHome)
    AppCompatEditText etHome;
    @BindView(R.id.rcHomeList)
    RecyclerView rcHomeList;
    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private MainPresenter mainPresenter;
    private final List<String> categoryList = new ArrayList<>();
    private final List<SearchBo> dataList = new ArrayList();
    private HomeListAdapter homeAdapter;
    private String curCategory = "";

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initPresenter() {
        mainPresenter = new MainPresenter(new DataManager());
    }

    @Override
    protected void init() {
        initStatusBar();
        initRecycleView();
        EventBus.getDefault().register(this);
        loadCategoryList();
        search("", "");
        //跟随光标搜索
        etHome.addTextChangedListener(new TextWatcher() {
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
        if (MmkvUtil.isAdmin()) {
            tvHomeAdd.setVisibility(View.VISIBLE);
        }
    }

    private void initRecycleView() {
        rcHomeList.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter = new HomeListAdapter(R.layout.item_home_list, dataList);
        View footView = getLayoutInflater().inflate(R.layout.common_footview, null);
        homeAdapter.addFooterView(footView);
        rcHomeList.setAdapter(homeAdapter);
    }

    @OnClick({R.id.tvAllBtn, R.id.tvHomeAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAllBtn: {
                //分类按钮
                showSelectPop();
                break;
            }
            case R.id.tvHomeAdd: {
                EditActivity.launchActivity(activity, "");
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
                                    tvAllBtn.setText("分类：" + text);
                                    curCategory = text;
                                    etHome.setText("");
                                    search(text, "");
                                }
                            }).show();
        }
    }

    private void loadCategoryList() {
        mainPresenter.getCategoryList(new NetCallBack<List<String>>() {
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
        mainPresenter.search(category, keyword, new NetCallBack<List<SearchBo>>() {
            @Override
            public void onLoadSuccess(List<SearchBo> resultList) {
                dataList.clear();
                dataList.addAll(resultList);
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }
    
    private class HomeListAdapter extends BaseQuickAdapter<SearchBo, BaseViewHolder> {

        public HomeListAdapter(int layoutResId, @Nullable List<SearchBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, SearchBo searchBo) {
            holder.setText(R.id.tvHomeName, searchBo.username);
            holder.setText(R.id.tvHomeTime, searchBo.time);
            holder.setText(R.id.tvHomeTitle, searchBo.title);
            holder.setText(R.id.tvHomeContent, searchBo.content);
            holder.setText(R.id.tvHomeCategory, "所属分类：" + searchBo.category);
            holder.itemView.setOnClickListener(v -> {
                NewsActivity.launchActivity(activity, searchBo.id + "");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EditEventBo event) {
        search(curCategory, "");
    }
}