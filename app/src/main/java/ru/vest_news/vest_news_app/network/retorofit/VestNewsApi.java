package ru.vest_news.vest_news_app.network.retorofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vest_news.vest_news_app.model.RetrofitNewsList;

public interface VestNewsApi {
    @GET("/api/news")
    Call<RetrofitNewsList> getNewsList(@Query("limit") Integer count);

    @GET("/api/last-news-item-id")
    Call<String> getLastNewsId();
}
