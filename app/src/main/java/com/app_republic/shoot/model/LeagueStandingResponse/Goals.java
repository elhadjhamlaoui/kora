package com.app_republic.shoot.model.LeagueStandingResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Goals implements Parcelable {
	private int against;
	@SerializedName("for")
	private int jsonMemberFor;

	protected Goals(Parcel in) {
		against = in.readInt();
		jsonMemberFor = in.readInt();
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

	public void setAgainst(int against){
		this.against = against;
	}

	public int getAgainst(){
		return against;
	}

	public void setJsonMemberFor(int jsonMemberFor){
		this.jsonMemberFor = jsonMemberFor;
	}

	public int getJsonMemberFor(){
		return jsonMemberFor;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeInt(against);
		parcel.writeInt(jsonMemberFor);
	}
}
