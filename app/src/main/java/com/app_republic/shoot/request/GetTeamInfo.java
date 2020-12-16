package com.app_republic.shoot.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.app_republic.shoot.utils.StaticConfig.API_BASE;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_TEAM_INFO;

public class GetTeamInfo extends StringRequest {

    Map<String, String> params;

    public GetTeamInfo(String team_id,
                       Response.Listener<String> listener,
                       @Nullable Response.ErrorListener errorListener) {
        super(POST, API_BASE + API_GET_TEAM_INFO, listener, errorListener);
        params = new HashMap<>();

        params.put("accessToken", "");
        params.put("is_android", "1");
        params.put("team_id", team_id);

    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

}
