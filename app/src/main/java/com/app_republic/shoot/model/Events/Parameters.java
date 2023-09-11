package com.app_republic.shoot.model.Events;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Parameters implements Parcelable {
	private String fixture;

	protected Parameters(Parcel in) {
		fixture = in.readString();
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

	public String getFixture(){
		return fixture;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(fixture);
	}
}
