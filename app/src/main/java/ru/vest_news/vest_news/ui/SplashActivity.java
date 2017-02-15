package ru.vest_news.vest_news.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vest_news.vest_news.R;
import ru.vest_news.vest_news.network.NewsPreLoader;
import ru.vest_news.vest_news.network.retorofit.RetrofitNewsFetcher;
import ru.vest_news.vest_news.network.retorofit.RetrofitNewsModel;

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Тестовый код
        runRetrofitApi();

        new NewsPreLoader().execute();
        new Loading().execute();
    }

    private void runRetrofitApi() {
        RetrofitNewsFetcher.getApi().getData(5).enqueue(new Callback<RetrofitNewsModel>() {
            @Override
            public void onResponse(Call<RetrofitNewsModel> call, Response<RetrofitNewsModel> response) {
                Log.i(TAG, "Данные успешно получены.");
                if (response.body() != null) {
                    Log.i(TAG, "response.body() != null -> true");
                } else {
                    Log.i(TAG, "response.body() != null -> false");
                }
            }
            @Override
            public void onFailure(Call<RetrofitNewsModel> call, Throwable t) {
                Log.i(TAG, "Произошла ошибка при загрузке.");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    private class Loading extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Thread.sleep(2000);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(), NewsListActivity.class);
            startActivity(intent);
        }
    }
}
