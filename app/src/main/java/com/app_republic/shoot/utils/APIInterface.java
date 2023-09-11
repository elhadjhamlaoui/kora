package com.app_republic.shoot.utils;

import com.app_republic.shoot.model.Events.EventsResponse;
import com.app_republic.shoot.model.LeagueStandingResponse.LeagueStandingResponse;
import com.app_republic.shoot.model.PlayerSquads.PlayerSquadsResponse;
import com.app_republic.shoot.model.PlayersResponse.PlayersResponse;
import com.app_republic.shoot.model.TeamInfos.TeamInfosResponse;
import com.app_republic.shoot.model.general.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.app_republic.shoot.utils.StaticConfig.API_GET_DEPS_PLAYERS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_DEPS_STANDINGS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_DEP_STANDINGS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_ITEM_NEWS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_LATEST_NEWS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_LEAGUES;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_MATCHES;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_MATCH_INFO;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_MATCH_VIDEOS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_PLAYERS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_PLAYER_MATCHES;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_PLAYER_SQUADS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_PLAYER_STATISTICS;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_TEAM_INFO;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_TIME_LINE;
import static com.app_republic.shoot.utils.StaticConfig.API_GET_TRENDING_TEAMS;

public interface APIInterface {

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_MATCHES)
    Call<ApiResponse> getMatches(@Query("date") String date
    );

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_TEAM_INFO)
    Call<TeamInfosResponse> getTeamInfos(@Query("id") String id
    );

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_MATCHES)
    Call<ApiResponse> getMatchById(@Query("id") String id
    );

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_DEP_STANDINGS)
    Call<LeagueStandingResponse> getDepStandings(@Query("league") String league, @Query("season") int season
    );


    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_LEAGUES)
    Call<LeagueStandingResponse> getLeagues(@Query("league") String league, @Query("season") int season
    );

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_PLAYER_SQUADS)
    Call<PlayerSquadsResponse> getPlayerSquads(@Query("team") String team
    );

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_TIME_LINE)
    Call<EventsResponse> getTimeline(@Query("fixture") String fixture
    );

    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_DEPS_PLAYERS)
    Call<ApiResponse> getDepsWithPlayers(@Field("is_android") String is_android,
                                         @Field("accessToken") String accessToken

    );

    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_DEPS_STANDINGS)
    Call<ApiResponse> getDepsWithStandings(@Field("is_android") String is_android,
                                           @Field("accessToken") String accessToken
    );

    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_ITEM_NEWS)
    Call<ApiResponse> getItemNews(@Field("is_android") String is_android,
                                  @Field("accessToken") String accessToken,
                                  @Field("type") String type,
                                  @Field("item_id") String item_id

    );

    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_PLAYERS)
    Call<ApiResponse> getItemPlayersDetailed(@Field("is_android") String is_android,
                                             @Field("accessToken") String accessToken,
                                             @Field("item_type") String item_type,
                                             @Field("item_id") String item_id
    );

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_PLAYERS)
    Call<PlayersResponse> getPlayersByLeague(@Query("league") String league, @Query("season") int season
    );

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_PLAYER_STATISTICS)
    Call<PlayersResponse> getPlayerById(@Query("id") String id, @Query("season") int season
    );

    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_PLAYER_STATISTICS)
    Call<PlayersResponse> getPlayersByTeam(@Query("team") String league, @Query("season") int season
    );


    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_PLAYER_STATISTICS)
    Call<PlayersResponse> getPlayerStatistics(@Query("id") String id, @Query("season") int season
    );


    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_LATEST_NEWS)
    Call<ApiResponse> getLatestNews(@Field("is_android") String is_android,
                                    @Field("accessToken") String accessToken
    );


    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_MATCH_INFO)
    Call<ApiResponse> GetMatchInfo(@Field("is_android") String is_android,
                                   @Field("accessToken") String accessToken,
                                   @Field("live_id") String live_id
    );


    @Headers({"X-RapidAPI-Key: iinOFooI2Gmshsr6k1f5A5og0VKRp1y19yNjsnQTviwr1SETsz", "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"})
    @GET(API_GET_MATCHES)
    Call<ApiResponse> getPlayerMatches(@Query("team") String league, @Query("season") int season
    );


    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_TRENDING_TEAMS)
    Call<ApiResponse> getTrendingTeams(@Field("is_android") String is_android,
                                       @Field("accessToken") String accessToken
    );


    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_MATCH_INFO)
    Call<ApiResponse> getMatchInfo(@Field("is_android") String is_android,
                                  @Field("accessToken") String accessToken,
                                  @Field("live_id") String live_id
    );

    @FormUrlEncoded
    @Headers({"User-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0"})
    @POST(API_GET_MATCH_VIDEOS)
    Call<ApiResponse> getVideos(@Field("is_android") String is_android,
                                   @Field("accessToken") String accessToken,
                                   @Field("live_id") String live_id
    );


}