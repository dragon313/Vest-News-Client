package ru.vest_news.vest_news_app.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class NewsListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, NewsListActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return NewsListFragment.newInstance();
    }
}
