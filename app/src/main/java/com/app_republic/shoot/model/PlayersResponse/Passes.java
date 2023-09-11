package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Passes implements Parcelable {
	private int total;
	private int accuracy;
	private int key;

	protected Passes(Parcel in) {
		total = in.readInt();
		accuracy = in.readInt();
		key = in.readInt();
	}

	public static final Creator<Passes> CREATOR = new Creator<Passes>() {
		@Override
		public Passes createFromParcel(Parcel in) {
			return new Passes(in);
		}

		@Override
		public Passes[] newArray(int size) {
			return new Passes[size];
		}
	};

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setAccuracy(int accuracy){
		this.accuracy = accuracy;
	}

	public int getAccuracy(){
		return accuracy;
	}

	public void setKey(int key){
		this.key = key;
	}

	public int getKey(){
		return key;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(total);
		parcel.writeInt(accuracy);
		parcel.writeInt(key);
	}
}
