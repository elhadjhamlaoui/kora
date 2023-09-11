package com.app_republic.shoot.model.Events;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Event implements Parcelable {
	private Object comments;
	private Assist assist;
	private Time time;
	private Team team;
	private String detail;
	private String type;
	private Player player;

	protected Event(Parcel in) {
		assist = in.readParcelable(Assist.class.getClassLoader());
		time = in.readParcelable(Time.class.getClassLoader());
		team = in.readParcelable(Team.class.getClassLoader());
		detail = in.readString();
		type = in.readString();
		player = in.readParcelable(Player.class.getClassLoader());
	}

	public static final Creator<Event> CREATOR = new Creator<Event>() {
		@Override
		public Event createFromParcel(Parcel in) {
			return new Event(in);
		}

		@Override
		public Event[] newArray(int size) {
			return new Event[size];
		}
	};

	public Object getComments(){
		return comments;
	}

	public Assist getAssist(){
		return assist;
	}

	public Time getTime(){
		return time;
	}

	public Team getTeam(){
		return team;
	}

	public String getDetail(){
		return detail;
	}

	public String getType(){
		return type;
	}

	public Player getPlayer(){
		return player;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeParcelable(assist, i);
		parcel.writeParcelable(time, i);
		parcel.writeParcelable(team, i);
		parcel.writeString(detail);
		parcel.writeString(type);
		parcel.writeParcelable(player, i);
	}
}
