package ru.vest_news.vest_news.utils;

import java.util.ArrayList;
import java.util.List;

import ru.vest_news.vest_news.model.NewsItem;
import ru.vest_news.vest_news.network.retorofit.Row;

public class NewsLab {
    private static NewsLab ourInstance = new NewsLab();
    private List<Row> mItems;

    public static NewsLab getInstance() {
        return ourInstance;
    }

    private NewsLab() {
        mItems = new ArrayList<>();
    }

    public ArrayList<Row> getItems() {
        return (ArrayList<Row>) mItems;
    }

    public void setItems(List<Row> items) {
        mItems = items;
    }

    public Row getItem(int position) {
        return mItems.get(position);
    }
}
