package com.app_republic.shoot.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app_republic.shoot.R;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.Utils;

public class GameActivity extends AppCompatActivity {
    private String GAME_BASE_URL = "https://play.famobi.com/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        WebView wv = findViewById(R.id.webView);

        String gameUrl = GAME_BASE_URL + getIntent().getStringExtra(StaticConfig.GAME);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.loadBannerAd(this, "game");

        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wv.clearCache(true);
        wv.clearHistory();
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        wv.loadUrl(gameUrl);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
