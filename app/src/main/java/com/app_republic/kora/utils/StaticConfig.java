package com.app_republic.kora.utils;

public class StaticConfig {
    public static final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    public static final String COMMENT = "comment";
    public static final String SUB_COMMENT = "sub_comment";
    public static final String TARGET_TYPE = "target_type";
    public static final String TARGET_ID = "target_id";
    public static final String USER = "user";
    public static final String RESULT_DONE = "result_done";
    public static final String CHAT = "chat";
    public static final String FRAGMENT_COMMENTS = "fragment_comments";
    public static final String COUNTRY = "country";
    public static final String PREDICTION = "prediction";
    public static final String MATCH_LIVE_URL = "matvh_live_url";
    public static final String IS_LIVE = "is_live";


    public static String API_BASE = "https://yalla-group.com/api-v3.4.520/api/";

    public static final String API_GET_MATCHES = "getMatches";
    public static final String API_GET_MATCH_INFO = "getMatchInfo";
    public static final String API_GET_TEAM_INFO = "getTeamInfo";
    public static final String API_GET_MATCH_VIDEOS = "getVideos";
    public static final String ADVERT = "advert";
    public static final int REQUEST_INTER_AD = 112;


    public static long TIME_DIFFERENCE;

    public static final String API_GET_DEPS_STANDINGS = "getDepsWithStandings";
    public static final String API_GET_DEPS_PLAYERS = "getDepsWithPlayers";
    public static final String API_GET_PLAYERS = "getItemPlayersDetailed";
    public static final String API_GET_PLAYER_MATCHES = "getPlayerMatches";

    public static final int NUMBER_OF_NATIVE_ADS = 2;
    public static final int NUMBER_OF_NATIVE_ADS_MATCHES = 2;
    public static final int NUMBER_OF_NATIVE_ADS_NEWS = 5;
    public static final int NUMBER_OF_NATIVE_ADS_VIDEOS = 1;



    public static final int CONTENT_ITEM_VIEW_TYPE = 0;

    public static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;



    public static final String API_GET_LATEST_NEWS = "getLatestNews";
    public static final String API_GET_TRENDING_TEAMS = "getTrendingTeams";
    public static final String API_GET_DEP_STANDINGS = "getDepStandings";
    public static final String API_GET_ITEM_NEWS = "getItemNews";
    public static final String API_GET_TIME_LINE = "getTimeline";

    public static final String NEWS = "news";
    public static final String TEAM = "team";
    public static final String THUMB = "thumb";
    public static final String MATCH = "match";
    public static final String RESULT = "result";
    public static final String STATE = "state";
    public static final String FRAGMENT_DEPS = "fragment_departments";
    public static final String FRAGMENT_STANDINGS = "fragment_standings";
    public static final String FRAGMENT_PLAYERS = "fragment_players";
    public static final String TEAM_INFO = "team_info";


    public static final String PARAM_DEPS_TYPE = "deps_type";
    public static final String PARAM_DEP_ID = "dep_id";
    public static final String PARAM_ITEM_ID = "item_id";
    public static final String PARAM_ITEM_TYPE = "item_type";

    public static final String PARAM_ITEM_TYPE_PLAYER = "Player";
    public static final String PARAM_ITEM_TYPE_DEPARTMENT = "dep";
    public static final String PARAM_ITEM_TYPE_TEAM = "team";

    public static final String DEPS_TYPE_PLAYERS = "players";
    public static final String DEPS_TYPE_STANDINGS = "standings";
    public static final String PARAM_TYPE_MATCH = "match";
    public static final String PARAM_TEAM_ID = "team_id";
    public static final String PARAM_TEAM_ID_A = "team_id_a";
    public static final String PARAM_TEAM_ID_B = "team_id_b";
    public static final String PARAM_PLAYER_ID = "player_id";
    public static final String PLAYER = "player";
    public static final String PLAYER_INFO = "player_info" ;
    public static final String VIDEO_URI = "video_uri";
    public static final String GAME = "game";


    public static String ENDED = "ended";
    public static String COMING = "coming";
    public static String CURRENT = "current";
    public static long HOUR_MILLIS = 60 * 60 * 1000;
    public static String MATCHES_REQUEST = "matches";
    public static String LATEST_NEWS_REQUEST = "latest_news";
    public static String TRENDING_TEAMS_REQUEST = "trending_teams";
    public static String MATCH_INFO_REQUEST = "match_info";
    public static String DEPS_WITH_STANDINGS_REQUEST = "deps_with_standings";
    public static String DEP_STANDINGS_REQUEST = "dep_standings";
    public static String DEPS_WITH_PLAYERS_REQUEST = "dep_players";
    public static String DEP_PLAYERS_REQUEST = "players_for_dep";
    public static String ITEM_NEWS_REQUEST = "item_news_request";
    public static String TIME_LINE_REQUEST = "time_line_request";
    public static String TEAM_INFO_REQUEST = "team_info_request";
    public static String PLAYER_MATCHES_REQUEST = "player_matches";




}
