package ru.vest_news.vest_news.network;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.vest_news.vest_news.model.NewsItem;

public class NewsFetcher {
    private static final String TAG = "NewsFetcher";
    private static String NEWS_TO_LOAD = "50";

    public static final String BASE_URI = "http://www.vest-news.ru/";
    private static final String BASE_NEWS_API_URL = "http://www.vest-news.ru/api/news";
    private static final String LAST_NEWS_ID_URL = "http://www.vest-news.ru/api/last-news-item-id";
    private static final String P_LIMIT = "limit";


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + urlSpec);
            }

            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while ((byteRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, byteRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<NewsItem> fetchItems() {
        List<NewsItem> items = new ArrayList<>();
        try {
            String url = Uri.parse(BASE_NEWS_API_URL)
                    .buildUpon()
                    .appendQueryParameter(P_LIMIT, NEWS_TO_LOAD)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.d(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        } catch (JSONException e) {
            Log.e(TAG, "Filed to parse JSON", e);
        }
        return items;
    }

    public NewsItem getNewsById(String newsId) {
        NewsItem item = new NewsItem();
        String url = Uri.parse(BASE_NEWS_API_URL + "/" + newsId).toString();
        return null;
    }

    private void parseItems(List<NewsItem> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONArray rows = jsonBody.getJSONArray("rows");
        for (int i = 0; i < rows.length(); i++) {
            NewsItem item = new NewsItem();
            JSONObject row = rows.getJSONObject(i);
            item.setTitle(row.getString("title"));
            item.setId(row.getString("nid"));
            item.setCreated(row.getString("created"));
            item.setBody(row.getString("body"));
            item.setRubric(row.getString("rubric"));
            item.setPhotoFilePath(BASE_URI + row.getString("filepath"));
            item.setViews(row.getString("views"));
            items.add(item);
        }
    }

    public String getLastNewsId() {
        String lastIdString = "";
        try {
            String url = Uri.parse(LAST_NEWS_ID_URL).toString();
            String jsonString = getUrlString(url);
            lastIdString = jsonString.replace('\"', ' ').trim();
            Log.d(TAG, "Last news id: " + lastIdString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastIdString;
    }
}
