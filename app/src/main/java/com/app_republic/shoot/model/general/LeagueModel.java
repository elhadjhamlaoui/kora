package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

public class LeagueModel implements Parcelable {

    private String country;
    private int id;

    private int rank;

    public LeagueModel() {

    }


    protected LeagueModel(Parcel in) {
        id = in.readInt();
        rank = in.readInt();
        country = in.readString();
    }

    public static final Creator<LeagueModel> CREATOR = new Creator<LeagueModel>() {
        @Override
        public LeagueModel createFromParcel(Parcel in) {
            return new LeagueModel(in);
        }

        @Override
        public LeagueModel[] newArray(int size) {
            return new LeagueModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(rank);
        parcel.writeString(country);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
