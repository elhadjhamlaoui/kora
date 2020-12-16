
package com.app_republic.shoot.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Video implements Parcelable {

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

    public Video() {
    }


    public Video(String mLiveId, String mVideoCode, String mVideoId, String mVideoImage, String mVideoTitle) {
        this.mLiveId = mLiveId;
        this.mVideoCode = mVideoCode;
        this.mVideoId = mVideoId;
        this.mVideoImage = mVideoImage;
        this.mVideoTitle = mVideoTitle;
    }

    protected Video(Parcel in) {
        mLiveId = in.readString();
        mVideoCode = in.readString();
        mVideoId = in.readString();
        mVideoImage = in.readString();
        mVideoTitle = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mLiveId);
        parcel.writeString(mVideoCode);
        parcel.writeString(mVideoId);
        parcel.writeString(mVideoImage);
        parcel.writeString(mVideoTitle);
    }
}
