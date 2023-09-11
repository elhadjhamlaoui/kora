package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Teams implements Parcelable {
	private Away away;
	private Home home;

	protected Teams(Parcel in) {
		away = in.readParcelable(Away.class.getClassLoader());
		home = in.readParcelable(Home.class.getClassLoader());
	}

	public static final Creator<Teams> CREATOR = new Creator<Teams>() {
		@Override
		public Teams createFromParcel(Parcel in) {
			return new Teams(in);
		}

		@Override
		public Teams[] newArray(int size) {
			return new Teams[size];
		}
	};

	public void setAway(Away away){
		this.away = away;
	}

	public Away getAway(){
		return away;
	}

	public void setHome(Home home){
		this.home = home;
	}

	public Home getHome(){
		return home;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeParcelable(away, i);
		parcel.writeParcelable(home, i);
	}
}
