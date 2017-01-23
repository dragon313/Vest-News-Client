package ru.vest_news.vest_news.ui;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.vest_news.vest_news.R;
import ru.vest_news.vest_news.model.NewsItem;
import ru.vest_news.vest_news.network.NewsFetcher;

public class NewsListFragment extends Fragment {
    private static final String TAG = "NewsListFragment";
    RecyclerView mNewsRecyclerView;
    private List<NewsItem> mItems = new ArrayList<>();


    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new NewsParser().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);

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
        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mNewsRecyclerView.setAdapter(new NewsAdapter(mItems));
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private List<NewsItem> mNewsItems;

        public NewsAdapter(List<NewsItem> newsItems) {
            mNewsItems = newsItems;
        }

        @Override
        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(getActivity());
            return new NewsHolder(textView);
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            NewsItem item = mNewsItems.get(position);
            holder.bindNewsItem(item);
        }

        @Override
        public int getItemCount() {
            return mNewsItems.size();
        }
    }

    private class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;

        public NewsHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }

        public void bindNewsItem(NewsItem item) {
            mTitleTextView.setText(item.getTitle());
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
            mItems = newsItems;
            setupAdapter();
        }
    }

}
