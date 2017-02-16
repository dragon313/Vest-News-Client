package ru.vest_news.vest_news_app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.vest_news.vest_news_app.R;
import ru.vest_news.vest_news_app.utils.CircularTransformation;

public class ContactActivity extends AppCompatActivity {

    private CardView mRastorguevCardView;
    private ImageView mRastorguevImageView;

    private CardView mAnriCardView;
    private ImageView mAnriImageView;

    private CardView mVdovenkovCardView;
    private ImageView mVdovenkovImageView;

    private CardView mStatsenkoCardView;
    private ImageView mStatsenkoImageView;

    private CardView mMosolovaCardView;
    private ImageView mMosolovaImageView;

    private CardView mKulakovaCardView;
    private ImageView mKulakovaImageView;

    private CardView mKorobovaCardView;
    private ImageView mKorobovaImageView;

    private CardView mHoteevCardView;
    private ImageView mHoteevImageView;

    private CardView mFadeevCardView;
    private ImageView mFadeevImageView;

    private CardView mPetrovaCardView;
    private ImageView mPetrovaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUI();
    }

    private void initUI() {

        mRastorguevCardView = (CardView) findViewById(R.id.contacts_rastorguev_card_view);
        mRastorguevCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mRastorguevImageView = (ImageView) findViewById(R.id.contacts_rastorguev_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/30744.jpg")
                .transform(new CircularTransformation(73))
                .into(mRastorguevImageView);

        mAnriCardView = (CardView) findViewById(R.id.contacts_ambartsumyan_card_view);
        mAnriCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mAnriImageView = (ImageView) findViewById(R.id.contacts_ambartsumyan_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/6431.jpg")
                .transform(new CircularTransformation(73))
                .into(mAnriImageView);


        mVdovenkovCardView = (CardView) findViewById(R.id.contacts_vdovenkov_card_view);
        mVdovenkovCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mVdovenkovImageView = (ImageView) findViewById(R.id.contacts_vdovenkov_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/12767.jpg")
                .transform(new CircularTransformation(73))
                .into(mVdovenkovImageView);

        mStatsenkoCardView = (CardView) findViewById(R.id.contacts_statsenko_card_view);
        mStatsenkoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mStatsenkoImageView = (ImageView) findViewById(R.id.contacts_statsenko_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/6432.jpg")
                .transform(new CircularTransformation(73))
                .into(mStatsenkoImageView);

        mMosolovaCardView = (CardView) findViewById(R.id.contacts_mosolova_card_view);
        mMosolovaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mMosolovaImageView = (ImageView) findViewById(R.id.contacts_mosolova_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/30767.jpg")
                .transform(new CircularTransformation(73))
                .into(mMosolovaImageView);

        mKulakovaCardView = (CardView) findViewById(R.id.contacts_kulakova_card_view);
        mKulakovaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mKulakovaImageView = (ImageView) findViewById(R.id.contacts_kulakova_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/6437.jpg")
                .transform(new CircularTransformation(73))
                .into(mKulakovaImageView);

        mKorobovaCardView = (CardView) findViewById(R.id.contacts_korobova_card_view);
        mKorobovaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mKorobovaImageView = (ImageView) findViewById(R.id.contacts_korobova_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/6433.jpg")
                .transform(new CircularTransformation(73))
                .into(mKorobovaImageView);

        mHoteevCardView = (CardView) findViewById(R.id.contacts_hoteev_card_view);
        mHoteevCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mHoteevImageView = (ImageView) findViewById(R.id.contacts_hoteev_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/6434.jpg")
                .transform(new CircularTransformation(73))
                .into(mHoteevImageView);

        mFadeevCardView = (CardView) findViewById(R.id.contacts_fadeev_card_view);
        mFadeevCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mFadeevImageView = (ImageView) findViewById(R.id.contacts_fadeev_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/6435.jpg")
                .transform(new CircularTransformation(73))
                .into(mFadeevImageView);

        mPetrovaCardView = (CardView) findViewById(R.id.contacts_petrova_card_view);
        mPetrovaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16.02.2017 Добавить вызов диалогового окна с выбором действий - позвонить, написать, отменить.
            }
        });
        mPetrovaImageView = (ImageView) findViewById(R.id.contacts_petrova_image_view);
        Picasso.with(this)
                .load("http://www.vest-news.ru/files/thumbs/150x150/files/contact_images/6436.jpg")
                .transform(new CircularTransformation(73))
                .into(mPetrovaImageView);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, ContactActivity.class);
    }

}
