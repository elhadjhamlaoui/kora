package com.app_republic.shoot.model.Events;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Player implements Parcelable {
	private String name;
	private int id;

	protected Player(Parcel in) {
		name = in.readString();
		id = in.readInt();
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

	public String getName(){
		return name;
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
		parcel.writeInt(id);
	}
}
