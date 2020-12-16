
package com.app_republic.shoot.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Player implements Parcelable {

    @SerializedName("default_dep_id")
    private String mDefaultDepId;
    @SerializedName("dep_id")
    private String mDepId;
    @SerializedName("dep_image")
    private String mDepImage;
    @SerializedName("dep_name")
    private String mDepName;
    @SerializedName("goals")
    private String mGoals;
    @SerializedName("goals_against")
    private String mGoalsAgainst;
    @SerializedName("has_players")
    private String mHasPlayers;
    @SerializedName("missed_penalty")
    private String mMissedPenalty;
    @SerializedName("name")
    private String mName;
    @SerializedName("name_en")
    private String mNameEn;
    @SerializedName("national_team")
    private String mNationalTeam;
    @SerializedName("national_team_image")
    private String mNationalTeamImage;
    @SerializedName("other_info")
    private ArrayList<Player> mOtherInfo;
    @SerializedName("other_team_id")
    private String mOtherTeamId;
    @SerializedName("other_team_image")
    private String mOtherTeamImage;
    @SerializedName("place")
    private String mPlace;
    @SerializedName("player_age")
    private String mPlayerAge;
    @SerializedName("player_dep_notes")
    private String mPlayerDepNotes;
    @SerializedName("player_id")
    private String mPlayerId;
    @SerializedName("player_image")
    private String mPlayerImage;
    @SerializedName("player_no")
    private String mPlayerNo;
    @SerializedName("player_status")
    private String mPlayerStatus;
    @SerializedName("red_cards")
    private String mRedCards;
    @SerializedName("scored_penalty")
    private String mScoredPenalty;
    @SerializedName("shall_count_pts")
    private String mShallCountPts;
    @SerializedName("team_id")
    private String mTeamId;
    @SerializedName("team_image")
    private String mTeamImage;
    @SerializedName("team_name")
    private String mTeamName;
    @SerializedName("yellow_cards")
    private String mYellowCards;


    protected Player(Parcel in) {
        mDefaultDepId = in.readString();
        mDepId = in.readString();
        mDepImage = in.readString();
        mDepName = in.readString();
        mGoals = in.readString();
        mGoalsAgainst = in.readString();
        mHasPlayers = in.readString();
        mMissedPenalty = in.readString();
        mName = in.readString();
        mNameEn = in.readString();
        mNationalTeam = in.readString();
        mNationalTeamImage = in.readString();
        mOtherInfo = in.createTypedArrayList(Player.CREATOR);
        mOtherTeamId = in.readString();
        mOtherTeamImage = in.readString();
        mPlace = in.readString();
        mPlayerAge = in.readString();
        mPlayerDepNotes = in.readString();
        mPlayerId = in.readString();
        mPlayerImage = in.readString();
        mPlayerNo = in.readString();
        mPlayerStatus = in.readString();
        mRedCards = in.readString();
        mScoredPenalty = in.readString();
        mShallCountPts = in.readString();
        mTeamId = in.readString();
        mTeamImage = in.readString();
        mTeamName = in.readString();
        mYellowCards = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getDefaultDepId() {
        return mDefaultDepId;
    }

    public void setDefaultDepId(String defaultDepId) {
        mDefaultDepId = defaultDepId;
    }

    public String getDepId() {
        return mDepId;
    }

    public void setDepId(String depId) {
        mDepId = depId;
    }

    public String getDepImage() {
        return mDepImage;
    }

    public void setDepImage(String depImage) {
        mDepImage = depImage;
    }

    public String getDepName() {
        return mDepName;
    }

    public void setDepName(String depName) {
        mDepName = depName;
    }

    public String getGoals() {
        return mGoals;
    }

    public void setGoals(String goals) {
        mGoals = goals;
    }

    public String getGoalsAgainst() {
        return mGoalsAgainst;
    }

    public void setGoalsAgainst(String goalsAgainst) {
        mGoalsAgainst = goalsAgainst;
    }

    public String getHasPlayers() {
        return mHasPlayers;
    }

    public void setHasPlayers(String hasPlayers) {
        mHasPlayers = hasPlayers;
    }

    public String getMissedPenalty() {
        return mMissedPenalty;
    }

    public void setMissedPenalty(String missedPenalty) {
        mMissedPenalty = missedPenalty;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNameEn() {
        return mNameEn;
    }

    public void setNameEn(String nameEn) {
        mNameEn = nameEn;
    }

    public String getNationalTeam() {
        return mNationalTeam;
    }

    public void setNationalTeam(String nationalTeam) {
        mNationalTeam = nationalTeam;
    }

    public String getNationalTeamImage() {
        return mNationalTeamImage;
    }

    public void setNationalTeamImage(String nationalTeamImage) {
        mNationalTeamImage = nationalTeamImage;
    }

    public List<Player> getOtherInfo() {
        return mOtherInfo;
    }

    public void setOtherInfo(ArrayList<Player> otherInfo) {
        mOtherInfo = otherInfo;
    }

    public String getOtherTeamId() {
        return mOtherTeamId;
    }

    public void setOtherTeamId(String otherTeamId) {
        mOtherTeamId = otherTeamId;
    }

    public String getOtherTeamImage() {
        return mOtherTeamImage;
    }

    public void setOtherTeamImage(String otherTeamImage) {
        mOtherTeamImage = otherTeamImage;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getPlayerAge() {
        return mPlayerAge;
    }

    public void setPlayerAge(String playerAge) {
        mPlayerAge = playerAge;
    }

    public String getPlayerDepNotes() {
        return mPlayerDepNotes;
    }

    public void setPlayerDepNotes(String playerDepNotes) {
        mPlayerDepNotes = playerDepNotes;
    }

    public String getPlayerId() {
        return mPlayerId;
    }

    public void setPlayerId(String playerId) {
        mPlayerId = playerId;
    }

    public String getPlayerImage() {
        return mPlayerImage;
    }

    public void setPlayerImage(String playerImage) {
        mPlayerImage = playerImage;
    }

    public String getPlayerNo() {
        return mPlayerNo;
    }

    public void setPlayerNo(String playerNo) {
        mPlayerNo = playerNo;
    }

    public String getPlayerStatus() {
        return mPlayerStatus;
    }

    public void setPlayerStatus(String playerStatus) {
        mPlayerStatus = playerStatus;
    }

    public String getRedCards() {
        return mRedCards;
    }

    public void setRedCards(String redCards) {
        mRedCards = redCards;
    }

    public String getScoredPenalty() {
        return mScoredPenalty;
    }

    public void setScoredPenalty(String scoredPenalty) {
        mScoredPenalty = scoredPenalty;
    }

    public String getShallCountPts() {
        return mShallCountPts;
    }

    public void setShallCountPts(String shallCountPts) {
        mShallCountPts = shallCountPts;
    }

    public String getTeamId() {
        return mTeamId;
    }

    public void setTeamId(String teamId) {
        mTeamId = teamId;
    }

    public String getTeamImage() {
        return mTeamImage;
    }

    public void setTeamImage(String teamImage) {
        mTeamImage = teamImage;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public String getYellowCards() {
        return mYellowCards;
    }

    public void setYellowCards(String yellowCards) {
        mYellowCards = yellowCards;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mDefaultDepId);
        parcel.writeString(mDepId);
        parcel.writeString(mDepImage);
        parcel.writeString(mDepName);
        parcel.writeString(mGoals);
        parcel.writeString(mGoalsAgainst);
        parcel.writeString(mHasPlayers);
        parcel.writeString(mMissedPenalty);
        parcel.writeString(mName);
        parcel.writeString(mNameEn);
        parcel.writeString(mNationalTeam);
        parcel.writeString(mNationalTeamImage);
        parcel.writeTypedList(mOtherInfo);
        parcel.writeString(mOtherTeamId);
        parcel.writeString(mOtherTeamImage);
        parcel.writeString(mPlace);
        parcel.writeString(mPlayerAge);
        parcel.writeString(mPlayerDepNotes);
        parcel.writeString(mPlayerId);
        parcel.writeString(mPlayerImage);
        parcel.writeString(mPlayerNo);
        parcel.writeString(mPlayerStatus);
        parcel.writeString(mRedCards);
        parcel.writeString(mScoredPenalty);
        parcel.writeString(mShallCountPts);
        parcel.writeString(mTeamId);
        parcel.writeString(mTeamImage);
        parcel.writeString(mTeamName);
        parcel.writeString(mYellowCards);
    }
}
