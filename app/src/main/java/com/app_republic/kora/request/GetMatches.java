package com.app_republic.kora.request;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.app_republic.kora.utils.StaticConfig.API_BASE;
import static com.app_republic.kora.utils.StaticConfig.API_GET_MATCHES;

public class GetMatches extends StringRequest {

    Map<String, String> params;

    public GetMatches(String date,
                      Response.Listener<String> listener,
                      @Nullable Response.ErrorListener errorListener) {
        super(POST, API_BASE + API_GET_MATCHES, listener, errorListener);
        params = new HashMap<>();

        params.put("accessToken", "");
        params.put("is_android", "1");
        params.put("date", date);

    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

}