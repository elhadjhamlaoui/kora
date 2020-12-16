
package com.app_republic.shoot.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class ApiResponse {

    @SerializedName("cacheKey")
    private String mCacheKey;
    @SerializedName("cacheTag")
    private String mCacheTag;
    @SerializedName("count")
    private Long mCount;
    @SerializedName("current_date")
    private String mCurrentDate;
    @SerializedName("items")
    private List<Object> mItems;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("pages_count")
    private Long mPagesCount;
    @SerializedName("status")
    private Boolean mStatus;

    public String getCacheKey() {
        return mCacheKey;
    }

    public void setCacheKey(String cacheKey) {
        mCacheKey = cacheKey;
    }

    public String getCacheTag() {
        return mCacheTag;
    }

    public void setCacheTag(String cacheTag) {
        mCacheTag = cacheTag;
    }

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public String getCurrentDate() {
        return mCurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        mCurrentDate = currentDate;
    }

    public List<Object> getItems() {
        return mItems;
    }

    public void setItems(List<Object> items) {
        mItems = items;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getPagesCount() {
        return mPagesCount;
    }

    public void setPagesCount(Long pagesCount) {
        mPagesCount = pagesCount;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
