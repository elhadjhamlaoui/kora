package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Duels implements Parcelable {
	private int total;
	private int won;

	protected Duels(Parcel in) {
		total = in.readInt();
		won = in.readInt();
	}

	public static final Creator<Duels> CREATOR = new Creator<Duels>() {
		@Override
		public Duels createFromParcel(Parcel in) {
			return new Duels(in);
		}

		@Override
		public Duels[] newArray(int size) {
			return new Duels[size];
		}
	};

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setWon(int won){
		this.won = won;
	}

	public int getWon(){
		return won;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(total);
		parcel.writeInt(won);
	}
}
