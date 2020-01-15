package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 15:13
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationHolder> {

    private Context mContext;

    private List<NavigationData.DataBean> mNavigationBeans;

    public void setBeans(NavigationData navigationData) {
        mNavigationBeans = navigationData.getData();
    }

    public NavigationAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public NavigationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_navigation, parent, false);
        return new NavigationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationHolder holder, int position) {
        if (mNavigationBeans != null) {
            List<NavigationData.DataBean.ArticlesBean> articlesBean = mNavigationBeans.get(position).getArticles();

            holder.mItemTitle.setText(Html.fromHtml(mNavigationBeans.get(position).getName(), Html.FROM_HTML_MODE_COMPACT));
            holder.mTagFlowLayout.setAdapter(new TagAdapter(articlesBean) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView tagView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_layout, parent, false);
                    tagView.setText(articlesBean.get(position).getTitle());
                    tagView.setTextColor(Constant.randomColor());
                    holder.mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            JumpWebUtils.startWebView(mContext,
                                    articlesBean.get(position).getTitle(),
                                    articlesBean.get(position).getLink());
                            return true;
                        }
                    });
                    return tagView;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mNavigationBeans == null ? 0 : mNavigationBeans.size();
    }

    class NavigationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_system_title)
        TextView mItemTitle;
        @BindView(R.id.item_system_flowlayout)
        TagFlowLayout mTagFlowLayout;

        public NavigationHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}