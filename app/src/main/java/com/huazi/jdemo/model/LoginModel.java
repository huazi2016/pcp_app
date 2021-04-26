package com.huazi.jdemo.model;

import com.huazi.jdemo.base.model.BaseModel;
import com.huazi.jdemo.bean.me.LoginData;
import com.huazi.jdemo.contract.login.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/11
 * Time: 22:16
 */
public class LoginModel extends BaseModel implements Contract.ILoginModel {
    public LoginModel() {
        setCookies(true);
    }

    @Override
    public Observable<LoginData> login(String userName, String passWord) {
        return mApiServer.login(userName, passWord);
    }
}
