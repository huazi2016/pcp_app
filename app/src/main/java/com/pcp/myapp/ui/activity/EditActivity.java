package com.pcp.myapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.pcp.myapp.R;
import com.pcp.myapp.base.activity.BaseActivity;
import com.pcp.myapp.bean.EditEventBo;
import com.pcp.myapp.bean.EventBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.net.DataManager;
import com.pcp.myapp.net.MainPresenter;
import com.pcp.myapp.net.NetCallBack;
import com.pcp.myapp.utils.LogUtils;
import com.pcp.myapp.utils.MmkvUtil;
import com.pcp.myapp.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditActivity extends BaseActivity {

    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.tvPbName)
    AppCompatTextView tvPbName;
    @BindView(R.id.tvPbAllBtn)
    AppCompatTextView tvPbAllBtn;
    @BindView(R.id.etPbDesc)
    AppCompatEditText etPbDesc;
    @BindView(R.id.etFbTitle)
    AppCompatEditText etFbTitle;
    @BindView(R.id.tvPbSubmit)
    AppCompatTextView tvPbSubmit;

    private MainPresenter editPresenter;
    private final static String NEWS_ID = "news_id";
    private String curCategory = "";
    private final List<String> categoryList = new ArrayList<>();

    public static void launchActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, EditActivity.class);
        intent.putExtra(NEWS_ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvCommonTitle.setText("????????????");
        String name = "?????????: " + MmkvUtil.getUserName();
        String time = "      ??????: " + TimeUtil.getNowDate();
        tvPbName.setText(name + time);
        loadCategoryList();
        if (getIntent() != null) {
            String id = getIntent().getStringExtra(NEWS_ID);

        }
    }

    @Override
    protected void initPresenter() {
        editPresenter = new MainPresenter(new DataManager());
    }

    private void loadNewsDetail(String id) {
        editPresenter.loadNewsDetail(id, new NetCallBack<SearchBo>() {
            @Override
            public void onLoadSuccess(SearchBo resultList) {

            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }

    @OnClick({R.id.ivCommonBack, R.id.tvPbSubmit, R.id.tvPbAllBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCommonBack:
                finish();
                break;
            case R.id.tvPbSubmit:
                String name = tvPbName.getText().toString();
                String title = etFbTitle.getText().toString();
                String content = etPbDesc.getText().toString();
                if (TextUtils.isEmpty(curCategory)) {
                    ToastUtils.showShort("???????????????");
                    return;
                }
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.showShort("??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                addNews(MmkvUtil.getUserName(), curCategory, title, content);
                break;
            case R.id.tvPbAllBtn:
                showSelectPop();
                break;
        }
    }

    private void addNews(String name, String category, String title, String content) {
        editPresenter.addNews(name, category, title, content, new NetCallBack<SearchBo>() {
            @Override
            public void onLoadSuccess(SearchBo dataBo) {
                ToastUtils.showShort("????????????");
                EventBus.getDefault().post(new EditEventBo());
                //setResult(1001);
                finish();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtils.showShort("????????????????????????!");
                LogUtils.e("addNews_err==" + errMsg);
            }
        });
    }

    private void loadCategoryList() {
        editPresenter.getCategoryList(new NetCallBack<List<String>>() {
            @Override
            public void onLoadSuccess(List<String> data) {
                categoryList.clear();
                if (CollectionUtils.isNotEmpty(data)) {
                    data.remove(0);
                }
                categoryList.addAll(data);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                LogUtils.e("loadCategoryList_err==" + errMsg);
            }
        });
    }

    private void showSelectPop() {
        if (CollectionUtils.isNotEmpty(categoryList)) {
            String[] strings = new String[categoryList.size()];
            categoryList.toArray(strings);
            new XPopup.Builder(activity)
                    .isDarkTheme(false)
                    .isDestroyOnDismiss(true)
                    .asCenterList("???????????????", strings,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    tvPbAllBtn.setText("?????????" + text);
                                    curCategory = text;
                                }
                            }).show();
        }
    }
}
