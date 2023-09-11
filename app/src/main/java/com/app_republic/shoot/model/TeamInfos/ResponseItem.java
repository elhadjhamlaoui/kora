package com.app_republic.shoot.model.TeamInfos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ResponseItem implements Parcelable {
	private Venue venue;
	private TeamInfos team;

	protected ResponseItem(Parcel in) {
		venue = in.readParcelable(Venue.class.getClassLoader());
		team = in.readParcelable(TeamInfos.class.getClassLoader());
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

	public void setVenue(Venue venue){
		this.venue = venue;
	}

	public Venue getVenue(){
		return venue;
	}

	public void setTeam(TeamInfos team){
		this.team = team;
	}

	public TeamInfos getTeam(){
		return team;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeParcelable(venue, i);
		parcel.writeParcelable(team, i);
	}
}
