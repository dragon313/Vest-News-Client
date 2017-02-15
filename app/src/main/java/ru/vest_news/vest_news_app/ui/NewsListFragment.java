package ru.vest_news.vest_news_app.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.vest_news.vest_news_app.R;
import ru.vest_news.vest_news_app.network.NewsFetcher;
import ru.vest_news.vest_news_app.network.NewsService;
import ru.vest_news.vest_news_app.network.retorofit.RetrofitNewsItem;
import ru.vest_news.vest_news_app.utils.NewsLab;

public class NewsListFragment extends VisibleFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "NewsListFragment";

    private RecyclerView mNewsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsAdapter mAdapter;
    private Toolbar mToolbar;
    private Drawer mDrawer;
    private boolean isFirstStart = true;


    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        mToolbar = (Toolbar) v.findViewById(R.id.fragment_news_list_toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.fragment_news_list_swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mNewsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_news_list_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        if (NewsLab.getInstance().getItems() == null) {
            updateItems();
        }
        setupAdapter();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstStart) {
            Log.d(TAG, "Первый запуск");
            isFirstStart = false;
        } else {
            Log.d(TAG, "Непервый запуск");
            updateItems();
        }
        setToolBar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_news_list, menu);

        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_updating);
        if (NewsService.isServiceAlarmOn(getActivity())) {
            toggleItem.setChecked(true);
        } else {
            toggleItem.setChecked(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_toggle_updating:
                boolean shouldStartAlarm = !NewsService.isServiceAlarmOn(getActivity());
                NewsService.setServiceAlarm(getActivity(), shouldStartAlarm);
                getActivity().invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateItems() {
        NewsFetcher.updateNewsList();
        setupAdapter();
    }

    private void setToolBar() {
        NewsListActivity activity = (NewsListActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitle("Новости");
        setUpNavigationDrawer();
    }

    private void setUpNavigationDrawer() {
        PrimaryDrawerItem news = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.drawer_item_news);
        PrimaryDrawerItem weather = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.drawer_item_weather);
        SecondaryDrawerItem settings = new SecondaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.drawer_item_settings);
        PrimaryDrawerItem contacts = new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.drawer_item_contacts);
        SecondaryDrawerItem about = new SecondaryDrawerItem()
                .withIdentifier(5)
                .withName(R.string.drawer_item_about);


        mDrawer = new DrawerBuilder()
                .withActivity(getActivity())
                .withToolbar(mToolbar)
                .withSelectedItem(-1)
                .withHeader(R.layout.drawer_header)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        news,
                        weather,
                        contacts,
                        new DividerDrawerItem(),
                        settings,
                        about
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()) {
                            case 1:
                                updateItems();
                                mDrawer.closeDrawer();
                                return true;
                            case 2:
                                Toast.makeText(getActivity(), getString(R.string.development_in_progress), Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                return true;
                            case 3:
                                Toast.makeText(getActivity(), getString(R.string.development_in_progress), Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                return true;
                            case 4:
                                Toast.makeText(getActivity(), getString(R.string.development_in_progress), Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                return true;
                            case 5:
                                Toast.makeText(getActivity(), getString(R.string.development_in_progress), Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                return true;
                            default:
                                return true;
                        }
                    }
                })
                .build();
    }

    private void setupAdapter() {
        if (isAdded()) {
            mAdapter = new NewsAdapter(NewsLab.getInstance().getItems());
            mNewsRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        updateItems();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private static final String TAG = "NewsAdapter";
        private ArrayList<RetrofitNewsItem> mNewsList = new ArrayList<>();

        public NewsAdapter(ArrayList<RetrofitNewsItem> retrofitNewsItems) {
            mNewsList = retrofitNewsItems;
            Log.d(TAG, "mNewsList.size() = " + mNewsList.size());
        }

        @Override
        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_list_item_small, parent, false);
            return new NewsHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            final RetrofitNewsItem item = mNewsList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = NewsDetailFragment.getIntent(getActivity(), item.getNid());
                    startActivity(detailIntent);
                }
            });
            holder.bindNewsItem(item);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    private class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private ImageView mPhotoImageView;
        private TextView mDateTextView;
        private TextView mRubricTextView;
        private TextView mViewCounterTextView;
        private Typeface mTitleTypeFace;

        public NewsHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.news_list_item_title);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                mTitleTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
                mTitleTextView.setTypeface(mTitleTypeFace);
            }
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.news_list_item_photo);
            mDateTextView = (TextView) itemView.findViewById(R.id.news_list_item_date_text_view);
            mRubricTextView = (TextView) itemView.findViewById(R.id.news_list_rubric_text_view);
            mViewCounterTextView = (TextView) itemView.findViewById(R.id.news_list_view_counter_text_view);

        }

        public void bindNewsItem(RetrofitNewsItem item) {
            mTitleTextView.setText(item.getTitle());
            mDateTextView.setText(item.getDate());
            mRubricTextView.setText(item.getRubric());
            mViewCounterTextView.setText(item.getViews());
            Picasso.with(getActivity())
                    .load(Uri.parse(item.getFilepath()))
                    .placeholder(R.drawable.logo_rectangle)
                    .error(R.drawable.logo_rectangle)
                    .into(mPhotoImageView);
        }
    }
}
