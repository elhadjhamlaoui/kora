package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Home implements Parcelable {
	private boolean winner;
	private String name;
	private String logo;
	private int id;

	protected Home(Parcel in) {
		winner = in.readByte() != 0;
		name = in.readString();
		logo = in.readString();
		id = in.readInt();
	}

	public static final Creator<Home> CREATOR = new Creator<Home>() {
		@Override
		public Home createFromParcel(Parcel in) {
			return new Home(in);
		}

		@Override
		public Home[] newArray(int size) {
			return new Home[size];
		}
	};

	public void setWinner(boolean winner){
		this.winner = winner;
	}

	public boolean isWinner(){
		return winner;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeByte((byte) (winner ? 1 : 0));
		parcel.writeString(name);
		parcel.writeString(logo);
		parcel.writeInt(id);
	}
}
