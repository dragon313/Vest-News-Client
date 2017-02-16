package ru.vest_news.vest_news_app.network;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vest_news.vest_news_app.application.App;
import ru.vest_news.vest_news_app.model.NewsLab;
import ru.vest_news.vest_news_app.model.RetrofitNewsList;

public class NewsFetcher {
    private static final String TAG = "NewsFetcher";

    public String getLastNewsId() {
        String result = "";
        try {
            result = App.getVestNewsApi().getLastNewsId().execute().body();
            Log.i(TAG, "Номер последней новости из NewsFetcher: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.replace('\"', ' ').trim();
    }

    public static void updateNewsList(int count) {
            App.getVestNewsApi().getNewsList(count).enqueue(new Callback<RetrofitNewsList>() {
                @Override
                public void onResponse(Call<RetrofitNewsList> call, Response<RetrofitNewsList> response) {
                    Log.i(TAG, "Данные успешно получены.");
                    if (response.body() != null) {
                        NewsLab.getInstance().setItems(response.body().getRows());
                    } else {
                        Log.i(TAG, "response.body() != null -> false");
                    }
                }
                @Override
                public void onFailure(Call<RetrofitNewsList> call, Throwable t) {
                    Log.i(TAG, "Произошла ошибка при загрузке.");
                }
            });
    }
}
