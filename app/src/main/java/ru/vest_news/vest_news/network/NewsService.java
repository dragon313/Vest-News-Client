package ru.vest_news.vest_news.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import java.util.List;

import ru.vest_news.vest_news.model.NewsItem;
import ru.vest_news.vest_news.utils.QueryPreferences;

public class NewsService extends IntentService {
    private static final String TAG = "NewsService";

    public NewsService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NewsService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        String lastResultId = QueryPreferences.getPrefLastResultId(this);
        List<NewsItem> items = new NewsFetcher().fetchItems();
        if (items.size() == 0) {
            return;
        }
        String resultId = items.get(0).getId();
        if (resultId.equals(lastResultId)) {
            Log.d(TAG, "Got an old result: " + resultId);
        } else {
            Log.d(TAG, "Got a new result:" + resultId);
        }
        QueryPreferences.setPrefLastResultId(this, resultId);
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

}
