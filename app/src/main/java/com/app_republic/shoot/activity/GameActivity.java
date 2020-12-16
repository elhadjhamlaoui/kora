package com.app_republic.shoot.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.app_republic.shoot.R;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.Utils;

public class GameActivity extends AppCompatActivity {
    private String GAME_BASE_URL = "https://html5games.com";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        WebView wv = findViewById(R.id.webView);

        String gameUrl = GAME_BASE_URL + getIntent().getStringExtra(StaticConfig.GAME);
        wv.loadUrl(gameUrl);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.loadBannerAd(this, "game");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
