package ru.vest_news.vest_news.network.retorofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RetrofitNewsItem {
    private static final String BASE_URI = "http://www.vest-news.ru/";

    @SerializedName("nid")
    @Expose
    private String nid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("is_event")
    @Expose
    private String isEvent;
    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("rubric")
    @Expose
    private String rubric;
    @SerializedName("filepath")
    @Expose
    private String filepath;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("body")
    @Expose
    private String body;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(String isEvent) {
        this.isEvent = isEvent;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    public String getFilepath() {
        return BASE_URI+filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        Date date = new Date(Long.parseLong(created + "000"));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return format.format(date);
    }

}