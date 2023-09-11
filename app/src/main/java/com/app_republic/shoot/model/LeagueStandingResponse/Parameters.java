package com.app_republic.shoot.model.LeagueStandingResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Parameters implements Parcelable {
	private String league;
	private String season;

	protected Parameters(Parcel in) {
		league = in.readString();
		season = in.readString();
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

	public void setLeague(String league){
		this.league = league;
	}

	public String getLeague(){
		return league;
	}

	public void setSeason(String season){
		this.season = season;
	}

	public String getSeason(){
		return season;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(league);
		parcel.writeString(season);
	}
}
