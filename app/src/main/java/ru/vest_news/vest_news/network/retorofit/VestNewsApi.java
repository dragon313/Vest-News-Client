package ru.vest_news.vest_news.network.retorofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VestNewsApi {
    @GET("/api/news")
    Call<RetrofitNewsModel> getData(@Query("limit") Integer count);
}
