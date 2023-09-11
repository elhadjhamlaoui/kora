package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Dribbles implements Parcelable {
	private int success;
	private int past;
	private int attempts;

	protected Dribbles(Parcel in) {
		success = in.readInt();
		past = in.readInt();
		attempts = in.readInt();
	}

	public static final Creator<Dribbles> CREATOR = new Creator<Dribbles>() {
		@Override
		public Dribbles createFromParcel(Parcel in) {
			return new Dribbles(in);
		}

		@Override
		public Dribbles[] newArray(int size) {
			return new Dribbles[size];
		}
	};

	public void setSuccess(int success){
		this.success = success;
	}

	public int getSuccess(){
		return success;
	}

	public void setPast(int past){
		this.past = past;
	}

	public int getPast(){
		return past;
	}

	public void setAttempts(int attempts){
		this.attempts = attempts;
	}

	public int getAttempts(){
		return attempts;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(success);
		parcel.writeInt(past);
		parcel.writeInt(attempts);
	}
}
