package com.app_republic.shoot.model.Events;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Assist implements Parcelable {
	private String name;
	private int id;

	protected Assist(Parcel in) {
		name = in.readString();
		id = in.readInt();
	}

	public static final Creator<Assist> CREATOR = new Creator<Assist>() {
		@Override
		public Assist createFromParcel(Parcel in) {
			return new Assist(in);
		}

		@Override
		public Assist[] newArray(int size) {
			return new Assist[size];
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
