package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Goals implements Parcelable {
	private int away;
	private int home;

	protected Goals(Parcel in) {
		away = in.readInt();
		home = in.readInt();
	}

	public static final Creator<Goals> CREATOR = new Creator<Goals>() {
		@Override
		public Goals createFromParcel(Parcel in) {
			return new Goals(in);
		}

		@Override
		public Goals[] newArray(int size) {
			return new Goals[size];
		}
	};

	public void setAway(int away){
		this.away = away;
	}

	public int getAway(){
		return away;
	}

	public void setHome(int home){
		this.home = home;
	}

	public int getHome(){
		return home;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(away);
		parcel.writeInt(home);
	}
}
