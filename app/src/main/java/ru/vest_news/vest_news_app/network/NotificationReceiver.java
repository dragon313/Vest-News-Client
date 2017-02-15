package ru.vest_news.vest_news_app.network;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "received result: " + getResultCode());
        if (getResultCode() != Activity.RESULT_OK) {
            return;
        }
        int requestCode = intent.getIntExtra(NewsService.REQUEST_CODE, 0);
        Notification notification = intent.getParcelableExtra(NewsService.NOTIFICATION);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        notificationManager.notify(requestCode, notification);
    }
}
