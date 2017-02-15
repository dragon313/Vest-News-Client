package ru.vest_news.vest_news_app.network.retorofit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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