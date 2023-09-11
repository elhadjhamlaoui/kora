package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Fouls implements Parcelable {
	private int committed;
	private int drawn;

	protected Fouls(Parcel in) {
		committed = in.readInt();
		drawn = in.readInt();
	}

	public static final Creator<Fouls> CREATOR = new Creator<Fouls>() {
		@Override
		public Fouls createFromParcel(Parcel in) {
			return new Fouls(in);
		}

		@Override
		public Fouls[] newArray(int size) {
			return new Fouls[size];
		}
	};

	public void setCommitted(int committed){
		this.committed = committed;
	}

	public int getCommitted(){
		return committed;
	}

	public void setDrawn(int drawn){
		this.drawn = drawn;
	}

	public int getDrawn(){
		return drawn;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(committed);
		parcel.writeInt(drawn);
	}
}
