
package com.app_republic.kora.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TrendingTeam implements Parcelable {

    @SerializedName("dep_id")
    private String mDepId;
    @SerializedName("has_players")
    private String mHasPlayers;
    @SerializedName("has_standings")
    private String mHasStandings;
    @SerializedName("team_id")
    private String mTeamId;
    @SerializedName("team_logo")
    private String mTeamLogo;
    @SerializedName("team_name")
    private String mTeamName;
    @SerializedName("team_name_en")
    private String mTeamNameEn;
    @SerializedName("term_id")
    private String mTermId;

    protected TrendingTeam(Parcel in) {
        mDepId = in.readString();
        mHasPlayers = in.readString();
        mHasStandings = in.readString();
        mTeamId = in.readString();
        mTeamLogo = in.readString();
        mTeamName = in.readString();
        mTeamNameEn = in.readString();
        mTermId = in.readString();
    }

    public static final Creator<TrendingTeam> CREATOR = new Creator<TrendingTeam>() {
        @Override
        public TrendingTeam createFromParcel(Parcel in) {
            return new TrendingTeam(in);
        }

        @Override
        public TrendingTeam[] newArray(int size) {
            return new TrendingTeam[size];
        }
    };

    public String getDepId() {
        return mDepId;
    }

    public void setDepId(String depId) {
        mDepId = depId;
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

    public String getTeamId() {
        return mTeamId;
    }

    public void setTeamId(String teamId) {
        mTeamId = teamId;
    }

    public String getTeamLogo() {
        return mTeamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        mTeamLogo = teamLogo;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public String getTeamNameEn() {
        return mTeamNameEn;
    }

    public void setTeamNameEn(String teamNameEn) {
        mTeamNameEn = teamNameEn;
    }

    public String getTermId() {
        return mTermId;
    }

    public void setTermId(String termId) {
        mTermId = termId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mDepId);
        parcel.writeString(mHasPlayers);
        parcel.writeString(mHasStandings);
        parcel.writeString(mTeamId);
        parcel.writeString(mTeamLogo);
        parcel.writeString(mTeamName);
        parcel.writeString(mTeamNameEn);
        parcel.writeString(mTermId);
    }
}
