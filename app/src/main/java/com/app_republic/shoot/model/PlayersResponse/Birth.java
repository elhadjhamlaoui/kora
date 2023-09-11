package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Birth implements Parcelable {
	private String date;
	private String country;
	private String place;

	protected Birth(Parcel in) {
		date = in.readString();
		country = in.readString();
		place = in.readString();
	}

	public static final Creator<Birth> CREATOR = new Creator<Birth>() {
		@Override
		public Birth createFromParcel(Parcel in) {
			return new Birth(in);
		}

		@Override
		public Birth[] newArray(int size) {
			return new Birth[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setPlace(String place){
		this.place = place;
	}

	public String getPlace(){
		return place;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(date);
		parcel.writeString(country);
		parcel.writeString(place);
	}
}
