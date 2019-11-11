
package com.app_republic.kora.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Match implements Parcelable, Comparable {

    @SerializedName("actual_minutes")
    private String mActualMinutes;
    @SerializedName("dep_id")
    private String mDepId;
    @SerializedName("dep_logo")
    private String mDepLogo;
    @SerializedName("dep_order")
    private String mDepOrder;
    @SerializedName("full_datetime")
    private String mFullDatetime;
    @SerializedName("full_datetime_spaces")
    private String mFullDatetimeSpaces;
    @SerializedName("full_time")
    private String mFullTime;
    @SerializedName("has_players")
    private String mHasPlayers;
    @SerializedName("has_standings")
    private String mHasStandings;
    @SerializedName("has_timeline")
    private String mHasTimeline;
    @SerializedName("has_videos")
    private String mHasVideos;
    @SerializedName("live_comm")
    private String mLiveComm;
    @SerializedName("live_date")
    private String mLiveDate;
    @SerializedName("live_dep")
    private String mLiveDep;
    @SerializedName("live_h")
    private String mLiveH;
    @SerializedName("live_home")
    private String mLiveHome;
    @SerializedName("live_home_2")
    private String mLiveHome2;
    @SerializedName("live_home_id")
    private String mLiveHomeId;
    @SerializedName("live_id")
    private String mLiveId;
    @SerializedName("live_imp")
    private String mLiveImp;
    @SerializedName("live_islive")
    private String mLiveIslive;
    @SerializedName("live_m")
    private String mLiveM;
    @SerializedName("live_m3")
    private String mLiveM3;
    @SerializedName("live_note")
    private String mLiveNote;
    @SerializedName("live_pam")
    private String mLivePam;
    @SerializedName("live_pe1")
    private String mLivePe1;
    @SerializedName("live_pe2")
    private String mLivePe2;
    @SerializedName("live_re1")
    private String mLiveRe1;
    @SerializedName("live_re2")
    private String mLiveRe2;
    @SerializedName("live_role")
    private String mLiveRole;
    @SerializedName("live_stad")
    private String mLiveStad;
    @SerializedName("live_stu")
    private String mLiveStu;
    @SerializedName("live_team1")
    private String mLiveTeam1;
    @SerializedName("live_team2")
    private String mLiveTeam2;
    @SerializedName("live_title")
    private String mLiveTitle;
    @SerializedName("live_tv")
    private String mLiveTv;
    @SerializedName("remaining_seconds")
    private String mRemainingSeconds;
    @SerializedName("remaining_time")
    private String mRemainingTime;
    @SerializedName("team_a_hasPlayers")
    private String mTeamAHasPlayers;
    @SerializedName("team_b_hasPlayers")
    private String mTeamBHasPlayers;
    @SerializedName("team_id_a")
    private String mTeamIdA;
    @SerializedName("team_id_b")
    private String mTeamIdB;
    @SerializedName("team_logo_a")
    private String mTeamLogoA;
    @SerializedName("team_logo_b")
    private String mTeamLogoB;

    protected Match(Parcel in) {
        mActualMinutes = in.readString();
        mDepId = in.readString();
        mDepLogo = in.readString();
        mDepOrder = in.readString();
        mFullDatetime = in.readString();
        mFullDatetimeSpaces = in.readString();
        mFullTime = in.readString();
        mHasPlayers = in.readString();
        mHasStandings = in.readString();
        mHasTimeline = in.readString();
        mHasVideos = in.readString();
        mLiveComm = in.readString();
        mLiveDate = in.readString();
        mLiveDep = in.readString();
        mLiveH = in.readString();
        mLiveHome = in.readString();
        mLiveHome2 = in.readString();
        mLiveHomeId = in.readString();
        mLiveId = in.readString();
        mLiveImp = in.readString();
        mLiveIslive = in.readString();
        mLiveM = in.readString();
        mLiveM3 = in.readString();
        mLiveNote = in.readString();
        mLivePam = in.readString();
        mLivePe1 = in.readString();
        mLivePe2 = in.readString();
        mLiveRe1 = in.readString();
        mLiveRe2 = in.readString();
        mLiveRole = in.readString();
        mLiveStad = in.readString();
        mLiveStu = in.readString();
        mLiveTeam1 = in.readString();
        mLiveTeam2 = in.readString();
        mLiveTitle = in.readString();
        mLiveTv = in.readString();
        mRemainingSeconds = in.readString();
        mRemainingTime = in.readString();
        mTeamAHasPlayers = in.readString();
        mTeamBHasPlayers = in.readString();
        mTeamIdA = in.readString();
        mTeamIdB = in.readString();
        mTeamLogoA = in.readString();
        mTeamLogoB = in.readString();
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public String getActualMinutes() {
        return mActualMinutes;
    }

    public void setActualMinutes(String actualMinutes) {
        mActualMinutes = actualMinutes;
    }

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

    public String getDepOrder() {
        return mDepOrder;
    }

    public void setDepOrder(String depOrder) {
        mDepOrder = depOrder;
    }

    public String getFullDatetime() {
        return mFullDatetime;
    }

    public void setFullDatetime(String fullDatetime) {
        mFullDatetime = fullDatetime;
    }

    public String getFullDatetimeSpaces() {
        return mFullDatetimeSpaces;
    }

    public void setFullDatetimeSpaces(String fullDatetimeSpaces) {
        mFullDatetimeSpaces = fullDatetimeSpaces;
    }

    public String getFullTime() {
        return mFullTime;
    }

    public void setFullTime(String fullTime) {
        mFullTime = fullTime;
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

    public String getHasTimeline() {
        return mHasTimeline;
    }

    public void setHasTimeline(String hasTimeline) {
        mHasTimeline = hasTimeline;
    }

    public String getHasVideos() {
        return mHasVideos;
    }

    public void setHasVideos(String hasVideos) {
        mHasVideos = hasVideos;
    }

    public String getLiveComm() {
        return mLiveComm;
    }

    public void setLiveComm(String liveComm) {
        mLiveComm = liveComm;
    }

    public String getLiveDate() {
        return mLiveDate;
    }

    public void setLiveDate(String liveDate) {
        mLiveDate = liveDate;
    }

    public String getLiveDep() {
        return mLiveDep;
    }

    public void setLiveDep(String liveDep) {
        mLiveDep = liveDep;
    }

    public String getLiveH() {
        return mLiveH;
    }

    public void setLiveH(String liveH) {
        mLiveH = liveH;
    }

    public String getLiveHome() {
        return mLiveHome;
    }

    public void setLiveHome(String liveHome) {
        mLiveHome = liveHome;
    }

    public String getLiveHome2() {
        return mLiveHome2;
    }

    public void setLiveHome2(String liveHome2) {
        mLiveHome2 = liveHome2;
    }

    public String getLiveHomeId() {
        return mLiveHomeId;
    }

    public void setLiveHomeId(String liveHomeId) {
        mLiveHomeId = liveHomeId;
    }

    public String getLiveId() {
        return mLiveId;
    }

    public void setLiveId(String liveId) {
        mLiveId = liveId;
    }

    public String getLiveImp() {
        return mLiveImp;
    }

    public void setLiveImp(String liveImp) {
        mLiveImp = liveImp;
    }

    public String getLiveIslive() {
        return mLiveIslive;
    }

    public void setLiveIslive(String liveIslive) {
        mLiveIslive = liveIslive;
    }

    public String getLiveM() {
        return mLiveM;
    }

    public void setLiveM(String liveM) {
        mLiveM = liveM;
    }

    public String getLiveM3() {
        return mLiveM3;
    }

    public void setLiveM3(String liveM3) {
        mLiveM3 = liveM3;
    }

    public String getLiveNote() {
        return mLiveNote;
    }

    public void setLiveNote(String liveNote) {
        mLiveNote = liveNote;
    }

    public String getLivePam() {
        return mLivePam;
    }

    public void setLivePam(String livePam) {
        mLivePam = livePam;
    }

    public String getLivePe1() {
        return mLivePe1;
    }

    public void setLivePe1(String livePe1) {
        mLivePe1 = livePe1;
    }

    public String getLivePe2() {
        return mLivePe2;
    }

    public void setLivePe2(String livePe2) {
        mLivePe2 = livePe2;
    }

    public String getLiveRe1() {
        return mLiveRe1;
    }

    public void setLiveRe1(String liveRe1) {
        mLiveRe1 = liveRe1;
    }

    public String getLiveRe2() {
        return mLiveRe2;
    }

    public void setLiveRe2(String liveRe2) {
        mLiveRe2 = liveRe2;
    }

    public String getLiveRole() {
        return mLiveRole;
    }

    public void setLiveRole(String liveRole) {
        mLiveRole = liveRole;
    }

    public String getLiveStad() {
        return mLiveStad;
    }

    public void setLiveStad(String liveStad) {
        mLiveStad = liveStad;
    }

    public String getLiveStu() {
        return mLiveStu;
    }

    public void setLiveStu(String liveStu) {
        mLiveStu = liveStu;
    }

    public String getLiveTeam1() {
        return mLiveTeam1;
    }

    public void setLiveTeam1(String liveTeam1) {
        mLiveTeam1 = liveTeam1;
    }

    public String getLiveTeam2() {
        return mLiveTeam2;
    }

    public void setLiveTeam2(String liveTeam2) {
        mLiveTeam2 = liveTeam2;
    }

    public String getLiveTitle() {
        return mLiveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        mLiveTitle = liveTitle;
    }

    public String getLiveTv() {
        return mLiveTv;
    }

    public void setLiveTv(String liveTv) {
        mLiveTv = liveTv;
    }

    public String getRemainingSeconds() {
        return mRemainingSeconds;
    }

    public void setRemainingSeconds(String remainingSeconds) {
        mRemainingSeconds = remainingSeconds;
    }

    public String getRemainingTime() {
        return mRemainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        mRemainingTime = remainingTime;
    }

    public String getTeamAHasPlayers() {
        return mTeamAHasPlayers;
    }

    public void setTeamAHasPlayers(String teamAHasPlayers) {
        mTeamAHasPlayers = teamAHasPlayers;
    }

    public String getTeamBHasPlayers() {
        return mTeamBHasPlayers;
    }

    public void setTeamBHasPlayers(String teamBHasPlayers) {
        mTeamBHasPlayers = teamBHasPlayers;
    }

    public String getTeamIdA() {
        return mTeamIdA;
    }

    public void setTeamIdA(String teamIdA) {
        mTeamIdA = teamIdA;
    }

    public String getTeamIdB() {
        return mTeamIdB;
    }

    public void setTeamIdB(String teamIdB) {
        mTeamIdB = teamIdB;
    }

    public String getTeamLogoA() {
        return mTeamLogoA;
    }

    public void setTeamLogoA(String teamLogoA) {
        mTeamLogoA = teamLogoA;
    }

    public String getTeamLogoB() {
        return mTeamLogoB;
    }

    public void setTeamLogoB(String teamLogoB) {
        mTeamLogoB = teamLogoB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mActualMinutes);
        parcel.writeString(mDepId);
        parcel.writeString(mDepLogo);
        parcel.writeString(mDepOrder);
        parcel.writeString(mFullDatetime);
        parcel.writeString(mFullDatetimeSpaces);
        parcel.writeString(mFullTime);
        parcel.writeString(mHasPlayers);
        parcel.writeString(mHasStandings);
        parcel.writeString(mHasTimeline);
        parcel.writeString(mHasVideos);
        parcel.writeString(mLiveComm);
        parcel.writeString(mLiveDate);
        parcel.writeString(mLiveDep);
        parcel.writeString(mLiveH);
        parcel.writeString(mLiveHome);
        parcel.writeString(mLiveHome2);
        parcel.writeString(mLiveHomeId);
        parcel.writeString(mLiveId);
        parcel.writeString(mLiveImp);
        parcel.writeString(mLiveIslive);
        parcel.writeString(mLiveM);
        parcel.writeString(mLiveM3);
        parcel.writeString(mLiveNote);
        parcel.writeString(mLivePam);
        parcel.writeString(mLivePe1);
        parcel.writeString(mLivePe2);
        parcel.writeString(mLiveRe1);
        parcel.writeString(mLiveRe2);
        parcel.writeString(mLiveRole);
        parcel.writeString(mLiveStad);
        parcel.writeString(mLiveStu);
        parcel.writeString(mLiveTeam1);
        parcel.writeString(mLiveTeam2);
        parcel.writeString(mLiveTitle);
        parcel.writeString(mLiveTv);
        parcel.writeString(mRemainingSeconds);
        parcel.writeString(mRemainingTime);
        parcel.writeString(mTeamAHasPlayers);
        parcel.writeString(mTeamBHasPlayers);
        parcel.writeString(mTeamIdA);
        parcel.writeString(mTeamIdB);
        parcel.writeString(mTeamLogoA);
        parcel.writeString(mTeamLogoB);
    }

    @Override
    public int compareTo(Object o) {
        Match match = (Match) o;
        return this.getDepId().compareTo(match.getDepId());
    }
}
