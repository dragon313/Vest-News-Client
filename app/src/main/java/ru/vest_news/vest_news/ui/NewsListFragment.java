package ru.vest_news.vest_news.ui;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.vest_news.vest_news.R;
import ru.vest_news.vest_news.model.NewsItem;
import ru.vest_news.vest_news.network.NewsFetcher;
import ru.vest_news.vest_news.network.NewsService;

public class NewsListFragment extends VisibleFragment {
    private static final String TAG = "NewsListFragment";

    private RecyclerView mNewsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsAdapter mAdapter;
    private Toolbar mToolbar;
    private List<NewsItem> mItems = new ArrayList<>();


    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        updateItems();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        mToolbar = (Toolbar) v.findViewById(R.id.fragment_news_list_toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.fragment_news_list_swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                updateItems();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mNewsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_news_list_recycler_view);
        mNewsRecyclerView.setHasFixedSize(true);
        mNewsRecyclerView.setItemAnimator(itemAnimator);
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
        setupAdapter();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolBar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_news_list, menu);

        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_updating);
        if (NewsService.isServiceAlarmOn(getActivity())) {
//            toggleItem.setTitle(R.string.stop_update);
            toggleItem.setChecked(true);
        } else {
//            toggleItem.setTitle(R.string.start_update);
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
        new NewsParser().execute();
    }

    private void setToolBar() {
        NewsListActivity activity = (NewsListActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitle("");
    }

    private void setupAdapter() {
        if (isAdded()) {
            mAdapter = new NewsAdapter(mItems);
            mAdapter.notifyDataSetChanged();
            mNewsRecyclerView.setAdapter(mAdapter);
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private List<NewsItem> mNewsItems;

        public NewsAdapter(List<NewsItem> newsItems) {
            mNewsItems = newsItems;
        }

        @Override
        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_list_item, parent, false);
            return new NewsHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            final NewsItem item = mNewsItems.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = NewsDetailFragment.getIntent(getActivity(), item);
                    startActivity(detailIntent);
                }
            });
            holder.bindNewsItem(item);
        }

        @Override
        public int getItemCount() {
            return mNewsItems.size();
        }
    }

    private class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private ImageView mPhotoImageView;
        private TextView mBodyTextView;
        private TextView mDateTextView;
        private TextView mRubricTextView;
        private TextView mViewCounterTextView;

        public NewsHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.news_list_item_title);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.news_list_item_photo);
            mBodyTextView = (TextView) itemView.findViewById(R.id.news_list_item_body_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.news_list_item_date_text_view);
            mRubricTextView = (TextView) itemView.findViewById(R.id.news_list_rubric_text_view);
            mViewCounterTextView = (TextView) itemView.findViewById(R.id.news_list_view_counter_text_view);

        }

        public void bindNewsItem(NewsItem item) {
            mTitleTextView.setText(item.getTitle());
            mBodyTextView.setText(Html.fromHtml(item.getBody()));
            mDateTextView.setText(item.getDate());
            mRubricTextView.setText(item.getRubric());
            mViewCounterTextView.setText(item.getViews());
            Picasso.with(getActivity())
                    .load(Uri.parse(item.getPhotoFilePath()))
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(mPhotoImageView);
        }
    }

    private class NewsParser extends AsyncTask<Void, Void, List<NewsItem>> {
        private static final String TAG = "NewsParser";

        @Override
        protected List<NewsItem> doInBackground(Void... params) {
            return new NewsFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
//            Log.d(TAG, String.valueOf(mItems != null));
            mItems = newsItems;
            setupAdapter();
        }
    }

}
