package com.app_republic.kora.utils;

import com.app_republic.kora.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.app_republic.kora.utils.StaticConfig.API_GET_DEPS_PLAYERS;
import static com.app_republic.kora.utils.StaticConfig.API_GET_DEPS_STANDINGS;
import static com.app_republic.kora.utils.StaticConfig.API_GET_DEP_STANDINGS;
import static com.app_republic.kora.utils.StaticConfig.API_GET_ITEM_NEWS;
import static com.app_republic.kora.utils.StaticConfig.API_GET_LATEST_NEWS;
import static com.app_republic.kora.utils.StaticConfig.API_GET_MATCHES;
import static com.app_republic.kora.utils.StaticConfig.API_GET_MATCH_INFO;
import static com.app_republic.kora.utils.StaticConfig.API_GET_PLAYERS;
import static com.app_republic.kora.utils.StaticConfig.API_GET_PLAYER_MATCHES;
import static com.app_republic.kora.utils.StaticConfig.API_GET_TEAM_INFO;
import static com.app_republic.kora.utils.StaticConfig.API_GET_TIME_LINE;
import static com.app_republic.kora.utils.StaticConfig.API_GET_TRENDING_TEAMS;

public interface APIInterface {

    @FormUrlEncoded
    @POST(API_GET_MATCHES)
    Call<ApiResponse> getMatches(@Field("is_android") String is_android,
                                 @Field("accessToken") String accessToken,
                                 @Field("date") String date

    );

    @FormUrlEncoded
    @POST(API_GET_DEP_STANDINGS)
    Call<ApiResponse> getDepStandings(@Field("is_android") String is_android,
                                      @Field("accessToken") String accessToken,
                                      @Field("dep_id") String dep_id

    );

    @FormUrlEncoded
    @POST(API_GET_DEPS_PLAYERS)
    Call<ApiResponse> getDepsWithPlayers(@Field("is_android") String is_android,
                                         @Field("accessToken") String accessToken

    );

    @FormUrlEncoded
    @POST(API_GET_DEPS_STANDINGS)
    Call<ApiResponse> getDepsWithStandings(@Field("is_android") String is_android,
                                           @Field("accessToken") String accessToken
    );

    @FormUrlEncoded
    @POST(API_GET_ITEM_NEWS)
    Call<ApiResponse> getItemNews(@Field("is_android") String is_android,
                                  @Field("accessToken") String accessToken,
                                  @Field("type") String type,
                                  @Field("item_id") String item_id

    );

    @FormUrlEncoded
    @POST(API_GET_PLAYERS)
    Call<ApiResponse> getItemPlayersDetailed(@Field("is_android") String is_android,
                                             @Field("accessToken") String accessToken,
                                             @Field("item_type") String item_type,
                                             @Field("item_id") String item_id
    );

    @FormUrlEncoded
    @POST(API_GET_LATEST_NEWS)
    Call<ApiResponse> getLatestNews(@Field("is_android") String is_android,
                                    @Field("accessToken") String accessToken
    );


    @FormUrlEncoded
    @POST(API_GET_MATCH_INFO)
    Call<ApiResponse> GetMatchInfo(@Field("is_android") String is_android,
                                   @Field("accessToken") String accessToken,
                                   @Field("live_id") String live_id
    );


    @FormUrlEncoded
    @POST(API_GET_PLAYER_MATCHES)
    Call<ApiResponse> getPlayerMatches(@Field("is_android") String is_android,
                                       @Field("accessToken") String accessToken,
                                       @Field("player_id") String player_id
    );


    @FormUrlEncoded
    @POST(API_GET_TEAM_INFO)
    Call<ApiResponse> getTeamInfo(@Field("is_android") String is_android,
                                  @Field("accessToken") String accessToken,
                                  @Field("team_id") String team_id
    );

    @FormUrlEncoded
    @POST(API_GET_TIME_LINE)
    Call<ApiResponse> getTimeline(@Field("is_android") String is_android,
                                  @Field("accessToken") String accessToken,
                                  @Field("live_id") String live_id
    );

    @FormUrlEncoded
    @POST(API_GET_TRENDING_TEAMS)
    Call<ApiResponse> getTrendingTeams(@Field("is_android") String is_android,
                                       @Field("accessToken") String accessToken
    );


    @FormUrlEncoded
    @POST(API_GET_MATCH_INFO)
    Call<ApiResponse> getMatchInfo(@Field("is_android") String is_android,
                                  @Field("accessToken") String accessToken,
                                  @Field("live_id") String live_id
    );


}