package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Status implements Parcelable {
	private int elapsed;
	@SerializedName("short")
	private String jsonMemberShort;
	@SerializedName("long")
	private String jsonMemberLong;

	protected Status(Parcel in) {
		elapsed = in.readInt();
		jsonMemberShort = in.readString();
		jsonMemberLong = in.readString();
	}

	public static final Creator<Status> CREATOR = new Creator<Status>() {
		@Override
		public Status createFromParcel(Parcel in) {
			return new Status(in);
		}

		@Override
		public Status[] newArray(int size) {
			return new Status[size];
		}
	};

	public void setElapsed(int elapsed){
		this.elapsed = elapsed;
	}

	public int getElapsed(){
		return elapsed;
	}

	public void setJsonMemberShort(String jsonMemberShort){
		this.jsonMemberShort = jsonMemberShort;
	}

	public String getJsonMemberShort(){
		return jsonMemberShort;
	}

	public void setJsonMemberLong(String jsonMemberLong){
		this.jsonMemberLong = jsonMemberLong;
	}

	public String getJsonMemberLong(){
		return jsonMemberLong;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(elapsed);
		parcel.writeString(jsonMemberShort);
		parcel.writeString(jsonMemberLong);
	}
}
