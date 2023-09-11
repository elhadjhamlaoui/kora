package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Shots implements Parcelable {
	private int total;
	private int on;

	protected Shots(Parcel in) {
		total = in.readInt();
		on = in.readInt();
	}

	public static final Creator<Shots> CREATOR = new Creator<Shots>() {
		@Override
		public Shots createFromParcel(Parcel in) {
			return new Shots(in);
		}

		@Override
		public Shots[] newArray(int size) {
			return new Shots[size];
		}
	};

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setOn(int on){
		this.on = on;
	}

	public int getOn(){
		return on;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(total);
		parcel.writeInt(on);
	}
}
