package com.pcp.myapp.net;

import com.google.gson.Gson;
import com.pcp.myapp.bean.LoginBo;
import com.pcp.myapp.bean.SearchBo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DataManager {

    private final ApiService apiService;

    public DataManager(){
        this.apiService = RetrofitUtils.get().retrofit().create(ApiService.class);
    }

    public Observable<BaseResponse<List<String>>> getCategoryList(){
        return apiService.getCategoryList();
    }

    public Observable<BaseResponse<LoginBo>> login(String username, String password){
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("username", username);
        jsonMap.put("password", password);
        String json = new Gson().toJson(jsonMap);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.login(body);
    }

    public Observable<BaseResponse<LoginBo>> register(String username, String password, String role){
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("username", username);
        jsonMap.put("password", password);
        jsonMap.put("role", role);
        String json = new Gson().toJson(jsonMap);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.register(body);
    }

    public Observable<BaseResponse<List<SearchBo>>> search(String category, String keyword){
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("category", category);
        jsonMap.put("keyword", keyword);
        String json = new Gson().toJson(jsonMap);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.search(body);
    }

    //public Observable<BaseResponse<List<SearchBo>>> loadNewsDetail(String category, String keyword){
    //    HashMap<String, String> jsonMap = new HashMap();
    //    jsonMap.put("category", category);
    //    jsonMap.put("keyword", keyword);
    //    String json = new Gson().toJson(jsonMap);
    //    String contentType = "application/json;charset=UTF-8";
    //    RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
    //    return apiService.search(body);
    //}

    public Observable<BaseResponse<SearchBo>> loadNewsDetail(String id){
        return apiService.loadNewsDetail(id);
    }
}
