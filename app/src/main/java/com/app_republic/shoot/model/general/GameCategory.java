package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

public class GameCategory implements Parcelable {

    private String name, url;

    public GameCategory(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected GameCategory(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<GameCategory> CREATOR = new Creator<GameCategory>() {
        @Override
        public GameCategory createFromParcel(Parcel in) {
            return new GameCategory(in);
        }

        @Override
        public GameCategory[] newArray(int size) {
            return new GameCategory[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
    }
}
