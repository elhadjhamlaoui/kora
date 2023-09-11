package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Substitutes implements Parcelable {
	private int bench;
	@SerializedName("in")
	private int inMember;
	private int out;

	protected Substitutes(Parcel in) {
		bench = in.readInt();
		inMember = in.readInt();
		out = in.readInt();
	}

	public static final Creator<Substitutes> CREATOR = new Creator<Substitutes>() {
		@Override
		public Substitutes createFromParcel(Parcel in) {
			return new Substitutes(in);
		}

		@Override
		public Substitutes[] newArray(int size) {
			return new Substitutes[size];
		}
	};

	public void setBench(int bench){
		this.bench = bench;
	}

	public int getBench(){
		return bench;
	}

	public void setInMember(int inMember){
		this.inMember = inMember;
	}

	public int getInMember(){
		return inMember;
	}

	public void setOut(int out){
		this.out = out;
	}

	public int getOut(){
		return out;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(bench);
		parcel.writeInt(inMember);
		parcel.writeInt(out);
	}
}
