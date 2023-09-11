
package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Department implements Parcelable {

    @SerializedName("dep_id")
    private String mDepId;
    @SerializedName("dep_logo")
    private String mDepLogo;
    @SerializedName("dep_name")
    private String mDepName;
    @SerializedName("dep_name_en")
    private String mDepNameEn;
    @SerializedName("has_groups")
    private String mHasGroups;
    @SerializedName("has_players")
    private String mHasPlayers;
    @SerializedName("has_standings")
    private String mHasStandings;


    public Department(String mDepId, String mDepLogo, String mDepName, String mDepNameEn, String mHasGroups, String mHasPlayers, String mHasStandings) {
        this.mDepId = mDepId;
        this.mDepLogo = mDepLogo;
        this.mDepName = mDepName;
        this.mDepNameEn = mDepNameEn;
        this.mHasGroups = mHasGroups;
        this.mHasPlayers = mHasPlayers;
        this.mHasStandings = mHasStandings;
    }

    protected Department(Parcel in) {
        mDepId = in.readString();
        mDepLogo = in.readString();
        mDepName = in.readString();
        mDepNameEn = in.readString();
        mHasGroups = in.readString();
        mHasPlayers = in.readString();
        mHasStandings = in.readString();
    }

    public static final Creator<Department> CREATOR = new Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };

    public String getDepId() {
        return mDepId;
    }

    public void setDepId(String depId) {
        mDepId = depId;
    }

    public String getDepLogo() {
        return mDepLogo;
    }

    public void setDepLogo(String depLogo) {
        mDepLogo = depLogo;
    }

    public String getDepName() {
        return mDepName;
    }

    public void setDepName(String depName) {
        mDepName = depName;
    }

    public String getDepNameEn() {
        return mDepNameEn;
    }

    public void setDepNameEn(String depNameEn) {
        mDepNameEn = depNameEn;
    }

    public String getHasGroups() {
        return mHasGroups;
    }

    public void setHasGroups(String hasGroups) {
        mHasGroups = hasGroups;
    }

    public String getHasPlayers() {
        return mHasPlayers;
    }

    public void setHasPlayers(String hasPlayers) {
        mHasPlayers = hasPlayers;
    }

    public String getHasStandings() {
        return mHasStandings;
    }

    public void setHasStandings(String hasStandings) {
        mHasStandings = hasStandings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mDepId);
        parcel.writeString(mDepLogo);
        parcel.writeString(mDepName);
        parcel.writeString(mDepNameEn);
        parcel.writeString(mHasGroups);
        parcel.writeString(mHasPlayers);
        parcel.writeString(mHasStandings);
    }
}
