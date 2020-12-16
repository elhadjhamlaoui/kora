
package com.app_republic.shoot.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class VideoItem {

    @SerializedName("live_id")
    private String mLiveId;
    @SerializedName("video_code")
    private String mVideoCode;
    @SerializedName("video_id")
    private String mVideoId;
    @SerializedName("video_image")
    private String mVideoImage;
    @SerializedName("video_title")
    private String mVideoTitle;

    public String getLiveId() {
        return mLiveId;
    }

    public void setLiveId(String liveId) {
        mLiveId = liveId;
    }

    public String getVideoCode() {
        return mVideoCode;
    }

    public void setVideoCode(String videoCode) {
        mVideoCode = videoCode;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }

    public String getVideoImage() {
        return mVideoImage;
    }

    public void setVideoImage(String videoImage) {
        mVideoImage = videoImage;
    }

    public String getVideoTitle() {
        return mVideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        mVideoTitle = videoTitle;
    }

}
