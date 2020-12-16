package com.app_republic.shoot.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.app_republic.shoot.R;
import com.app_republic.shoot.model.LiveVideo;
import com.app_republic.shoot.model.Match;
import com.app_republic.shoot.model.Video;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.InterstitialAdListener;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.TouchyWebView;
import com.app_republic.shoot.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VideoActivity extends AppCompatActivity {

    Match match;
    AppSingleton appSingleton;
    WebView webView;
    Handler handler;
    Button BT_hide_ad;
    Runnable runnable;
    boolean isToday;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        appSingleton = AppSingleton.getInstance(this);

        match = getIntent().getParcelableExtra(StaticConfig.MATCH);


        String VIDEO_URL;

        isToday = DateUtils.isToday(Utils.getMillisFromMatchDate(match.getFullDatetimeSpaces()));

        VIDEO_URL = appSingleton.VIDEO_BASE_URL + match.getLiveId();



        String MyUA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

        webView = findViewById(R.id.webView);
        BT_hide_ad = findViewById(R.id.hide_ad);

        //webView.clearCache(true);
        //webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.getSettings().setUserAgentString(MyUA);

        webView.setWebViewClient(new Browser_home());
        webView.setWebChromeClient(new MyChrome());



       /* webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setSupportMultipleWindows(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });*/

        if (match.getState().equals(getString(R.string.match_state_finished))) {
            webView.loadUrl(VIDEO_URL);
        } else {
            AsyncTask.execute(() -> loadLiveVideos());
        }


       /* FirebaseDatabase.getInstance().getReference()
                .child("lives")
                .orderByChild("date")
                .equalTo(getDate())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot live : snapshot.getChildren()) {
                            String team1 = live.child("team1").getValue().toString();
                            String team2 = live.child("team2").getValue().toString();

                            if (match.getLiveTeam1().equals(team1) || match.getLiveTeam1().equals(team2)) {
                                String link = live.child("href").getValue().toString();
                                if (link.contains("link=")) {
                                    link = link.split("link=")[1];
                                }
                               webView.loadUrl(link);
                                Intent intent = new Intent(VideoActivity.this, GoToVideoActivity.class);
                                intent.putExtra(StaticConfig.VIDEO_URI, live.child("href").getValue().toString());
                                startActivity(intent);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }); */


        //AsyncTask.execute(() -> loadLiveVideos());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.loadBannerAd(this, "video");


        handler = new Handler();

        FrameLayout frameLayout = findViewById(R.id.adView_native);
        runnable = () -> {
            appSingleton.loadNativeAdSmall(frameLayout, () -> {
                BT_hide_ad.setVisibility(View.VISIBLE);
            });
            handler.postDelayed(runnable, 60 * 1 * 1000);
        };

        BT_hide_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = view.findViewById(R.id.ad_call_to_action);
                frameLayout.setVisibility(View.GONE);
                BT_hide_ad.setVisibility(View.GONE);
            }
        });

    }

    private String getDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
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

            //view.loadUrl("javascript:void(0)");

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

        String url = "https://www.as-goal.com/2019/09/live-match-today.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select("a.ElGadwl");
            for (Element element : elements) {
                LiveVideo liveVideo = new LiveVideo();

                liveVideo.setHref(element.attr("href"));
                liveVideo.setTeam1(element.select("div.Fareeq-r > span").first().html());
                liveVideo.setTeam2(element.select("div.Fareeq-l > span").first().html());

                if (match.getLiveTeam1().equals(liveVideo.getTeam1()) ||
                        match.getLiveTeam1().equals(liveVideo.getTeam2())
                || match.getLiveTeam2().equals(liveVideo.getTeam1())
                        || match.getLiveTeam2().equals(liveVideo.getTeam2())
                ) {

                    handler.post(() -> webView.loadUrl(liveVideo.getHref()));



                    break;

                }

            }
           // handler.post(() -> webView.loadData(element.html(), "text/html", "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
