package com.huazi.jdemo.model;

import com.huazi.jdemo.base.model.BaseModel;
import com.huazi.jdemo.bean.square.NavigationData;
import com.huazi.jdemo.contract.square.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/29
 * Time: 14:56
 */
public class SquareModel extends BaseModel implements Contract.ISquareModel {

    public SquareModel() {
        setCookies(false);
    }
    @Override
    public Observable<NavigationData> loadNavigation() {
        return mApiServer.loadNavigationData();
    }
}
