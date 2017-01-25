package ru.vest_news.vest_news.ui;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.vest_news.vest_news.R;

public class NewsDetailFragment extends Fragment {
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Intent mIntent;
    private TextView mTitleTextView;
    private TextView mBodyTextView;
    private ImageView mPhotoImageView;


    public static NewsDetailFragment newInstance() {
        return new NewsDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getActivity().getIntent();
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_detail, container, false);
        mToolbar = (Toolbar) v.findViewById(R.id.fragment_news_detail_toolbar);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolBar();
    }

    private void setToolBar() {
        NewsDetailActivity activity = (NewsDetailActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
    }
}
