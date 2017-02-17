package ru.vest_news.vest_news_app.model;

import java.util.ArrayList;
import java.util.List;

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

    public RetrofitNewsItem getItem(String nid) {
        for (RetrofitNewsItem item : mItems) {
            if (item.getNid().equalsIgnoreCase(nid)) {
                return item;
            }
        }
        return null;
    }
}
