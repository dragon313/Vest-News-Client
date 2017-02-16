package ru.vest_news.vest_news_app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import ru.vest_news.vest_news_app.R;

public class AboutActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWebView();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.about_web_view);
        String aboutText = "<div class=\"text\">\n" +
                "        <p style=\"font-size:17px\"><strong>Государственное бюджетное учреждение Калужской области \"Редакция газеты \"Весть\"</strong></p>\n" +
                "\n" +
                "<p><strong>Адрес редакции:</strong> 248600, г. Калуга, ул. Марата, 10.</p>\n" +
                "\n" +
                "<p><strong>Телефон:</strong> <a href=\"tel:+74842591120\">59-11-20.</a></p>\n" +
                "\n" +
                "<p><strong>E-mail:</strong> <a class=\"email\" href=\"mailto:contact@vest-news.ru\">contact@vest-news.ru</a><br>\n" +
                "<p><strong>Главный редактор сайта:</strong> Калакин Алексей Викторович<p>"+
                "<br>\n" +
                "<strong>Телефоны отделов:</strong></p>\n" +
                "\n" +
                "<ul>\n" +
                "\t<li>рекламы - <a href=\"tel:+74842576451\">57-64-51</a></li>\n" +
                "\t<li>культуры - <a href=\"tel:+74842577281\">57-72-81</a></li>\n" +
                "\t<li>писем и социальных проблем - <a href=\"tel:+74842795051\">79-50-51</a>, <a href=\"tel:+74842579347\">57-93-47</a></li>\n" +
                "\t<li>политики - <a href=\"tel:+74842591125\">59-11-25</a>, <a href=\"tel:+74842562251\">56-22-51</a></li>\n" +
                "\t<li>экономики - <a href=\"tel:+74842562881\">56-28-81</a></li>\n" +
                "\t<li>новостей - <a href=\"tel:+74842591132\">59-11-32</a></li>\n" +
                "\t<li>рынка товаров и услуг - <a href=\"tel:+74842562518\">56-25-18</a></li>\n" +
                "\t<li>информационных технологий - <a href=\"tel:+74842576482\">57-64-82</a></li>\n" +
                "</ul>\n" +
                "\n" +
                "<p><strong>Учредители:</strong><br>\n" +
                "Законодательное Собрание Калужской области;<br>\n" +
                "Правительство Калужской области.&nbsp;</p>\n" +
                "\n" +
                "<p><strong>Издатель:</strong> государственное бюджетное учреждение Калужской области «Редакция газеты Калужской области «Весть».</p>\n" +
                "\n" +
                "    </div>";
        mWebView.loadDataWithBaseURL(null,aboutText,"text/html","UTF-8", null);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

}
