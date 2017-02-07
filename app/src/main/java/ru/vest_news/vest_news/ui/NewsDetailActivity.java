package ru.vest_news.vest_news.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.vest_news.vest_news.model.NewsItem;

public class NewsDetailActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, NewsItem item) {
        return NewsDetailFragment.getIntent(context, item.getId());
    }

    @Override
    protected Fragment createFragment() {
        return NewsDetailFragment.newInstance();
    }

}
