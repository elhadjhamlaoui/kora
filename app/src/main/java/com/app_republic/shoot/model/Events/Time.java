package com.app_republic.shoot.model.Events;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Time implements Parcelable {
	private int elapsed;
	private int extra;

	protected Time(Parcel in) {
		elapsed = in.readInt();
		extra = in.readInt();
	}

	public static final Creator<Time> CREATOR = new Creator<Time>() {
		@Override
		public Time createFromParcel(Parcel in) {
			return new Time(in);
		}

		@Override
		public Time[] newArray(int size) {
			return new Time[size];
		}
	};

	public int getElapsed(){
		return elapsed;
	}

	public int getExtra(){
		return extra;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(elapsed);
		parcel.writeInt(extra);
	}
}
