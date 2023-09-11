package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Fulltime implements Parcelable {
	private int away;
	private int home;

	protected Fulltime(Parcel in) {
		away = in.readInt();
		home = in.readInt();
	}

	public static final Creator<Fulltime> CREATOR = new Creator<Fulltime>() {
		@Override
		public Fulltime createFromParcel(Parcel in) {
			return new Fulltime(in);
		}

		@Override
		public Fulltime[] newArray(int size) {
			return new Fulltime[size];
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
