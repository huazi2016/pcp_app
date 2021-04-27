package com.pcp.myapp.base.callback;


import com.kingja.loadsir.callback.Callback;
import com.pcp.myapp.R;

/**
 * Created with Android Studio.
 * Description: 网络错误提示
 *
 * @author: Wangjianxian
 * @date: 2020/02/10
 * Time: 19:37
 */
public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.network_error;
    }
}
