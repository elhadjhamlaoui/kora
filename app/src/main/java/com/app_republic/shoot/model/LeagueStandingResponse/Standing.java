package com.app_republic.shoot.model.LeagueStandingResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Standing implements Parcelable {
	private All all;
	private Away away;
	private String form;
	private int rank;
	private String description;
	private String update;
	private Team team;
	private int goalsDiff;
	private int points;
	private String group;
	private String status;
	private Home home;

	protected Standing(Parcel in) {
		all = in.readParcelable(All.class.getClassLoader());
		away = in.readParcelable(Away.class.getClassLoader());
		form = in.readString();
		rank = in.readInt();
		description = in.readString();
		update = in.readString();
		team = in.readParcelable(Team.class.getClassLoader());
		goalsDiff = in.readInt();
		points = in.readInt();
		group = in.readString();
		status = in.readString();
		home = in.readParcelable(Home.class.getClassLoader());
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

	public void setAll(All all){
		this.all = all;
	}

	public All getAll(){
		return all;
	}

	public void setAway(Away away){
		this.away = away;
	}

	public Away getAway(){
		return away;
	}

	public void setForm(String form){
		this.form = form;
	}

	public String getForm(){
		return form;
	}

	public void setRank(int rank){
		this.rank = rank;
	}

	public int getRank(){
		return rank;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setUpdate(String update){
		this.update = update;
	}

	public String getUpdate(){
		return update;
	}

	public void setTeam(Team team){
		this.team = team;
	}

	public Team getTeam(){
		return team;
	}

	public void setGoalsDiff(int goalsDiff){
		this.goalsDiff = goalsDiff;
	}

	public int getGoalsDiff(){
		return goalsDiff;
	}

	public void setPoints(int points){
		this.points = points;
	}

	public int getPoints(){
		return points;
	}

	public void setGroup(String group){
		this.group = group;
	}

	public String getGroup(){
		return group;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setHome(Home home){
		this.home = home;
	}

	public Home getHome(){
		return home;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeParcelable(all, i);
		parcel.writeParcelable(away, i);
		parcel.writeString(form);
		parcel.writeInt(rank);
		parcel.writeString(description);
		parcel.writeString(update);
		parcel.writeParcelable(team, i);
		parcel.writeInt(goalsDiff);
		parcel.writeInt(points);
		parcel.writeString(group);
		parcel.writeString(status);
		parcel.writeParcelable(home, i);
	}
}
