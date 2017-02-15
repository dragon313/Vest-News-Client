package ru.vest_news.vest_news.utils;

import java.util.ArrayList;
import java.util.List;

import ru.vest_news.vest_news.network.retorofit.RetrofitNewsItem;

public class NewsLab {
    private static NewsLab ourInstance = new NewsLab();
    private List<RetrofitNewsItem> mItems;

    public static NewsLab getInstance() {
        return ourInstance;
    }

    private NewsLab() {
        mItems = new ArrayList<>();
    }

    public ArrayList<RetrofitNewsItem> getItems() {
        return (ArrayList<RetrofitNewsItem>) mItems;
    }

    public void setItems(List<RetrofitNewsItem> items) {
        mItems = items;
    }

    public RetrofitNewsItem getItem(int position) {
        return mItems.get(position);
    }
}
