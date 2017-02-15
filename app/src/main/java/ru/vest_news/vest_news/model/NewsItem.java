package ru.vest_news.vest_news.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsItem {
    private static final String TAG = "RetrofitNewsItem";
    private String mTitle;
    private String mId;
    private String mType;
    private String mCreated;
    private String mBody;
    private String mRubric;
    private String mPhotoFilePath;
    private ArrayList<String> mPhotoFilePaths;
    private String mViews;

    public String getTitle() {
        return mTitle.trim();
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

    public String getBody() {
        return mBody.trim();
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getRubric() {
        if (mRubric == null) {
            return "";
        } else {
            return mRubric;
        }
    }

    public void setRubric(String rubric) {
        mRubric = rubric;
    }

    public String getPhotoFilePath() {
        return mPhotoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        mPhotoFilePath = photoFilePath;
    }

    public ArrayList<String> getPhotoFilePaths() {
        return mPhotoFilePaths;
    }
    public void setPhotoFilePaths(ArrayList<String> photoFilePaths) {
        mPhotoFilePaths = photoFilePaths;
    }

    public String getViews() {
        if (mViews == null) {
            return "0";
        } else
            return mViews;
    }

    public void setViews(String views) {
        mViews = views;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public String getDate() {
        Date date = new Date(Long.parseLong(mCreated + "000"));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return format.format(date);
    }

    public void addPhotoFilePath(String path) {
        mPhotoFilePaths.add(path);
    }
}
