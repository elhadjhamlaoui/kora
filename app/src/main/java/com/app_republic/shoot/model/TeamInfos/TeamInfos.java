package com.app_republic.shoot.model.TeamInfos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TeamInfos implements Parcelable {
	private String country;
	private String code;
	private String name;
	private int founded;
	private boolean national;
	private String logo;
	private int id;

	protected TeamInfos(Parcel in) {
		country = in.readString();
		code = in.readString();
		name = in.readString();
		founded = in.readInt();
		national = in.readByte() != 0;
		logo = in.readString();
		id = in.readInt();
	}

	public static final Creator<TeamInfos> CREATOR = new Creator<TeamInfos>() {
		@Override
		public TeamInfos createFromParcel(Parcel in) {
			return new TeamInfos(in);
		}

		@Override
		public TeamInfos[] newArray(int size) {
			return new TeamInfos[size];
		}
	};

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFounded(int founded){
		this.founded = founded;
	}

	public int getFounded(){
		return founded;
	}

	public void setNational(boolean national){
		this.national = national;
	}

	public boolean isNational(){
		return national;
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
		parcel.writeString(country);
		parcel.writeString(code);
		parcel.writeString(name);
		parcel.writeInt(founded);
		parcel.writeByte((byte) (national ? 1 : 0));
		parcel.writeString(logo);
		parcel.writeInt(id);
	}
}
