package com.app_republic.shoot.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.app_republic.shoot.utils.StaticConfig.API_BASE;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_LATEST_NEWS;

public class GetLatestNews extends StringRequest {

    Map<String, String> params;

    public GetLatestNews(Response.Listener<String> listener,
                         @Nullable Response.ErrorListener errorListener) {
        super(POST, API_BASE + API_GET_LATEST_NEWS, listener, errorListener);
        params = new HashMap<>();

        params.put("accessToken", "");
        params.put("is_android", "1");

    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

}
