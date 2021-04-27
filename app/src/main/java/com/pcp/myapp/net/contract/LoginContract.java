package com.pcp.myapp.net.contract;

import com.pcp.myapp.bean.LoginBo;
import com.pcp.myapp.net.BaseContract;

import java.util.List;

public interface LoginContract {

    interface View extends BaseContract.View {
        void showCategoryList(List<String> dataList);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void getCategoryList();
    }

    interface LoginView extends BaseContract.View {
        void login(LoginBo resultBo);
    }

    interface LoginPresenter extends BaseContract.Presenter<View>{
        void getCategoryList();
    }
}
