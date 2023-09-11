package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Parameters implements Parcelable {
	private String date;

	protected Parameters(Parcel in) {
		date = in.readString();
	}

	public static final Creator<Parameters> CREATOR = new Creator<Parameters>() {
		@Override
		public Parameters createFromParcel(Parcel in) {
			return new Parameters(in);
		}

		@Override
		public Parameters[] newArray(int size) {
			return new Parameters[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(date);
	}
}
