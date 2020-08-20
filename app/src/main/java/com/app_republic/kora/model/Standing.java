
package com.app_republic.kora.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Standing implements Parcelable {

    @SerializedName("against")
    private String mAgainst;
    @SerializedName("dep_for")
    private String mDepFor;
    @SerializedName("dep_id")
    private String mDepId;
    @SerializedName("dep_name")
    private String mDepName;
    @SerializedName("draw")
    private String mDraw;
    @SerializedName("goals_diff")
    private String mGoalsDiff;
    @SerializedName("group_no")
    private String mGroupNo;
    @SerializedName("has_groups")
    private String mHasGroups;
    @SerializedName("lose")
    private String mLose;
    @SerializedName("other_team_name")
    private String mOtherTeamName;
    @SerializedName("play")
    private String mPlay;
    @SerializedName("points")
    private String mPoints;
    @SerializedName("team_id")
    private String mTeamId;
    @SerializedName("team_logo")
    private String mTeamLogo;
    @SerializedName("team_name")
    private String mTeamName;
    @SerializedName("win")
    private String mWin;

    protected Standing(Parcel in) {
        mAgainst = in.readString();
        mDepFor = in.readString();
        mDepId = in.readString();
        mDepName = in.readString();
        mDraw = in.readString();
        mGoalsDiff = in.readString();
        mGroupNo = in.readString();
        mHasGroups = in.readString();
        mLose = in.readString();
        mOtherTeamName = in.readString();
        mPlay = in.readString();
        mPoints = in.readString();
        mTeamId = in.readString();
        mTeamLogo = in.readString();
        mTeamName = in.readString();
        mWin = in.readString();
    }

    public static final Creator<Standing> CREATOR = new Creator<Standing>() {
        @Override
        public Standing createFromParcel(Parcel in) {
            return new Standing(in);
        }

        @Override
        public Standing[] newArray(int size) {
            return new Standing[size];
        }
    };

    public String getAgainst() {
        return mAgainst;
    }

    public void setAgainst(String against) {
        mAgainst = against;
    }

    public String getDepFor() {
        return mDepFor;
    }

    public void setDepFor(String depFor) {
        mDepFor = depFor;
    }

    public String getDepId() {
        return mDepId;
    }

    public void setDepId(String depId) {
        mDepId = depId;
    }

    public String getDepName() {
        return mDepName;
    }

    public void setDepName(String depName) {
        mDepName = depName;
    }

    public String getDraw() {
        return mDraw;
    }

    public void setDraw(String draw) {
        mDraw = draw;
    }

    public String getGoalsDiff() {
        return mGoalsDiff;
    }

    public void setGoalsDiff(String goalsDiff) {
        mGoalsDiff = goalsDiff;
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

    public String getLose() {
        return mLose;
    }

    public void setLose(String lose) {
        mLose = lose;
    }

    public String getOtherTeamName() {
        return mOtherTeamName;
    }

    public void setOtherTeamName(String otherTeamName) {
        mOtherTeamName = otherTeamName;
    }

    public String getPlay() {
        return mPlay;
    }

    public void setPlay(String play) {
        mPlay = play;
    }

    public String getPoints() {
        return mPoints;
    }

    public void setPoints(String points) {
        mPoints = points;
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

    public String getWin() {
        return mWin;
    }

    public void setWin(String win) {
        mWin = win;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAgainst);
        parcel.writeString(mDepFor);
        parcel.writeString(mDepId);
        parcel.writeString(mDepName);
        parcel.writeString(mDraw);
        parcel.writeString(mGoalsDiff);
        parcel.writeString(mGroupNo);
        parcel.writeString(mHasGroups);
        parcel.writeString(mLose);
        parcel.writeString(mOtherTeamName);
        parcel.writeString(mPlay);
        parcel.writeString(mPoints);
        parcel.writeString(mTeamId);
        parcel.writeString(mTeamLogo);
        parcel.writeString(mTeamName);
        parcel.writeString(mWin);
    }
}
