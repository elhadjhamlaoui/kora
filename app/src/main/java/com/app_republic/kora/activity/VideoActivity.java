package com.app_republic.kora.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.app_republic.kora.R;
import com.app_republic.kora.model.Match;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity {

    Match match;
    String match_url;
    AppSingleton appSingleton;
    WebView webView;
    Handler handler;
    Runnable runnable;
    boolean isToday, isLive;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        appSingleton = AppSingleton.getInstance(this);

        match = getIntent().getParcelableExtra(StaticConfig.MATCH);
        match_url = getIntent().getStringExtra(StaticConfig.MATCH_LIVE_URL);

        isLive = getIntent().getBooleanExtra(StaticConfig.IS_LIVE, false);

        String VIDEO_URL;

        isToday = DateUtils.isToday(Utils.getMillisFromMatchDate(match.getFullDatetimeSpaces()));

        if (!match.getState().equals(getString(R.string.match_state_finished)) && isLive) {
            VIDEO_URL = "http://www.yalla-shoot.com/" + match_url;
        } else {
            VIDEO_URL = appSingleton.VIDEO_BASE_URL + match.getLiveId();
        }

        String MyUA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

        webView = findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);

        webView.getSettings().setUserAgentString(MyUA);

        webView.setWebViewClient(new Browser_home());
        webView.setWebChromeClient(new MyChrome());


        webView.loadUrl(VIDEO_URL);


        //AsyncTask.execute(() -> loadLiveVideos());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.loadBannerAd(this, "video");


        handler = new Handler();
        runnable = () -> {
            appSingleton.loadNativeAdBig(findViewById(R.id.adView_native));
            handler.postDelayed(runnable, 40 * 1000);
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);


        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            view.loadUrl("javascript:(function() { " +
                    "document.querySelector('.ui-content>center>div>center').innerHTML = \"\";" +
                    "document.querySelector('.ui-header').style.display = \"none\"" +
                    " })()");

        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return false;
        }
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }



    private void loadLiveVideos() {

        String url = "http://www.yalla-shoot.com/live/" + match_url;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Element element = doc.select(".live_box_pop").first();
            handler.post(() -> webView.loadData(element.html(), "text/html", "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
