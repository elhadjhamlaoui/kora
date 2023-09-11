package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Venue implements Parcelable {
	private String city;
	private String name;
	private int id;

	protected Venue(Parcel in) {
		city = in.readString();
		name = in.readString();
		id = in.readInt();
	}

	public static final Creator<Venue> CREATOR = new Creator<Venue>() {
		@Override
		public Venue createFromParcel(Parcel in) {
			return new Venue(in);
		}

		@Override
		public Venue[] newArray(int size) {
			return new Venue[size];
		}
	};

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
		parcel.writeString(city);
		parcel.writeString(name);
		parcel.writeInt(id);
	}
}
