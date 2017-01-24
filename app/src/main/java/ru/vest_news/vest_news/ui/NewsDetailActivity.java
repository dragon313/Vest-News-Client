package ru.vest_news.vest_news.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsDetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return NewsDetailFragment.newInstance();
    }

}
