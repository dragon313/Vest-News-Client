package ru.vest_news.vest_news.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import ru.vest_news.vest_news.R;
import ru.vest_news.vest_news.model.NewsItem;
import ru.vest_news.vest_news.network.NewsFetcher;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Loading().execute();
    }

    public class Loading extends AsyncTask<Void, Void, List<NewsItem>> {
        private static final String TAG = "NewsParser";

        @Override
        protected List<NewsItem> doInBackground(Void... params) {
            return new NewsFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
//            Log.d(TAG, String.valueOf(mItems != null));
//            Проверять новые листы нужно здесь, но как нужно подумать.
//            mItems = newsItems;
//            setupAdapter();
        }
    }
    }

}
