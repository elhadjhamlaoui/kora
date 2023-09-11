package com.app_republic.shoot.model.TeamInfos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Parameters implements Parcelable {
	private String id;

	protected Parameters(Parcel in) {
		id = in.readString();
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(id);
	}
}
