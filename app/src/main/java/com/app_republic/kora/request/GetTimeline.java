package com.app_republic.kora.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.app_republic.kora.utils.StaticConfig.API_BASE;
import static com.app_republic.kora.utils.StaticConfig.API_GET_MATCH_INFO;
import static com.app_republic.kora.utils.StaticConfig.API_GET_TIME_LINE;

public class GetTimeline extends StringRequest {

    Map<String, String> params;

    public GetTimeline(String live_id,
                       Response.Listener<String> listener,
                       @Nullable Response.ErrorListener errorListener) {
        super(POST, API_BASE + API_GET_TIME_LINE, listener, errorListener);
        params = new HashMap<>();

        params.put("accessToken", "");
        params.put("is_android", "1");
        params.put("live_id", live_id);

    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

}
