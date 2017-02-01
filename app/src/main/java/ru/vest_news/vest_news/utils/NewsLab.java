package ru.vest_news.vest_news.utils;

import java.util.ArrayList;
import java.util.List;

import ru.vest_news.vest_news.model.NewsItem;

public class NewsLab {
    private static NewsLab ourInstance = new NewsLab();
    private List<NewsItem> mItems;

    public static NewsLab getInstance() {
        return ourInstance;
    }

    private NewsLab() {
        mItems = new ArrayList<>();
    }

    public List<NewsItem> getItems() {
        return mItems;
    }

    public void setItems(List<NewsItem> items) {
        mItems = items;
    }

    public NewsItem getItem(int position) {
        return mItems.get(position);
    }
}
