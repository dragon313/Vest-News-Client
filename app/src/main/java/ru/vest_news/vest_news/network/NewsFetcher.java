package ru.vest_news.vest_news.network;

import android.net.Uri;
import android.util.Log;
import android.widget.Gallery;

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
            String url = Uri.parse("http://www.vest-news.ru/api/news/")
                    .buildUpon()
                    .appendPath(getLastNewsUri())
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        } catch (JSONException e) {
            Log.e(TAG, "Filed to parse JSON", e);
        }
        return items;
    }

    //Для начала напишем метод, который возвращает урл последней новости.
    public String getLastNewsUri() throws IOException {
        String lastNewsId = getUrlString("http://www.vest-news.ru/api/last-news-item-id");
        lastNewsId = lastNewsId.replace('\"', ' ').trim();
        Log.i(TAG, "ID последней новости: " + lastNewsId);
        return lastNewsId;
    }

    private void parseItems(List<NewsItem> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONObject newsItemJsonObject = jsonBody.getJSONObject("article");

        NewsItem item = new NewsItem();
        item.setTitle(newsItemJsonObject.getString("title"));
        Log.d(TAG, item.getTitle());
        item.setId(newsItemJsonObject.getString("nid"));
        Log.d(TAG, item.getId());
        item.setCreated(newsItemJsonObject.getString("created"));
        Log.d(TAG, item.getCreated());
        item.setType(newsItemJsonObject.getString("type"));
        Log.d(TAG, item.getType());
        item.setBody(newsItemJsonObject.getString("body"));
        Log.d(TAG, item.getBody());
        item.setRubric(newsItemJsonObject.getString("rubric"));
        Log.d(TAG, item.getRubric());
        item.setPhotoFilePath("http://www.vest-news.ru/"+newsItemJsonObject.getString("filepath"));
        Log.d(TAG, item.getPhotoFilePath());
        item.setViews(newsItemJsonObject.getString("views"));
        Log.d(TAG, item.getViews());

        items.add(item);
    }
}
