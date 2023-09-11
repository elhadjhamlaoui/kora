package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Goals implements Parcelable {
	private int conceded;
	private int total;
	private int saves;
	private int assists;

	protected Goals(Parcel in) {
		conceded = in.readInt();
		total = in.readInt();
		saves = in.readInt();
		assists = in.readInt();
	}

	public static final Creator<Goals> CREATOR = new Creator<Goals>() {
		@Override
		public Goals createFromParcel(Parcel in) {
			return new Goals(in);
		}

		@Override
		public Goals[] newArray(int size) {
			return new Goals[size];
		}
	};

	public void setConceded(int conceded){
		this.conceded = conceded;
	}

	public int getConceded(){
		return conceded;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setSaves(int saves){
		this.saves = saves;
	}

	public int getSaves(){
		return saves;
	}

	public void setAssists(int assists){
		this.assists = assists;
	}

	public int getAssists(){
		return assists;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(conceded);
		parcel.writeInt(total);
		parcel.writeInt(saves);
		parcel.writeInt(assists);
	}
}
