package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cards implements Parcelable {
	private int red;
	private int yellowred;
	private int yellow;

	protected Cards(Parcel in) {
		red = in.readInt();
		yellowred = in.readInt();
		yellow = in.readInt();
	}

	public static final Creator<Cards> CREATOR = new Creator<Cards>() {
		@Override
		public Cards createFromParcel(Parcel in) {
			return new Cards(in);
		}

		@Override
		public Cards[] newArray(int size) {
			return new Cards[size];
		}
	};

	public void setRed(int red){
		this.red = red;
	}

	public int getRed(){
		return red;
	}

	public void setYellowred(int yellowred){
		this.yellowred = yellowred;
	}

	public int getYellowred(){
		return yellowred;
	}

	public void setYellow(int yellow){
		this.yellow = yellow;
	}

	public int getYellow(){
		return yellow;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(red);
		parcel.writeInt(yellowred);
		parcel.writeInt(yellow);
	}
}
