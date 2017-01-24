package ru.vest_news.vest_news.network;

import android.net.Uri;
import android.util.Log;
import android.widget.Gallery;

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
            String url = Uri.parse("http://www.vest-news.ru/api/news")
                    .buildUpon()
                    .appendQueryParameter("limit", NEWS_TO_LOAD)
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

    private void parseItems(List<NewsItem> items, JSONObject jsonBody) throws IOException, JSONException {

        JSONArray rows = jsonBody.getJSONArray("rows");
        Log.d(TAG, "Доступно новостей: " + rows.length());
        for (int i = 0; i < rows.length(); i++) {
            NewsItem item = new NewsItem();
            JSONObject row = rows.getJSONObject(i);
            item.setTitle(row.getString("title"));
//            Log.d(TAG, item.getTitle());
            item.setId(row.getString("nid"));
//            Log.d(TAG, item.getId());
            item.setCreated(row.getString("created"));
//            Log.d(TAG, item.getCreated());
            item.setBody(row.getString("body"));
//            Log.d(TAG, item.getBody());
//            item.setRubric(row.getString("rubric"));
//            Log.d(TAG, item.getRubric());
            item.setPhotoFilePath("http://www.vest-news.ru/" + row.getString("filepath"));
//            Log.d(TAG, item.getPhotoFilePath());
            item.setViews(row.getString("views"));
//            Log.d(TAG, item.getViews());
            items.add(item);
        }


    }
}
