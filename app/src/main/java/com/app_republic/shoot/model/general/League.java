package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class League implements Parcelable {
	private String country;
	private String flag;
	private String round;
	private String name;
	private String logo;
	private int season;
	private int id;

	protected League(Parcel in) {
		country = in.readString();
		flag = in.readString();
		round = in.readString();
		name = in.readString();
		logo = in.readString();
		season = in.readInt();
		id = in.readInt();
	}

	public static final Creator<League> CREATOR = new Creator<League>() {
		@Override
		public League createFromParcel(Parcel in) {
			return new League(in);
		}

		@Override
		public League[] newArray(int size) {
			return new League[size];
		}
	};

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setFlag(String flag){
		this.flag = flag;
	}

	public String getFlag(){
		return flag;
	}

	public void setRound(String round){
		this.round = round;
	}

	public String getRound(){
		return round;
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

	public void setSeason(int season){
		this.season = season;
	}

	public int getSeason(){
		return season;
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
		parcel.writeString(country);
		parcel.writeString(flag);
		parcel.writeString(round);
		parcel.writeString(name);
		parcel.writeString(logo);
		parcel.writeInt(season);
		parcel.writeInt(id);
	}
}
