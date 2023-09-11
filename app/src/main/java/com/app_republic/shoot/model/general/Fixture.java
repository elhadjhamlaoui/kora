package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Fixture implements Parcelable {
	private String date;
	private Venue venue;
	private String timezone;
	private Periods periods;
	private int id;
	private String referee;
	private int timestamp;
	private Status status;


	protected Fixture(Parcel in) {
		date = in.readString();
		venue = in.readParcelable(Venue.class.getClassLoader());
		timezone = in.readString();
		periods = in.readParcelable(Periods.class.getClassLoader());
		id = in.readInt();
		referee = in.readString();
		timestamp = in.readInt();
		status = in.readParcelable(Status.class.getClassLoader());
	}

	public static final Creator<Fixture> CREATOR = new Creator<Fixture>() {
		@Override
		public Fixture createFromParcel(Parcel in) {
			return new Fixture(in);
		}

		@Override
		public Fixture[] newArray(int size) {
			return new Fixture[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setVenue(Venue venue){
		this.venue = venue;
	}

	public Venue getVenue(){
		return venue;
	}

	public void setTimezone(String timezone){
		this.timezone = timezone;
	}

	public String getTimezone(){
		return timezone;
	}

	public void setPeriods(Periods periods){
		this.periods = periods;
	}

	public Periods getPeriods(){
		return periods;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setReferee(String referee){
		this.referee = referee;
	}

	public String getReferee(){
		return referee;
	}

	public void setTimestamp(int timestamp){
		this.timestamp = timestamp;
	}

	public int getTimestamp(){
		return timestamp;
	}

	public void setStatus(Status status){
		this.status = status;
	}

	public Status getStatus(){
		return status;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(date);
		parcel.writeParcelable(venue, i);
		parcel.writeString(timezone);
		parcel.writeParcelable(periods, i);
		parcel.writeInt(id);
		parcel.writeString(referee);
		parcel.writeInt(timestamp);
		parcel.writeParcelable(status, i);
	}
}
