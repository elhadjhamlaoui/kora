package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Player implements Parcelable {
	private boolean injured;
	private String firstname;
	private String nationality;
	private String name;
	private Birth birth;
	private String weight;
	private String photo;
	private int id;
	private int age;
	private String lastname;
	private String height;

	protected Player(Parcel in) {
		injured = in.readByte() != 0;
		firstname = in.readString();
		nationality = in.readString();
		name = in.readString();
		weight = in.readString();
		photo = in.readString();
		id = in.readInt();
		age = in.readInt();
		lastname = in.readString();
		height = in.readString();
	}

	public static final Creator<Player> CREATOR = new Creator<Player>() {
		@Override
		public Player createFromParcel(Parcel in) {
			return new Player(in);
		}

		@Override
		public Player[] newArray(int size) {
			return new Player[size];
		}
	};

	public void setInjured(boolean injured){
		this.injured = injured;
	}

	public boolean isInjured(){
		return injured;
	}

	public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname(){
		return firstname;
	}

	public void setNationality(String nationality){
		this.nationality = nationality;
	}

	public String getNationality(){
		return nationality;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setBirth(Birth birth){
		this.birth = birth;
	}

	public Birth getBirth(){
		return birth;
	}

	public void setWeight(String weight){
		this.weight = weight;
	}

	public String getWeight(){
		return weight;
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

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getLastname(){
		return lastname;
	}

	public void setHeight(String height){
		this.height = height;
	}

	public String getHeight(){
		return height;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeByte((byte) (injured ? 1 : 0));
		parcel.writeString(firstname);
		parcel.writeString(nationality);
		parcel.writeString(name);
		parcel.writeString(weight);
		parcel.writeString(photo);
		parcel.writeInt(id);
		parcel.writeInt(age);
		parcel.writeString(lastname);
		parcel.writeString(height);
	}
}
