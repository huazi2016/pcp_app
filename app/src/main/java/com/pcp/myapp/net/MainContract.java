package com.pcp.myapp.net;

import java.util.List;

public interface MainContract {

    interface View extends BaseContract.View{
        void showCategoryList(List<String> topArticleList);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void getCategoryList();
    }
}
