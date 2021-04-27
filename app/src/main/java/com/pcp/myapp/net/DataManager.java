package com.pcp.myapp.net;

import java.util.List;

import io.reactivex.Observable;

public class DataManager {

    private final ApiService apiService;

    public DataManager(){
        this.apiService = RetrofitUtils.get().retrofit().create(ApiService.class);
    }

    public Observable<BaseResponse<List<String>>> getCategoryList(){
        return apiService.getCategoryList();
    }
}
