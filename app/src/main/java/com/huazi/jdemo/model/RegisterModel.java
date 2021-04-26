package com.huazi.jdemo.model;

import com.huazi.jdemo.base.model.BaseModel;
import com.huazi.jdemo.bean.me.RegisterData;
import com.huazi.jdemo.contract.register.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/26
 * Time: 15:18
 */
public class RegisterModel extends BaseModel implements Contract.IRegisterModel {
    @Override
    public Observable<RegisterData> register(String userName, String passWord) {
        return mApiServer.register(userName, passWord, passWord);
    }
}
