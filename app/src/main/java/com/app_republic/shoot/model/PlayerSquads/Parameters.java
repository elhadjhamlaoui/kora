package com.app_republic.shoot.model.PlayerSquads;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Parameters implements Parcelable {
	private String team;

	protected Parameters(Parcel in) {
		team = in.readString();
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

	public void setTeam(String team){
		this.team = team;
	}

	public String getTeam(){
		return team;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(team);
	}
}
