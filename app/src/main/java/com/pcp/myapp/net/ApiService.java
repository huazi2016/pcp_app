package com.pcp.myapp.net;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * api service
 */
public interface ApiService {

    @GET("/api/article/category")
    Observable<BaseResponse<List<String>>> getCategoryList();
}
