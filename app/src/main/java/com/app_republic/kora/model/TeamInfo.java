
package com.app_republic.kora.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TeamInfo implements Parcelable {

    @SerializedName("coming_match_date")
    private String mComingMatchDate;
    @SerializedName("dep_id")
    private String mDepId;
    @SerializedName("group_no")
    private String mGroupNo;
    @SerializedName("has_groups")
    private String mHasGroups;
    @SerializedName("has_players")
    private String mHasPlayers;
    @SerializedName("has_standings")
    private String mHasStandings;
    @SerializedName("is_best")
    private String mIsBest;
    @SerializedName("is_faved")
    private String mIsFaved;
    @SerializedName("recent_matches")
    private List<Match> mRecentMatches;
    @SerializedName("team_country")
    private String mTeamCountry;
    @SerializedName("team_id")
    private String mTeamId;
    @SerializedName("team_logo")
    private String mTeamLogo;
    @SerializedName("team_name")
    private String mTeamName;
    @SerializedName("team_name_en")
    private String mTeamNameEn;

    protected TeamInfo(Parcel in) {
        mComingMatchDate = in.readString();
        mDepId = in.readString();
        mGroupNo = in.readString();
        mHasGroups = in.readString();
        mHasPlayers = in.readString();
        mHasStandings = in.readString();
        mIsBest = in.readString();
        mIsFaved = in.readString();
        mTeamCountry = in.readString();
        mTeamId = in.readString();
        mTeamLogo = in.readString();
        mTeamName = in.readString();
        mTeamNameEn = in.readString();
    }

    public static final Creator<TeamInfo> CREATOR = new Creator<TeamInfo>() {
        @Override
        public TeamInfo createFromParcel(Parcel in) {
            return new TeamInfo(in);
        }

        @Override
        public TeamInfo[] newArray(int size) {
            return new TeamInfo[size];
        }
    };

    public String getComingMatchDate() {
        return mComingMatchDate;
    }

    public void setComingMatchDate(String comingMatchDate) {
        mComingMatchDate = comingMatchDate;
    }

    public String getDepId() {
        return mDepId;
    }

    public void setDepId(String depId) {
        mDepId = depId;
    }

    public String getGroupNo() {
        return mGroupNo;
    }

    public void setGroupNo(String groupNo) {
        mGroupNo = groupNo;
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

    public String getIsBest() {
        return mIsBest;
    }

    public void setIsBest(String isBest) {
        mIsBest = isBest;
    }

    public String getIsFaved() {
        return mIsFaved;
    }

    public void setIsFaved(String isFaved) {
        mIsFaved = isFaved;
    }

    public List<Match> getRecentMatches() {
        return mRecentMatches;
    }

    public void setRecentMatches(List<Match> recentMatches) {
        mRecentMatches = recentMatches;
    }

    public String getTeamCountry() {
        return mTeamCountry;
    }

    public void setTeamCountry(String teamCountry) {
        mTeamCountry = teamCountry;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mComingMatchDate);
        parcel.writeString(mDepId);
        parcel.writeString(mGroupNo);
        parcel.writeString(mHasGroups);
        parcel.writeString(mHasPlayers);
        parcel.writeString(mHasStandings);
        parcel.writeString(mIsBest);
        parcel.writeString(mIsFaved);
        parcel.writeString(mTeamCountry);
        parcel.writeString(mTeamId);
        parcel.writeString(mTeamLogo);
        parcel.writeString(mTeamName);
        parcel.writeString(mTeamNameEn);
    }
}
