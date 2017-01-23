package ru.vest_news.vest_news.ui;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_list_item, parent, false);
            return new NewsHolder(view);
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
            mItems = newsItems;
            setupAdapter();
        }
    }

}
