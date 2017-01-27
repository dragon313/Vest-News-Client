package ru.vest_news.vest_news.network;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.List;

import ru.vest_news.vest_news.R;
import ru.vest_news.vest_news.model.NewsItem;
import ru.vest_news.vest_news.ui.NewsListActivity;
import ru.vest_news.vest_news.utils.QueryPreferences;

public class NewsService extends IntentService {
    private static final String TAG = "NewsService";

    private static final int CONNECTION_INTERVAL = 1000 * 60; //60 секунд

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

            Resources resources = getResources();
            Intent i = NewsListActivity.newIntent(this);
            PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
            Uri ringURI =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(resources.getString(R.string.new_news_title))
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle(resources.getString(R.string.new_news_title))
                    .setContentText(items.get(0).getTitle())
                    .setContentIntent(pi)
                    .setSound(ringURI)
                    .setAutoCancel(true)
                    .build();
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);
            notificationManager.notify(0, notification);

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
