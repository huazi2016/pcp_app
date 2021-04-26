package com.huazi.jdemo.model;

import com.huazi.jdemo.base.model.BaseModel;
import com.huazi.jdemo.bean.me.LogoutData;
import com.huazi.jdemo.contract.setting.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/16
 * Time: 13:56
 */
public class SettingModel extends BaseModel implements Contract.ILogoutModel {
    public SettingModel() {
        setCookies(true);
    }

    @Override
    public Observable<LogoutData> logout() {
        return mApiServer.logout();
    }
}
