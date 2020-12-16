
package com.app_republic.shoot.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TimeLine {

    @SerializedName("date_added")
    private String mDateAdded;
    @SerializedName("id")
    private String mId;
    @SerializedName("original_date_added")
    private String mOriginalDateAdded;
    @SerializedName("player_id")
    private String mPlayerId;
    @SerializedName("player_image")
    private String mPlayerImage;
    @SerializedName("player_status")
    private String mPlayerStatus;
    @SerializedName("team")
    private String mTeam;
    @SerializedName("text")
    private String mText;
    @SerializedName("time")
    private String mTime;
    @SerializedName("type")
    private String mType;
    @SerializedName("video_id")
    private String mVideoId;
    @SerializedName("video_item")
    private VideoItem mVideoItem;

    public String getDateAdded() {
        return mDateAdded;
    }

    public void setDateAdded(String dateAdded) {
        mDateAdded = dateAdded;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getOriginalDateAdded() {
        return mOriginalDateAdded;
    }

    public void setOriginalDateAdded(String originalDateAdded) {
        mOriginalDateAdded = originalDateAdded;
    }

    public String getPlayerId() {
        return mPlayerId;
    }

    public void setPlayerId(String playerId) {
        mPlayerId = playerId;
    }

    public String getPlayerImage() {
        return mPlayerImage;
    }

    public void setPlayerImage(String playerImage) {
        mPlayerImage = playerImage;
    }

    public String getPlayerStatus() {
        return mPlayerStatus;
    }

    public void setPlayerStatus(String playerStatus) {
        mPlayerStatus = playerStatus;
    }

    public String getTeam() {
        return mTeam;
    }

    public void setTeam(String team) {
        mTeam = team;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }

    public VideoItem getVideoItem() {
        return mVideoItem;
    }

    public void setVideoItem(VideoItem videoItem) {
        mVideoItem = videoItem;
    }

}
