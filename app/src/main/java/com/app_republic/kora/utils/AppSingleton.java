package com.app_republic.kora.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class AppSingleton {
    private static AppSingleton mAppSingletonInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mContext;
    private Gson gson;
    private Picasso picasso;
    private AppSingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        gson = new Gson();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);
 
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
 
                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }



    public static synchronized AppSingleton getInstance(Context context) {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new AppSingleton(context);
        }
        return mAppSingletonInstance;
    }
 
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public Picasso getPicasso() {
        if (picasso == null)
            picasso = Picasso.get();
        return picasso;

    }

    public Gson getGson() {
        return gson;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
 
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}