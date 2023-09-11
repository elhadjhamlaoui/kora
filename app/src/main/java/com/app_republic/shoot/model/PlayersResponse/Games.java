package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Games implements Parcelable {
	private int number;
	private int minutes;
	private String rating;
	private int appearences;
	private String position;
	private boolean captain;
	private int lineups;

	protected Games(Parcel in) {
		number = in.readInt();
		minutes = in.readInt();
		rating = in.readString();
		appearences = in.readInt();
		position = in.readString();
		captain = in.readByte() != 0;
		lineups = in.readInt();
	}

	public static final Creator<Games> CREATOR = new Creator<Games>() {
		@Override
		public Games createFromParcel(Parcel in) {
			return new Games(in);
		}

		@Override
		public Games[] newArray(int size) {
			return new Games[size];
		}
	};

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setMinutes(int minutes){
		this.minutes = minutes;
	}

	public int getMinutes(){
		return minutes;
	}

	public void setRating(String rating){
		this.rating = rating;
	}

	public String getRating(){
		return rating;
	}

	public void setAppearences(int appearences){
		this.appearences = appearences;
	}

	public int getAppearences(){
		return appearences;
	}

	public void setPosition(String position){
		this.position = position;
	}

	public String getPosition(){
		return position;
	}

	public void setCaptain(boolean captain){
		this.captain = captain;
	}

	public boolean isCaptain(){
		return captain;
	}

	public void setLineups(int lineups){
		this.lineups = lineups;
	}

	public int getLineups(){
		return lineups;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(number);
		parcel.writeInt(minutes);
		parcel.writeString(rating);
		parcel.writeInt(appearences);
		parcel.writeString(position);
		parcel.writeByte((byte) (captain ? 1 : 0));
		parcel.writeInt(lineups);
	}
}
