package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Match implements Parcelable {
	private Fixture fixture;
	private Score score;
	private Teams teams;
	private League league;
	private Goals goals;

	protected Match(Parcel in) {
		fixture = in.readParcelable(Fixture.class.getClassLoader());
		league = in.readParcelable(League.class.getClassLoader());
		goals = in.readParcelable(Goals.class.getClassLoader());
		score = in.readParcelable(Score.class.getClassLoader());
		teams = in.readParcelable(Teams.class.getClassLoader());
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

	public void setFixture(Fixture fixture){
		this.fixture = fixture;
	}

	public Fixture getFixture(){
		return fixture;
	}

	public void setScore(Score score){
		this.score = score;
	}

	public Score getScore(){
		return score;
	}

	public void setTeams(Teams teams){
		this.teams = teams;
	}

	public Teams getTeams(){
		return teams;
	}

	public void setLeague(League league){
		this.league = league;
	}

	public League getLeague(){
		return league;
	}

	public void setGoals(Goals goals){
		this.goals = goals;
	}

	public Goals getGoals(){
		return goals;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeParcelable(fixture, i);
		parcel.writeParcelable(league, i);
		parcel.writeParcelable(goals, i);
		parcel.writeParcelable(score, i);
		parcel.writeParcelable(teams, i);
	}
}
