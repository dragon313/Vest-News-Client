package ru.vest_news.vest_news.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {
    private static final String PREF_LAST_RESULT_ID = "lastResult";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";


    public static String getPrefLastResultId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_LAST_RESULT_ID, null);
    }

    public static void setPrefLastResultId(Context context, String lastResultId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_RESULT_ID, lastResultId)
                .apply();
    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, false);
    }

    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn)
                .apply();
    }
}
