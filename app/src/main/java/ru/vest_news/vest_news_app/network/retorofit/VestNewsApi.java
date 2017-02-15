package ru.vest_news.vest_news_app.network.retorofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VestNewsApi {
    @GET("/api/news")
    Call<RetrofitNewsList> getNewsList(@Query("limit") Integer count);
}
