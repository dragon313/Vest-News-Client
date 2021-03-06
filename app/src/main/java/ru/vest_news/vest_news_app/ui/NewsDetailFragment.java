package ru.vest_news.vest_news_app.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import ru.vest_news.vest_news_app.R;
import ru.vest_news.vest_news_app.network.NewsFetcher;
import ru.vest_news.vest_news_app.model.RetrofitNewsItem;
import ru.vest_news.vest_news_app.model.NewsLab;

public class NewsDetailFragment extends Fragment {
    private static final String TAG = "NewsDetailFragment";
    private static final String BASE_URI = "http://www.vest-news.ru/";

    public static final String EXTRA_ID = "EXTRA_ID";

    private RetrofitNewsItem mItem;
    private Toolbar mToolbar;
    private Drawer mDrawer;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mToolbarTitle;
    private WebView mBodyTextView;
    private ImageView mPhotoImageView;
    private TextView mRubricTextView;
    private TextView mViewCounterTextView;


    public static NewsDetailFragment newInstance() {
        return new NewsDetailFragment();
    }

    public NewsDetailFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    private void loadNewsInfo() {
        Intent intent = getActivity().getIntent();
        final String id = intent.getStringExtra(EXTRA_ID);
        mItem = NewsLab.getInstance().getItem(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_detail, container, false);
        initUI(v);
        loadNewsInfo();
        return v;
    }

    private void initUI(View v) {
        mToolbar = (Toolbar) v.findViewById(R.id.fragment_news_detail_toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.fragment_news_detail_collapsing_toolbar);
        mToolbarTitle = (TextView) v.findViewById(R.id.fragment_news_detail_title_text_view);
        mPhotoImageView = (ImageView) v.findViewById(R.id.fragment_news_detail_photo_image_view);
        mRubricTextView = (TextView) v.findViewById(R.id.fragment_news_detail_rubric_text_view);
        mViewCounterTextView = (TextView) v.findViewById(R.id.fragment_news_detail_view_counter_text_view);
        mBodyTextView = (WebView) v.findViewById(R.id.fragment_news_detail_body_text_view);
    }

    private void updateUI() {

        Picasso.with(getActivity())
                .load(mItem.getFilepath())
                .error(R.drawable.logo_rectangle)
                .into(mPhotoImageView);

        mToolbarTitle.setText(mItem.getTitle());
        mRubricTextView.setText(mItem.getRubric());
        mViewCounterTextView.setText(mItem.getViews());
        mBodyTextView.loadData(mItem.getBody().trim(), "text/html; charset=utf-8", "UTF-8");
        String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"fragment_detail_body_style.css\" />" + mItem.getBody().trim();
        mBodyTextView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
        mCollapsingToolbarLayout.post(new Runnable() {
            @Override
            public void run() {
                int tvHeight = mToolbarTitle.getHeight();
                int tbHeight = mToolbar.getHeight();
                if (tvHeight > tbHeight) {
                    CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) mToolbar.getLayoutParams();
                    params.bottomMargin = tvHeight - tbHeight;
                    mToolbar.setLayoutParams(params);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolBar();
        setUpNavigationDrawer();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_news_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail_share_button:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, createShareText());
                shareIntent.putExtra(Intent.EXTRA_HTML_TEXT, mItem.getBody());
                shareIntent.setType("text/plain");
                Intent chooser = Intent.createChooser(shareIntent, getString(R.string.send_via));
                startActivity(chooser);
                return true;
            case R.id.menu_detail_show_in_browser:
                Intent showInBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_URI + "news/" + mItem.getNid()));
                startActivity(showInBrowser);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String createShareText() {
        StringBuilder newsText = new StringBuilder();
        newsText.append(mItem.getTitle()).append("\n");
        newsText.append(BASE_URI + "news/").append(mItem.getNid());
        return newsText.toString();
    }


    private void setToolBar() {
        AppCompatActivity activity = (NewsDetailActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitle("");
    }

    private void setUpNavigationDrawer() {
        PrimaryDrawerItem news = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.drawer_item_news);
        PrimaryDrawerItem weather = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.drawer_item_weather);
        SecondaryDrawerItem settings = new SecondaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.drawer_item_settings);
        PrimaryDrawerItem contacts = new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.drawer_item_contacts);
        SecondaryDrawerItem about = new SecondaryDrawerItem()
                .withIdentifier(5)
                .withName(R.string.drawer_item_about);


        mDrawer = new DrawerBuilder()
                .withActivity(getActivity())
                .withToolbar(mToolbar)
                .withSelectedItem(-1)
                .withHeader(R.layout.drawer_header)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        news,
                        weather,
                        contacts,
                        new DividerDrawerItem(),
                        settings,
                        about
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()) {
                            case 1:
                                getActivity().finish();
                                mDrawer.closeDrawer();
                                return true;
                            case 2:
                                Toast.makeText(getActivity(), getString(R.string.development_in_progress), Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                return true;
                            case 3:
                                Toast.makeText(getActivity(),  getString(R.string.development_in_progress), Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                return true;
                            case 4:
                                startActivity(ContactActivity.getIntent(getContext()));
//                                Toast.makeText(getActivity(),  getString(R.string.development_in_progress), Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                return true;
                            case 5:
                                startActivity(AboutActivity.getIntent(getContext()));
//                                Toast.makeText(getActivity(),  getString(R.string.development_in_progress), Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                return true;
                            default:
                                return true;
                        }
                    }
                })
                .build();
    }

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }
}
