package com.app_republic.shoot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_republic.shoot.R;
import com.app_republic.shoot.model.general.Advert;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    AppSingleton appSingleton;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String SETTINGS_TITLE = "settings";
    public static final String SETTING_ALREADY_SUBSCRIBED = "already_subscribed";
    public static final String SETTING_ALREADY_SUBSCRIBED_new_topic = "already_subscribed2";
    long current_time, last_read, last_read_leagues;
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

        last_read_leagues = prefs.getLong("last_read_leagues", current_time);
        if ((last_read_leagues + 1000 * 60 * 60 * 12) < current_time || last_read_leagues == current_time) {
            source = Source.SERVER;
        } else {
            source = Source.CACHE;
        }
        getLeagues(source);

    }

    private void getLeagues(Source source) {
        db.collection("leagues")
                .document("leagues")
                .get(source).addOnSuccessListener(document -> {
                    appSingleton.leagues = new ArrayList<>();
                    appSingleton.leagueNames = new ArrayList<>();

                    for (Long longValue : (ArrayList<Long>) document.get("leagues")) {
                        appSingleton.leagues.add(longValue.intValue());
                    }

                    appSingleton.leagueNames.addAll((ArrayList<String>) document.get("names"));


                    String orderString = (String) document.get("order");

                    String[] stringArray = orderString.split(",");

                    List<Integer> order = new ArrayList<>();

                    for (String str : stringArray) {
                        try {
                            int num = Integer.parseInt(str);
                            order.add(num);
                        } catch (NumberFormatException e) {
                            System.err.println("Unable to parse: " + str);
                        }
                    }

                    Comparator<Integer> customComparator = (id1, id2) -> {
                        int index1 = order.indexOf(appSingleton.leagues.indexOf(id1));
                        int index2 = order.indexOf(appSingleton.leagues.indexOf(id2));
                        if (index1 != -1 && index2 != -1) {
                            return Integer.compare(index1, index2);
                        } else if (index1 != -1) {
                            return -1;
                        } else if (index2 != -1) {
                            return 1;
                        } else {
                            return Integer.compare(appSingleton.leagues.indexOf(id1), appSingleton.leagues.indexOf(id2));
                        }
                    };

                    Collections.sort(appSingleton.leagues, customComparator);

                    Comparator<String> customComparator2 = (id1, id2) -> {
                        int index1 = order.indexOf(appSingleton.leagueNames.indexOf(id1));
                        int index2 = order.indexOf(appSingleton.leagueNames.indexOf(id2));
                        if (index1 != -1 && index2 != -1) {
                            return Integer.compare(index1, index2);
                        } else if (index1 != -1) {
                            return -1;
                        } else if (index2 != -1) {
                            return 1;
                        } else {
                            return Integer.compare(appSingleton.leagueNames.indexOf(id1), appSingleton.leagueNames.indexOf(id2));
                        }
                    };

                    Collections.sort(appSingleton.leagueNames, customComparator2);


                    editor.putLong("last_read_leagues", current_time);
                    editor.apply();
                }).addOnFailureListener(e -> {
                    getLeagues(Source.CACHE);
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void getSettings(Source source) {

        db.collection("settings")
                .get(source).addOnSuccessListener(queryDocumentSnapshots -> {

            db.collection("ads_center")

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
                                    appSingleton.MOPUB_BANNER_UNIT_ID = documentSnapshot1.get("center_banner_unit_id").toString();
                                    appSingleton.MOPUB_NATIVE_UNIT_ID = documentSnapshot1.get("center_native_unit_id").toString();
                                    appSingleton.MOPUB_INTER_UNIT_ID = documentSnapshot1.get("center_inter_unit_id").toString();
                                } else if (documentSnapshot1.getId().equals("admob")) {
                                    appSingleton.ADMOB_APP_ID = documentSnapshot1.get("center_app_id").toString();
                                    appSingleton.ADMOB_BANNER_UNIT_ID = documentSnapshot1.get("center_banner_unit_id").toString();
                                    appSingleton.ADMOB_NATIVE_UNIT_ID = documentSnapshot1.get("center_native_unit_id").toString();
                                    appSingleton.ADMOB_INTER_UNIT_ID = documentSnapshot1.get("center_inter_unit_id").toString();

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
                                FirebaseMessaging.getInstance().subscribeToTopic("center.5.3.1")
                                        .addOnSuccessListener(aVoid1 -> {
                                            editor.putBoolean(SETTING_ALREADY_SUBSCRIBED_new_topic, true);

                                            editor.apply();
                                        }));


            }
        } catch (Exception e) {

        }

    }

}
