package com.app_republic.shoot.model.LeagueStandingResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ResponseItem implements Parcelable {
	private League league;

	protected ResponseItem(Parcel in) {
	}

	public static final Creator<ResponseItem> CREATOR = new Creator<ResponseItem>() {
		@Override
		public ResponseItem createFromParcel(Parcel in) {
			return new ResponseItem(in);
		}

		@Override
		public ResponseItem[] newArray(int size) {
			return new ResponseItem[size];
		}
	};

	public void setLeague(League league){
		this.league = league;
	}

	public League getLeague(){
		return league;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
	}
}
