package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Periods implements Parcelable {
	private int first;
	private int second;

	protected Periods(Parcel in) {
		first = in.readInt();
		second = in.readInt();
	}

	public static final Creator<Periods> CREATOR = new Creator<Periods>() {
		@Override
		public Periods createFromParcel(Parcel in) {
			return new Periods(in);
		}

		@Override
		public Periods[] newArray(int size) {
			return new Periods[size];
		}
	};

	public void setFirst(int first){
		this.first = first;
	}

	public int getFirst(){
		return first;
	}

	public void setSecond(int second){
		this.second = second;
	}

	public int getSecond(){
		return second;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(first);
		parcel.writeInt(second);
	}
}
