package com.huazi.jdemo.model;

import com.huazi.jdemo.base.model.BaseModel;
import com.huazi.jdemo.bean.me.IntegralData;
import com.huazi.jdemo.contract.me.Contract;

import io.reactivex.Observable;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/31
 * Time: 14:19
 */
public class MeModel extends BaseModel implements Contract.IMeModel {
    public MeModel() {
        setCookies(false);
    }

    @Override
    public Observable<IntegralData> loadIntegralData() {
        return mApiServer.loadIntegralData();
    }

    @Override
    public Observable<IntegralData> refreshIntegralData() {
        return mApiServer.loadIntegralData();
    }


}
