package com.app_republic.kora.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_republic.kora.BuildConfig;
import com.app_republic.kora.R;
import com.app_republic.kora.model.Advert;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    AppSingleton appSingleton;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String SETTINGS_TITLE = "settings";
    public static final String SETTING_ALREADY_SUBSCRIBED = "already_subscribed";
    public static final String SETTING_ALREADY_SUBSCRIBED_new_topic = "already_subscribed2";
    long current_time, last_read;
    Source source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        appSingleton = AppSingleton.getInstance(this);
        firebaseAuth = appSingleton.getFirebaseAuth();
        db = appSingleton.getDb();

        /*SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(appSingleton.MOPUB_BANNER_UNIT_ID)
                .withLegitimateInterestAllowed(false)
                .build();

        MoPub.initializeSdk(this, sdkConfiguration, () -> {

        });*/

        prefs = getSharedPreferences(SETTINGS_TITLE, MODE_PRIVATE);
        editor = prefs.edit();

        subscribeToMessaging();
        current_time = System.currentTimeMillis();

        last_read = prefs.getLong("last_read", current_time);
        if ((last_read + 1000 * 60 * 60 * 12) < current_time || last_read == current_time) {
            source = Source.SERVER;
        } else {
            source = Source.CACHE;
        }
        getSettings(source);

    }

    private void getSettings(Source source) {
        db.collection("settings")
                .get(source).addOnSuccessListener(queryDocumentSnapshots -> {

            db.collection("ads")

                    .get(source)
                    .addOnSuccessListener(queryDocumentSnapshots1 -> {
                        appSingleton.banner_adverts = new ArrayList<>();
                        appSingleton.inter_adverts = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot1 : queryDocumentSnapshots1) {
                            try {
                                Advert advert = documentSnapshot1.toObject(Advert.class);

                                if (advert.getType().equals("inter"))
                                    appSingleton.inter_adverts.add(advert);
                                else
                                    appSingleton.banner_adverts.add(advert);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }

                        for (DocumentSnapshot documentSnapshot1 : queryDocumentSnapshots) {

                            try {
                                if (documentSnapshot1.getId().equals("mopub")) {
                                    appSingleton.MOPUB_BANNER_UNIT_ID = documentSnapshot1.get("banner_unit_id").toString();
                                    appSingleton.MOPUB_NATIVE_UNIT_ID = documentSnapshot1.get("native_unit_id").toString();
                                    appSingleton.MOPUB_INTER_UNIT_ID = documentSnapshot1.get("inter_unit_id").toString();
                                } else if (documentSnapshot1.getId().equals("admob")) {
                                    appSingleton.ADMOB_APP_ID = documentSnapshot1.get("app_id").toString();
                                    /*appSingleton.ADMOB_BANNER_UNIT_ID = documentSnapshot1.get("banner_unit_id").toString();
                                    appSingleton.ADMOB_NATIVE_UNIT_ID = documentSnapshot1.get("native_unit_id").toString();
                                    appSingleton.ADMOB_INTER_UNIT_ID = documentSnapshot1.get("inter_unit_id").toString();*/

                                    appSingleton.ADMOB_BANNER_UNIT_ID = "ca-app-pub-5411690074381837/6929156030";
                                    appSingleton.ADMOB_NATIVE_UNIT_ID = "ca-app-pub-5411690074381837/3682037983";
                                    appSingleton.ADMOB_INTER_UNIT_ID = "ca-app-pub-5411690074381837/7621282997";

                                } else if (documentSnapshot1.getId().equals("api")) {
                                    StaticConfig.API_BASE = documentSnapshot1.get("url_base").toString();
                                    appSingleton.VIDEO_BASE_URL = documentSnapshot1.get("video_base").toString();
                                    appSingleton.MAIN_SCREEN = documentSnapshot1.get("main_screen").toString();
                                    appSingleton.JWS = documentSnapshot1.get("jws").toString();

                                } else if (documentSnapshot1.getId().equals("links")) {
                                    appSingleton.FACEBOOK_PAGE = documentSnapshot1.get("facebook").toString();
                                    appSingleton.WEBSITE_URL = documentSnapshot1.get("website").toString();
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }


                        }

                        Utils.loadInterstitialAd(getSupportFragmentManager(), "custom", "main",
                                SplashActivity.this, () -> {
                                    if (source == Source.SERVER) {
                                        editor.putLong("last_read", current_time);
                                        editor.apply();
                                    }

                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    if (getIntent().getExtras() != null) {
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtras(getIntent().getExtras());

                                    }
                                    startActivity(intent);
                                    finish();
                                });

                    }).addOnFailureListener(e -> {
                getSettings(Source.CACHE);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });

        }).addOnFailureListener(e -> {
            getSettings(Source.CACHE);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void subscribeToMessaging() {

        try {
            boolean alreadySubscribed = prefs.getBoolean(SETTING_ALREADY_SUBSCRIBED_new_topic, false);
            if (!alreadySubscribed) {
                FirebaseMessaging.getInstance().subscribeToTopic("global.5.3.1")
                        .addOnSuccessListener(aVoid ->
                                FirebaseMessaging.getInstance().subscribeToTopic("lite.5.3.1")
                                        .addOnSuccessListener(aVoid1 -> {
                                            editor.putBoolean(SETTING_ALREADY_SUBSCRIBED_new_topic, true);

                                            editor.apply();
                                        }));


            }
        } catch (Exception e) {

        }

    }

}
