package ru.vest_news.vest_news_app.ui;

import android.support.v4.app.Fragment;

public class NewsDetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return NewsDetailFragment.newInstance();
    }

}
