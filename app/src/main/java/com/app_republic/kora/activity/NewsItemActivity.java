package com.app_republic.kora.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app_republic.kora.R;
import com.app_republic.kora.model.News;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;
import com.squareup.picasso.Picasso;

public class NewsItemActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView IV_thumb, IV_back, IV_share;
    TextView TV_title, TV_body, TV_time;
    News news;
    Picasso picasso;
    AppSingleton appSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);


        appSingleton = AppSingleton.getInstance(this);

        picasso = appSingleton.getPicasso();

        news = getIntent().getParcelableExtra(StaticConfig.NEWS);
        initialiseViews();

        Utils.loadBannerAd(this, "news");
        AppSingleton.getInstance(this).loadNativeAd(findViewById(R.id.adViewNative));
        AppSingleton.getInstance(this).loadNativeAd(findViewById(R.id.adViewNative2));


    }

    private void initialiseViews() {
        IV_thumb = findViewById(R.id.thumb);
        IV_back = findViewById(R.id.back);
        IV_share = findViewById(R.id.share);

        TV_title = findViewById(R.id.title);
        TV_body = findViewById(R.id.body);
        TV_time = findViewById(R.id.time);

        IV_back.setOnClickListener(this);
        IV_share.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            IV_thumb.setTransitionName(StaticConfig.THUMB);
        }

        if (!news.getPostImage().isEmpty())
            try {
                picasso.load(news.getPostImage())
                        .fit()
                        .placeholder(R.drawable.ic_news_large)
                        .into(IV_thumb);
            } catch (Exception e) {
                picasso.load(news.getPostImage())
                        .fit()
                        .into(IV_thumb);
            }

        TV_title.setText(news.getPostTitle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TV_body.setText(Html.fromHtml(news.getPostContent(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            TV_body.setText(Html.fromHtml(news.getPostContent()));
        }
        TV_time.setText(news.getPostDate());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        news.getPostTitle() + "\n\n" + Html.fromHtml(news.getPostContent())
                                + "\n\n" + getString(R.string.app_name) + "\n\n" +
                                getString(R.string.play_store_link)
                );

                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
