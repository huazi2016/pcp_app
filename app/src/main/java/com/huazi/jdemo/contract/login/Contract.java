package com.huazi.jdemo.contract.login;

import com.huazi.jdemo.base.interfaces.IBaseView;
import com.huazi.jdemo.bean.me.LoginData;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/11
 * Time: 22:15
 */
public class Contract {
    public interface ILoginModel {
        /**
         * 登录
         * @param userName
         * @param passWord
         * @return
         */
        Observable<LoginData> login(String userName, String passWord);

    }

    public interface ILoginView extends IBaseView {
        void onLogin (LoginData loginData);

    }

    public interface ILoginPresenter {
        void login (String userName, String passWord);
    }
}
