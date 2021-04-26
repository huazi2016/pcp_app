package com.huazi.jdemo.contract.register;

import com.huazi.jdemo.base.interfaces.IBaseView;
import com.huazi.jdemo.bean.me.RegisterData;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description: 注册契约类
 *
 * @author: Wangjianxian
 * @date: 2020/01/26
 * Time: 15:15
 */
public class Contract {

    public interface IRegisterModel {
        Observable<RegisterData> register(String userName, String passWord);
    }

    public interface IRegisterView extends IBaseView {
        void onRegister(RegisterData registerData);
    }

    public interface IRegisterPresenter {
        void register(String userName, String passWord);
    }
}
