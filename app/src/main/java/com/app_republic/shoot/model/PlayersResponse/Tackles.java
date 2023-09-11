package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Tackles implements Parcelable {
	private int total;
	private int blocks;
	private int interceptions;

	protected Tackles(Parcel in) {
		total = in.readInt();
		blocks = in.readInt();
		interceptions = in.readInt();
	}

	public static final Creator<Tackles> CREATOR = new Creator<Tackles>() {
		@Override
		public Tackles createFromParcel(Parcel in) {
			return new Tackles(in);
		}

		@Override
		public Tackles[] newArray(int size) {
			return new Tackles[size];
		}
	};

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setBlocks(int blocks){
		this.blocks = blocks;
	}

	public int getBlocks(){
		return blocks;
	}

	public void setInterceptions(int interceptions){
		this.interceptions = interceptions;
	}

	public int getInterceptions(){
		return interceptions;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(total);
		parcel.writeInt(blocks);
		parcel.writeInt(interceptions);
	}
}
