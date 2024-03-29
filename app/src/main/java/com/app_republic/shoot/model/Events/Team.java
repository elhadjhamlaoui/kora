package com.app_republic.shoot.model.Events;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Team implements Parcelable {
	private String name;
	private String logo;
	private int id;

	protected Team(Parcel in) {
		name = in.readString();
		logo = in.readString();
		id = in.readInt();
	}

	public static final Creator<Team> CREATOR = new Creator<Team>() {
		@Override
		public Team createFromParcel(Parcel in) {
			return new Team(in);
		}

		@Override
		public Team[] newArray(int size) {
			return new Team[size];
		}
	};

	public String getName(){
		return name;
	}

	public String getLogo(){
		return logo;
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
		parcel.writeString(name);
		parcel.writeString(logo);
		parcel.writeInt(id);
	}
}
