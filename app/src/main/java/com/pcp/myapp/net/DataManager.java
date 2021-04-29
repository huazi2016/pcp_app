package com.pcp.myapp.net;

import com.google.gson.Gson;
import com.pcp.myapp.bean.ChatListBo;
import com.pcp.myapp.bean.ChatMsgBo;
import com.pcp.myapp.bean.LoginBo;
import com.pcp.myapp.bean.MessageListBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.bean.SearchTestBo;
import com.pcp.myapp.utils.LogUtils;
import com.pcp.myapp.utils.MmkvUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
        LogUtils.d("okhttp:==" + json);
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
        LogUtils.d("okhttp:==" + json);
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
        LogUtils.d("okhttp:==" + json);
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
        LogUtils.d("okhttp:==" + json);
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
        LogUtils.d("okhttp:==" + json);
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
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.sendMessage(body);
    }

    public Observable<BaseResponse<ChatMsgBo>> replyMsg(String id, String reply){
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("id", id);
        jsonMap.put("reply", reply);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.replyMsg(body);
    }

    public Observable<BaseResponse<ChatMsgBo>> replyTeacherMsg(String content, String student, String teacher) {
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("content", content);
        jsonMap.put("student", student);
        jsonMap.put("teacher", teacher);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.replyTeacherMsg(body);
    }

    public Observable<BaseResponse<SearchBo>> addNews(String name, String category, String title, String content) {
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("username", name);
        jsonMap.put("category", category);
        jsonMap.put("title", title);
        jsonMap.put("content", content);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.addNews(body);
    }

    public Observable<BaseResponse<SearchBo>> deleteNews(String id){
        return apiService.deleteNews(id);
    }

    public Observable<BaseResponse<SearchBo>> getCommentsList(String id){
        return apiService.getCommentsList(id);
    }

    //public Observable<BaseResponse<SearchBo>> addComments(String id){
    //    HashMap<String, String> jsonMap = new HashMap();
    //    jsonMap.put("id", id);
    //    String json = new Gson().toJson(jsonMap);
    //    LogUtils.d("okhttp:==" + json);
    //    String contentType = "application/json;charset=UTF-8";
    //    RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
    //    return apiService.addComments(body);
    //}

    public Observable<BaseResponse<SearchBo>> addComments(String id){
        return apiService.addComments(id);
    }

    public Observable<BaseResponse<SearchBo>> deleteComments(String id, String content, String username){
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("articleId", id);
        jsonMap.put("content", content);
        jsonMap.put("username", username);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.deleteComments(body);
    }
}
