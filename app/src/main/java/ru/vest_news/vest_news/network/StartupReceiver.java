package ru.vest_news.vest_news.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ru.vest_news.vest_news.utils.QueryPreferences;

public class StartupReceiver extends BroadcastReceiver {
    private static final String TAG = "StartupReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received broadcast intent: " + intent.getAction());

        boolean isOn = QueryPreferences.isAlarmOn(context);
        NewsService.setServiceAlarm(context, isOn);
    }
}
