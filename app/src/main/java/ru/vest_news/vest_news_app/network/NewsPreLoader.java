package ru.vest_news.vest_news_app.network;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ru.vest_news.vest_news_app.model.NewsItem;
import ru.vest_news.vest_news_app.utils.NewsLab;

public class NewsPreLoader extends AsyncTask<Void, Void, List<NewsItem>> {
    private static final String TAG = "NewsParser";
    private NewsLab mNewsLab = NewsLab.getInstance();

    @Override
    protected List<NewsItem> doInBackground(Void... params) {
        return new NewsFetcher().fetchItems();
    }

    @Override
    protected void onPostExecute(List<NewsItem> newsItems) {
//        mNewsLab.setItems(newsItems);
        Log.d(TAG, "Новостей: " + mNewsLab.getItems().size());
    }
}
