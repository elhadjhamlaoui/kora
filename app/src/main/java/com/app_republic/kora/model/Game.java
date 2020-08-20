package com.app_republic.kora.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    private String icon, name, url;

    public Game(String icon, String name, String url) {
        this.icon = icon;
        this.name = name;
        this.url = url;
    }

    protected Game(Parcel in) {
        icon = in.readString();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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
        parcel.writeString(icon);
        parcel.writeString(name);
        parcel.writeString(url);
    }
}
