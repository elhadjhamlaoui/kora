package com.app_republic.shoot.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Prediction implements Parcelable {
    private long count1, count2;
    private String targetId, targetType, id;

    public Prediction() {
    }

    public Prediction(long count1, long count2, String targetId, String targetType, String id) {
        this.count1 = count1;
        this.count2 = count2;
        this.targetId = targetId;
        this.targetType = targetType;
        this.id = id;
    }

    protected Prediction(Parcel in) {
        count1 = in.readLong();
        count2 = in.readLong();
        targetId = in.readString();
        targetType = in.readString();
        id = in.readString();
    }

    public static final Creator<Prediction> CREATOR = new Creator<Prediction>() {
        @Override
        public Prediction createFromParcel(Parcel in) {
            return new Prediction(in);
        }

        @Override
        public Prediction[] newArray(int size) {
            return new Prediction[size];
        }
    };

    public long getCount1() {
        return count1;
    }

    public void setCount1(long count1) {
        this.count1 = count1;
    }

    public long getCount2() {
        return count2;
    }

    public void setCount2(long count2) {
        this.count2 = count2;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(count1);
        parcel.writeLong(count2);
        parcel.writeString(targetId);
        parcel.writeString(targetType);
        parcel.writeString(id);
    }
}
