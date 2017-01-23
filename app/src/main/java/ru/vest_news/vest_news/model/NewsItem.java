package ru.vest_news.vest_news.model;

public class NewsItem {
    private String mTitle;
    private String mId;
    private String mType;
    private String mCreated;
    private String mBody;
    private String mRubric;
    private String mPhotoFilePath;
    private String mViews;

    public String getTitle() {
        return mTitle;
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
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getRubric() {
        return mRubric;
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

    public String getViews() {
        return mViews;
    }

    public void setViews(String views) {
        mViews = views;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
