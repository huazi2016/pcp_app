package com.huazi.jdemo.net;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * api service
 */
public interface ApiService {

    /**
     * 获取首页文章列表
     * @param page
     * @return
     */
    //@GET("/article/list/{page}/json")
    //Observable<BaseResponse<Article>> getArticleList(@Path("page") int page);

    /**
     * 获取置顶文章
     * @return
     */
    //@GET("/article/top/json")
    @GET("http://103.100.208.50/api/article/category")
    Observable<BaseResponse<List<String>>> getCategoryList();
}
