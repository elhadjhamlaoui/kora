package com.app_republic.shoot.model.TeamInfos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Paging implements Parcelable {
	private int current;
	private int total;

	public void setCurrent(int current){
		this.current = current;
	}

	public int getCurrent(){
		return current;
	}

	public void setTotal(int total){
		this.total = total;
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

	}
}
