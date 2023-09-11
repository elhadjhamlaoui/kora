package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Extratime implements Parcelable {
	private int away;
	private int home;

	protected Extratime(Parcel in) {
		away = in.readInt();
		home = in.readInt();
	}

	public static final Creator<Extratime> CREATOR = new Creator<Extratime>() {
		@Override
		public Extratime createFromParcel(Parcel in) {
			return new Extratime(in);
		}

		@Override
		public Extratime[] newArray(int size) {
			return new Extratime[size];
		}
	};

	public int getAway() {
		return away;
	}

	public void setAway(int away) {
		this.away = away;
	}

	public int getHome() {
		return home;
	}

	public void setHome(int home) {
		this.home = home;
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
