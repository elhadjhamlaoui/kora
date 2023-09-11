package com.app_republic.shoot.model.TeamInfos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Venue implements Parcelable {
	private String image;
	private String address;
	private String surface;
	private String city;
	private String name;
	private int id;
	private int capacity;

	protected Venue(Parcel in) {
		image = in.readString();
		address = in.readString();
		surface = in.readString();
		city = in.readString();
		name = in.readString();
		id = in.readInt();
		capacity = in.readInt();
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

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setSurface(String surface){
		this.surface = surface;
	}

	public String getSurface(){
		return surface;
	}

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

	public void setCapacity(int capacity){
		this.capacity = capacity;
	}

	public int getCapacity(){
		return capacity;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(image);
		parcel.writeString(address);
		parcel.writeString(surface);
		parcel.writeString(city);
		parcel.writeString(name);
		parcel.writeInt(id);
		parcel.writeInt(capacity);
	}
}
