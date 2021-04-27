package com.pcp.myapp.net;

import com.pcp.myapp.bean.LoginBo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class DataManager {

    private final ApiService apiService;

    public DataManager(){
        this.apiService = RetrofitUtils.get().retrofit().create(ApiService.class);
    }

    public Observable<BaseResponse<LoginBo>> login(String username, String password){
        Map<String, String> jsonMap = new HashMap();
        jsonMap.put("username", username);
        jsonMap.put("password", password);
        RequestBody body = null;
        //try {
        //    String contentType = "Content-Type, application/json";
        //    body = RequestBody.create(contentType, jsonMap.toString());
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        return apiService.login(body);
    }

    public Observable<BaseResponse<List<String>>> getCategoryList(){
        return apiService.getCategoryList();
    }
}
