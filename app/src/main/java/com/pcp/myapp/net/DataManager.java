package com.pcp.myapp.net;

import com.google.gson.Gson;
import com.pcp.myapp.bean.ChatListBo;
import com.pcp.myapp.bean.ChatMsgBo;
import com.pcp.myapp.bean.LoginBo;
import com.pcp.myapp.bean.MessageListBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.bean.SearchTestBo;
import com.pcp.myapp.utils.MmkvUtil;

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

    public Observable<BaseResponse<List<String>>> getCategoryList(){
        return apiService.getCategoryList();
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

    public Observable<BaseResponse<List<String>>> getTestList(){
        return apiService.getTestList();
    }

    public Observable<BaseResponse<List<SearchTestBo>>> searchTest(String category, String keyword){
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("category", category);
        jsonMap.put("keyword", keyword);
        String json = new Gson().toJson(jsonMap);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.searchTest(body);
    }

    public Observable<BaseResponse<SearchBo>> loadNewsDetail(String id){
        return apiService.loadNewsDetail(id);
    }

    public Observable<BaseResponse<List<ChatListBo>>> loadChatList(String role){
        return apiService.loadChatList(role);
    }

    public Observable<BaseResponse<List<MessageListBo>>> loadMessageList(String teacher){
        return apiService.loadMessageList(teacher);
    }

    public Observable<BaseResponse<ChatListBo>> loadAnswer(String psyId){
        return apiService.loadAnswer(psyId, MmkvUtil.getUserName());
    }

    public Observable<BaseResponse<String>> commitAnswer(String psyId, String answer){
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("psyId", psyId);
        jsonMap.put("username", MmkvUtil.getUserName());
        jsonMap.put("answer", answer);
        String json = new Gson().toJson(jsonMap);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.commitAnswer(body);
    }

    public Observable<BaseResponse<List<ChatMsgBo>>> getMsgList(String student, String teacher, String id){
        return apiService.getMsgList(student, teacher, id);
    }

    public Observable<BaseResponse<ChatMsgBo>> sendMessage(String send, String receive, String content){
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("send", send);
        jsonMap.put("receive", receive);
        jsonMap.put("content", content);
        String json = new Gson().toJson(jsonMap);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.sendMessage(body);
    }
}
