package com.app_republic.shoot.model.LeagueStandingResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class All implements Parcelable {
	private int lose;
	private int draw;
	private int played;
	private int win;
	private Goals goals;

	protected All(Parcel in) {
		lose = in.readInt();
		draw = in.readInt();
		played = in.readInt();
		win = in.readInt();
		goals = in.readParcelable(Goals.class.getClassLoader());
	}

	public static final Creator<All> CREATOR = new Creator<All>() {
		@Override
		public All createFromParcel(Parcel in) {
			return new All(in);
		}

		@Override
		public All[] newArray(int size) {
			return new All[size];
		}
	};

	public void setLose(int lose){
		this.lose = lose;
	}

	public int getLose(){
		return lose;
	}

	public void setDraw(int draw){
		this.draw = draw;
	}

	public int getDraw(){
		return draw;
	}

	public void setPlayed(int played){
		this.played = played;
	}

	public int getPlayed(){
		return played;
	}

	public void setWin(int win){
		this.win = win;
	}

	public int getWin(){
		return win;
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
		parcel.writeInt(lose);
		parcel.writeInt(draw);
		parcel.writeInt(played);
		parcel.writeInt(win);
		parcel.writeParcelable(goals, i);
	}
}
