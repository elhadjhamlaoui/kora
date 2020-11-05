package com.app_republic.kora.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

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
    public byte[] getBody() {
        return new JSONObject(params).toString().getBytes();
    }

    @Override
    public Map<String, String> getHeaders(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0");
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

}
