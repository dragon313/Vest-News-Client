package ru.vest_news.vest_news.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
        Log.i(TAG, "Received an intent: " + intent);
    }
}
