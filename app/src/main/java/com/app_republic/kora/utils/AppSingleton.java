package com.app_republic.kora.utils;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.security.PrivilegedAction;
import java.util.List;

import static com.app_republic.kora.utils.StaticConfig.NUMBER_OF_NATIVE_ADS;

public class AppSingleton {
    private static AppSingleton mAppSingletonInstance;
    private static Context mContext;
    private Gson gson;
    private Picasso picasso;
    private AdLoader adLoader;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    private AppSingleton(Context context) {
        mContext = context;
        gson = new Gson();
    }


    public static synchronized AppSingleton getInstance(Context context) {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new AppSingleton(context);
        }
        return mAppSingletonInstance;
    }


    public Picasso getPicasso() {
        if (picasso == null)
            picasso = Picasso.get();
        return picasso;

    }

    public FirebaseFirestore getDb() {
        if (db == null)
            db = FirebaseFirestore.getInstance();
        return db;
    }

    public FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }

    public Gson getGson() {
        return gson;
    }


    private void insertAdsInMenuItems(RecyclerView.Adapter adapter,
                                      List<UnifiedNativeAd> mNativeAds,
                                      List contentList,
                                      List completeList) {
        if (mNativeAds.size() <= 0) {
            return;
        }

        int offset = (contentList.size() / mNativeAds.size()) + 1;
        int index = 0;
        for (UnifiedNativeAd ad : mNativeAds) {
            completeList.add(index, ad);
            index = index + offset;
        }


        adapter.notifyDataSetChanged();
    }

    public void loadNativeAds(List<UnifiedNativeAd> mNativeAds,
                              RecyclerView.Adapter adapter,
                              List contentList,
                              List completeList,
                              int number_of_ads
    ) {

        if (mNativeAds.isEmpty()) {
            AdLoader.Builder builder = new AdLoader.Builder(mContext, StaticConfig.ADMOB_NATIVE_UNIT_ID);

            adLoader = builder.forUnifiedNativeAd(unifiedNativeAd -> {
                mNativeAds.add(unifiedNativeAd);
                if (!adLoader.isLoading()) {
                    insertAdsInMenuItems(adapter, mNativeAds, contentList, completeList);
                }
            }).withAdListener(
                    new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            if (!adLoader.isLoading()) {
                                insertAdsInMenuItems(adapter, mNativeAds, contentList, completeList);
                            }
                        }
                    }).build();

            // Load the Native Express ad.
            adLoader.loadAds(new AdRequest.Builder().build(), number_of_ads);
        } else
            insertAdsInMenuItems(adapter, mNativeAds, contentList, completeList);

    }

}