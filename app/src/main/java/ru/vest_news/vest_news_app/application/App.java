package ru.vest_news.vest_news_app.application;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vest_news.vest_news_app.network.retorofit.VestNewsApi;

public class App extends Application {
    private static final String BASE_URI = "http://www.vest-news.ru";
    private static VestNewsApi sVestNewsList;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sVestNewsList = mRetrofit.create(VestNewsApi.class);
    }

    public static VestNewsApi getVestNewsApi() {
        return sVestNewsList;
    }
}
