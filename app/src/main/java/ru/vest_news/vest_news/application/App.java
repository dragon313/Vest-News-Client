package ru.vest_news.vest_news.application;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vest_news.vest_news.network.retorofit.VestNewsApi;

public class App extends Application {
    private static final String BASE_URI = "http://www.vest-news.ru";
    private static VestNewsApi sVestNewsApi;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sVestNewsApi = mRetrofit.create(VestNewsApi.class);
    }

    public static VestNewsApi getApi() {
        return sVestNewsApi;
    }
}
