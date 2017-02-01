package ru.vest_news.vest_news.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.List;

import ru.vest_news.vest_news.model.NewsItem;
import ru.vest_news.vest_news.ui.NewsListActivity;
import ru.vest_news.vest_news.utils.NewsLab;

public class NewsPreLoader extends AsyncTask<Void, Void, List<NewsItem>> {
    private static final String TAG = "NewsParser";

    @Override
    protected List<NewsItem> doInBackground(Void... params) {
        return new NewsFetcher().fetchItems();
    }

    @Override
    protected void onPostExecute(List<NewsItem> newsItems) {
        NewsLab.getInstance().setItems(newsItems);
    }
}
