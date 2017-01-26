package ru.vest_news.vest_news.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import ru.vest_news.vest_news.R;
import ru.vest_news.vest_news.model.NewsItem;

public class NewsDetailFragment extends Fragment {
    private static final String TAG = "NewsDetailFragment";

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_BODY = "EXTRA_BODY";
    public static final String EXTRA_CREATED = "EXTRA_CREATED";
    public static final String EXTRA_RUBRIC = "EXTRA_RUBRIC";
    public static final String EXTRA_VIEWS = "EXTRA_VIEWS";
    public static final String EXTRA_PHOTO_FILE_PATH = "EXTRA_PHOTO_FILE_PATH";

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mToolbarTitle;
    private Intent mIntent;
    private WebView mBodyTextView;
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
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.fragment_news_detail_collapsing_toolbar);
        mToolbarTitle = (TextView) v.findViewById(R.id.fragment_news_detail_title_text_view);
        mPhotoImageView = (ImageView) v.findViewById(R.id.fragment_news_detail_photo_image_view);
        mBodyTextView = (WebView) v.findViewById(R.id.fragment_news_detail_body_text_view);
        updateUI();
        mCollapsingToolbarLayout.post(new Runnable() {
            @Override
            public void run() {
                int tvHeight = mToolbarTitle.getHeight();
                int tbHeight = mToolbar.getHeight();
                if (tvHeight > tbHeight) {
                    CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) mToolbar.getLayoutParams();
                    params.bottomMargin = tvHeight - tbHeight;
                    mToolbar.setLayoutParams(params);
                }
            }
        });
        return v;
    }

    private void updateUI() {
        Picasso.with(getActivity())
                .load(mIntent.getStringExtra(EXTRA_PHOTO_FILE_PATH))
                .error(R.drawable.logo)
                .into(mPhotoImageView);
        mToolbarTitle.setText(mIntent.getStringExtra(EXTRA_TITLE));
        mBodyTextView.loadData(mIntent.getStringExtra(EXTRA_BODY), "text/html; charset=utf-8", "UTF-8");
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolBar();
    }

    private void setToolBar() {
        NewsDetailActivity activity = (NewsDetailActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    public static Intent getIntent(Context context, NewsItem item) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(EXTRA_TITLE, item.getTitle());
        intent.putExtra(EXTRA_BODY, item.getBody());
        intent.putExtra(EXTRA_CREATED, item.getCreated());
        intent.putExtra(EXTRA_RUBRIC, item.getRubric());
        intent.putExtra(EXTRA_VIEWS, item.getViews());
        intent.putExtra(EXTRA_PHOTO_FILE_PATH, item.getPhotoFilePath());
        return intent;
    }
}
