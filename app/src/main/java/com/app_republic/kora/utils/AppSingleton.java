package com.app_republic.kora.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.app_republic.kora.model.Advert;
import com.app_republic.kora.model.User;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AppSingleton {
    private static AppSingleton mAppSingletonInstance;
    private AppCompatActivity mContext;
    public String ADMOB_APP_ID = "";

    public String ADMOB_NATIVE_UNIT_ID = "";
    public String ADMOB_BANNER_UNIT_ID = "";
    public String ADMOB_INTER_UNIT_ID = "";
    public String VIDEO_BASE_URL = "";
    public String MAIN_SCREEN = "";

    public String MOPUB_NATIVE_UNIT_ID = "";
    public String MOPUB_BANNER_UNIT_ID = "";
    public String MOPUB_INTER_UNIT_ID = "";


    public String WEBSITE_URL = "";
    public String FACEBOOK_PAGE = "";


    public ArrayList<Advert> banner_adverts = new ArrayList<>();
    public ArrayList<Advert> inter_adverts = new ArrayList<>();

    private FirebaseStorage storage;

    private Gson gson;
    private Picasso picasso;
    private AdLoader adLoader;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private UserLocalStore userLocalStore;
    private InterstitialAd mInterstitialAd;


    private AppSingleton(Context context) {
        mContext = (AppCompatActivity) context;
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

    public User getUser() {
        return getUserLocalStore().getLoggedInUser();
    }

    public UserLocalStore getUserLocalStore() {
        if (userLocalStore == null)
            userLocalStore = new UserLocalStore(mContext);
        return userLocalStore;

    }

    public FirebaseFirestore getDb() {
        if (db == null)
            db = FirebaseFirestore.getInstance();
        return db;
    }

    public InterstitialAd getInterstitialAd() {

        if (mInterstitialAd == null) {
            mInterstitialAd = new InterstitialAd(mContext);
            mInterstitialAd.setAdUnitId(mAppSingletonInstance.ADMOB_INTER_UNIT_ID);
        }
        return mInterstitialAd;
    }

    public FirebaseStorage getFirebaseStorage() {
        if (storage == null)
            storage = FirebaseStorage.getInstance();
        return storage;
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
                              RecyclerView recyclerView,
                              RecyclerView.Adapter adapter,
                              List contentList,
                              List completeList,
                              int number_of_ads

    ) {






        /*

        MoPubStaticNativeAdRenderer adRenderer = new MoPubStaticNativeAdRenderer(
                new ViewBinder.Builder(R.layout.native_ad_list_item)
                        .titleId(R.id.native_title)
                        .textId(R.id.native_text)
                        .mainImageId(R.id.native_main_image)
                        .iconImageId(R.id.native_icon_image)
                        .callToActionId(R.id.native_cta)
                        .privacyInformationIconImageId(R.id.native_privacy_information_icon_image)
                        .build()
        );

        GooglePlayServicesAdRenderer googlePlayServicesAdRenderer = new GooglePlayServicesAdRenderer(
                new MediaViewBinder.Builder(R.layout.native_video_layout)
                        .mediaLayoutId(R.id.native_ad_media_layout)
                        .iconImageId(R.id.native_icon_image)
                        .titleId(R.id.native_title)
                        .textId(R.id.native_text)
                        .callToActionId(R.id.native_cta)
                        .privacyInformationIconImageId(R.id.native_privacy_information_icon_image)
                        .build());

        FacebookAdRenderer facebookAdRenderer = new FacebookAdRenderer(
                new FacebookAdRenderer.FacebookViewBinder.Builder(R.layout.native_ad_list_item_facebook)
                        .titleId(R.id.native_title)
                        .textId(R.id.native_text)
                        // Binding to new layouts from Facebook 4.99.0+
                        .mediaViewId(R.id.native_main_image)
                        .adIconViewId(R.id.native_icon_image)
                        .adChoicesRelativeLayoutId(R.id.native_ad_choices_relative_layout)
                        .advertiserNameId(R.id.native_title) // Bind either the titleId or advertiserNameId depending on the FB SDK version
                        // End of binding to new layouts
                        .callToActionId(R.id.native_cta)
                        .build());

        MoPubNativeAdPositioning.MoPubServerPositioning adPositioning =
                MoPubNativeAdPositioning.serverPositioning();

        MoPubRecyclerAdapter mAdAdapter = new MoPubRecyclerAdapter(mContext, adapter, adPositioning);

        mAdAdapter.registerAdRenderer(adRenderer);
        mAdAdapter.registerAdRenderer(facebookAdRenderer);
        mAdAdapter.registerAdRenderer(googlePlayServicesAdRenderer);

        recyclerView.setAdapter(mAdAdapter);


        RequestParameters mRequestParameters = new RequestParameters.Builder()
                .build();
        mAdAdapter.loadAds(mAppSingletonInstance.MOPUB_NATIVE_UNIT_ID, mRequestParameters);

        */

        if (mNativeAds.isEmpty()) {
            AdLoader.Builder builder = new AdLoader.Builder(mContext, mAppSingletonInstance.ADMOB_NATIVE_UNIT_ID);

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


    public void loadNativeAd(FrameLayout frameLayout) {
        /*MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
            @Override
            public void onNativeLoad(final NativeAd nativeAd) {
                AdapterHelper adapterHelper = new AdapterHelper(mContext, 0, 3);

                View v = adapterHelper.getAdView(null, frameLayout, nativeAd,
                        new ViewBinder.Builder(0).build());

                frameLayout.removeAllViews();
                frameLayout.addView(v);

            }

            @Override
            public void onNativeFail(NativeErrorCode errorCode) {
            }
        };

        MoPubNative moPubNative = new MoPubNative(mContext, mAppSingletonInstance.MOPUB_NATIVE_UNIT_ID , moPubNativeNetworkListener);

        MoPubStaticNativeAdRenderer adRenderer = new MoPubStaticNativeAdRenderer(
                new ViewBinder.Builder(R.layout.native_ad_list_item)
                        .titleId(R.id.native_title)
                        .textId(R.id.native_text)
                        .mainImageId(R.id.native_main_image)
                        .iconImageId(R.id.native_icon_image)
                        .callToActionId(R.id.native_cta)
                        .privacyInformationIconImageId(R.id.native_privacy_information_icon_image)
                        .build()
        );

        GooglePlayServicesAdRenderer googlePlayServicesAdRenderer = new GooglePlayServicesAdRenderer(
                new MediaViewBinder.Builder(R.layout.native_video_layout)
                        .mediaLayoutId(R.id.native_ad_media_layout)
                        .iconImageId(R.id.native_icon_image)
                        .titleId(R.id.native_title)
                        .textId(R.id.native_text)
                        .callToActionId(R.id.native_cta)
                        .privacyInformationIconImageId(R.id.native_privacy_information_icon_image)
                        .build());

        FacebookAdRenderer facebookAdRenderer = new FacebookAdRenderer(
                new FacebookAdRenderer.FacebookViewBinder.Builder(R.layout.native_ad_list_item_facebook)
                        .titleId(R.id.native_title)
                        .textId(R.id.native_text)
                        // Binding to new layouts from Facebook 4.99.0+
                        .mediaViewId(R.id.native_main_image)
                        .adIconViewId(R.id.native_icon_image)
                        .adChoicesRelativeLayoutId(R.id.native_ad_choices_relative_layout)
                        .advertiserNameId(R.id.native_title) // Bind either the titleId or advertiserNameId depending on the FB SDK version
                        // End of binding to new layouts
                        .callToActionId(R.id.native_cta)
                        .build());


        moPubNative.registerAdRenderer(adRenderer);
        moPubNative.registerAdRenderer(facebookAdRenderer);
        moPubNative.registerAdRenderer(googlePlayServicesAdRenderer);


        RequestParameters mRequestParameters = new RequestParameters.Builder()
                .build();

        moPubNative.makeRequest(mRequestParameters);

        */


        AdLoader loader = new AdLoader.Builder(mContext, ADMOB_NATIVE_UNIT_ID)
                .forUnifiedNativeAd(unifiedNativeAd -> {

                    View unifiedNativeLayoutView = LayoutInflater.from(
                            mContext).inflate(R.layout.ad_unified, frameLayout, false);
                    UnifiedNativeAdViewHolder holder = new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);

                    UnifiedNativeAdViewHolder.populateNativeAdView(unifiedNativeAd,
                            holder.getAdView());

                    frameLayout.removeAllViews();
                    frameLayout.addView(unifiedNativeLayoutView);

                })

                .build();

        loader.loadAd(new AdRequest.Builder().build());
    }

    public void loadNativeAdBig(FrameLayout frameLayout) {

        AdLoader loader = new AdLoader.Builder(mContext, ADMOB_NATIVE_UNIT_ID)
                .forUnifiedNativeAd(unifiedNativeAd -> {

                    View unifiedNativeLayoutView = LayoutInflater.from(
                            mContext).inflate(R.layout.ad_unified_news, frameLayout, false);
                    UnifiedNativeAdViewHolder holder = new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);

                    UnifiedNativeAdViewHolder.populateNativeAdView(unifiedNativeAd,
                            holder.getAdView());

                    frameLayout.removeAllViews();
                    frameLayout.addView(unifiedNativeLayoutView);

                })

                .build();

        loader.loadAd(new AdRequest.Builder().build());


    }


}
