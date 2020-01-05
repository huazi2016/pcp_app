package com.wjx.android.wanandroidmvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;

import com.wjx.android.wanandroidmvp.ui.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/26
 * Time: 20:13
 */
public class MeFragment extends Fragment {
    private TextView mName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view =  inflater.inflate(R.layout.me_fragment, container, false);
        mName = view.findViewById(R.id.me_name);
        ViewGroup meCollect = view.findViewById(R.id.me_collect);
        TextView mec = meCollect.findViewById(R.id.mec);
        String loginUser = LoginUtils.getLoginUser();
        if (!TextUtils.isEmpty(loginUser)) {
            mName.setText(loginUser);
        }
        mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), CollectActivity.class);
//                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_MENU) {
            if (event.type == Event.TYPE_LOGIN) {
                mName.setText(event.data);
            }
        }
    }
}
