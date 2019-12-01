package com.app_republic.kora.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class AppSingleton {
    private static AppSingleton mAppSingletonInstance;
    private static Context mContext;
    private Gson gson;
    private Picasso picasso;
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

    public Gson getGson() {
        return gson;
    }




}