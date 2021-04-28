package com.pcp.myapp.net;

import com.pcp.myapp.bean.ChatListBo;
import com.pcp.myapp.bean.LoginBo;
import com.pcp.myapp.bean.MessageListBo;
import com.pcp.myapp.bean.SearchBo;
import com.pcp.myapp.bean.SearchTestBo;

import java.util.List;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * api service
 */
public interface ApiService {

    @GET("/api/login")
    Observable<BaseResponse<LoginBo>> login(
            @Query("username") String username,
            @Query("password") String password

    );

    @POST("/api/login")
    //@Headers("Content-Type:application/json; charset=utf-8")
    Observable<BaseResponse<LoginBo>> login(@Body RequestBody body);

    @POST("/api/register")
    Observable<BaseResponse<LoginBo>> register(@Body RequestBody body);

    @GET("/api/article/category")
    Observable<BaseResponse<List<String>>> getCategoryList();

    @POST("/api/article/search")
    Observable<BaseResponse<List<SearchBo>>> search(@Body RequestBody body);

    @GET("/api/psy/test/category")
    Observable<BaseResponse<List<String>>> getTestList();

    @POST("/api/psy/search")
    Observable<BaseResponse<List<SearchTestBo>>> searchTest(@Body RequestBody body);

    @GET("/api/article/detail")
    Observable<BaseResponse<SearchBo>> loadNewsDetail(
            @Query("id") String id
    );

    @GET("/api/user/list")
    Observable<BaseResponse<List<ChatListBo>>> loadChatList(
            @Query("role") String role
    );

    @GET("/api/message/list")
    Observable<BaseResponse<List<MessageListBo>>> loadMessageList(
            @Query("teacher") String teacher
            //@Query("reply") String reply
    );

    @GET("/api/psy/my/answer")
    Observable<BaseResponse<ChatListBo>> loadAnswer(
            @Query("psyId") String psyId,
            @Query("username") String username
    );

    @POST("/api/psy/test/answer")
    Observable<BaseResponse<String>> commitAnswer(@Body RequestBody body);
}
