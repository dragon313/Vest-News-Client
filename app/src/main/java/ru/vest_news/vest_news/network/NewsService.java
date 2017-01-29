package ru.vest_news.vest_news.network;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

import ru.vest_news.vest_news.R;
import ru.vest_news.vest_news.model.NewsItem;
import ru.vest_news.vest_news.ui.NewsListActivity;
import ru.vest_news.vest_news.utils.QueryPreferences;

public class NewsService extends IntentService {
    private static final String TAG = "NewsService";

    private static final int CONNECTION_INTERVAL = 1000 * 60; //60 секунд

    public static final String ACTION_SHOW_NOTIFICATION =
            "ru.vest_news.vest_news.SHOW_NOTIFICATION";
    public static final String PERM_PRIVATE =
            "ru.vest_news.vest_news.PRIVATE";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";

    public NewsService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NewsService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = NewsService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), CONNECTION_INTERVAL, pi);
//            Данный варант будет выводить устройство из спящего режима.
//            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), CONNECTION_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        QueryPreferences.setAlarmOn(context, isOn);
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = NewsService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
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

            NewsItem newsItem = items.get(0);
            Resources resources = getResources();
            Intent i = NewsListActivity.newIntent(this);
//            Intent i = NewsDetailActivity.newIntent(this, newsItem);
//            Log.d(TAG, "Получен заголовок: " + items.get(0).getTitle());
//            Log.d(TAG, "Получено тело новости: " + items.get(0).getBody());
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(resources.getString(R.string.new_news_title))
                    .setSmallIcon(R.drawable.ic_newspaper_report)
                    .setContentTitle(resources.getString(R.string.new_news_title))
                    .setContentText(items.get(0).getTitle())
                    .setContentIntent(pi)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .build();

//            NotificationManagerCompat notificationManager =
//                    NotificationManagerCompat.from(this);
//            notificationManager.notify(0, notification);
//
//            sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION), PERM_PRIVATE);
            showBackgroundNotification(0, notification);
        }
        QueryPreferences.setPrefLastResultId(this, resultId);
    }

    private void showBackgroundNotification(int requestCode, Notification notification) {
        Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
        i.putExtra(REQUEST_CODE, requestCode);
        i.putExtra(NOTIFICATION, notification);
        sendOrderedBroadcast(i, PERM_PRIVATE, null, null, Activity.RESULT_OK, null, null);
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

}
