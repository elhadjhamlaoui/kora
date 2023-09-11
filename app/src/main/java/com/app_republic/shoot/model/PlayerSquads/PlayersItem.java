package com.app_republic.shoot.model.PlayerSquads;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PlayersItem implements Parcelable {
	private int number;
	private String name;
	private String photo;
	private int id;
	private String position;
	private int age;

	protected PlayersItem(Parcel in) {
		number = in.readInt();
		name = in.readString();
		photo = in.readString();
		id = in.readInt();
		position = in.readString();
		age = in.readInt();
	}

	public static final Creator<PlayersItem> CREATOR = new Creator<PlayersItem>() {
		@Override
		public PlayersItem createFromParcel(Parcel in) {
			return new PlayersItem(in);
		}

		@Override
		public PlayersItem[] newArray(int size) {
			return new PlayersItem[size];
		}
	};

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPosition(String position){
		this.position = position;
	}

	public String getPosition(){
		return position;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(number);
		parcel.writeString(name);
		parcel.writeString(photo);
		parcel.writeInt(id);
		parcel.writeString(position);
		parcel.writeInt(age);
	}
}
