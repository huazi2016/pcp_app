package com.pcp.myapp.net;


/**
 * author : huazi
 * time   : 2021/4/27
 * desc   :
 */
public interface NetCallBack<T> {
    void onLoadSuccess(T data);
    void onLoadFailed(String errMsg);
}
