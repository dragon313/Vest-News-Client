package ru.vest_news.vest_news_app.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.vest_news.vest_news_app.model.RetrofitNewsItem;

public class RetrofitNewsList {

    @SerializedName("rows")
    @Expose
    private List<RetrofitNewsItem> rows;

    public List<RetrofitNewsItem> getRows() {
        return rows;
    }

    public void setRows(List<RetrofitNewsItem> rows) {
        this.rows = rows;
    }

}