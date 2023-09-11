package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Penalty implements Parcelable {
	private int saved;
	private int scored;
	private int missed;
	private int won;
	private int commited;

	protected Penalty(Parcel in) {
		saved = in.readInt();
		scored = in.readInt();
		missed = in.readInt();
		won = in.readInt();
		commited = in.readInt();
	}

	public static final Creator<Penalty> CREATOR = new Creator<Penalty>() {
		@Override
		public Penalty createFromParcel(Parcel in) {
			return new Penalty(in);
		}

		@Override
		public Penalty[] newArray(int size) {
			return new Penalty[size];
		}
	};

	public void setSaved(int saved){
		this.saved = saved;
	}

	public int getSaved(){
		return saved;
	}

	public void setScored(int scored){
		this.scored = scored;
	}

	public int getScored(){
		return scored;
	}

	public void setMissed(int missed){
		this.missed = missed;
	}

	public int getMissed(){
		return missed;
	}

	public void setWon(int won){
		this.won = won;
	}

	public int getWon(){
		return won;
	}

	public void setCommited(int commited){
		this.commited = commited;
	}

	public int getCommited(){
		return commited;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(saved);
		parcel.writeInt(scored);
		parcel.writeInt(missed);
		parcel.writeInt(won);
		parcel.writeInt(commited);
	}
}
