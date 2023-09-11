package com.app_republic.shoot.model.Events;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Paging implements Parcelable {
	private int current;
	private int total;

	protected Paging(Parcel in) {
		current = in.readInt();
		total = in.readInt();
	}

	public static final Creator<Paging> CREATOR = new Creator<Paging>() {
		@Override
		public Paging createFromParcel(Parcel in) {
			return new Paging(in);
		}

		@Override
		public Paging[] newArray(int size) {
			return new Paging[size];
		}
	};

	public int getCurrent(){
		return current;
	}

	public int getTotal(){
		return total;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(current);
		parcel.writeInt(total);
	}
}
