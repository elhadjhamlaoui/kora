
package com.app_republic.kora.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class News implements Parcelable {

    @SerializedName("dislikes_count")
    private String mDislikesCount;
    @SerializedName("guid")
    private String mGuid;
    @SerializedName("ID")
    private String mID;
    @SerializedName("image_thumb")
    private String mImageThumb;
    @SerializedName("likes_count")
    private String mLikesCount;
    @SerializedName("post_content")
    private String mPostContent;
    @SerializedName("post_date")
    private String mPostDate;
    @SerializedName("post_image")
    private String mPostImage;
    @SerializedName("post_title")
    private String mPostTitle;

    protected News(Parcel in) {
        mDislikesCount = in.readString();
        mGuid = in.readString();
        mID = in.readString();
        mImageThumb = in.readString();
        mLikesCount = in.readString();
        mPostContent = in.readString();
        mPostDate = in.readString();
        mPostImage = in.readString();
        mPostTitle = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getDislikesCount() {
        return mDislikesCount;
    }

    public void setDislikesCount(String dislikesCount) {
        mDislikesCount = dislikesCount;
    }

    public String getGuid() {
        return mGuid;
    }

    public void setGuid(String guid) {
        mGuid = guid;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getImageThumb() {
        return mImageThumb;
    }

    public void setImageThumb(String imageThumb) {
        mImageThumb = imageThumb;
    }

    public String getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(String likesCount) {
        mLikesCount = likesCount;
    }

    public String getPostContent() {
        return mPostContent;
    }

    public void setPostContent(String postContent) {
        mPostContent = postContent;
    }

    public String getPostDate() {
        return mPostDate;
    }

    public void setPostDate(String postDate) {
        mPostDate = postDate;
    }

    public String getPostImage() {
        return mPostImage;
    }

    public void setPostImage(String postImage) {
        mPostImage = postImage;
    }

    public String getPostTitle() {
        return mPostTitle;
    }

    public void setPostTitle(String postTitle) {
        mPostTitle = postTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mDislikesCount);
        parcel.writeString(mGuid);
        parcel.writeString(mID);
        parcel.writeString(mImageThumb);
        parcel.writeString(mLikesCount);
        parcel.writeString(mPostContent);
        parcel.writeString(mPostDate);
        parcel.writeString(mPostImage);
        parcel.writeString(mPostTitle);
    }

}
