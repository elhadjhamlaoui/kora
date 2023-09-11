package com.app_republic.shoot.model.Events;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class EventsResponse implements Parcelable {
	private List<Event> response;
	private String get;
	private Paging paging;
	private Parameters parameters;
	private int results;
	private List<Object> errors;

	protected EventsResponse(Parcel in) {
		response = in.createTypedArrayList(Event.CREATOR);
		get = in.readString();
		paging = in.readParcelable(Paging.class.getClassLoader());
		parameters = in.readParcelable(Parameters.class.getClassLoader());
		results = in.readInt();
	}

	public static final Creator<EventsResponse> CREATOR = new Creator<EventsResponse>() {
		@Override
		public EventsResponse createFromParcel(Parcel in) {
			return new EventsResponse(in);
		}

		@Override
		public EventsResponse[] newArray(int size) {
			return new EventsResponse[size];
		}
	};

	public List<Event> getResponse(){
		return response;
	}

	public String getGet(){
		return get;
	}

	public Paging getPaging(){
		return paging;
	}

	public Parameters getParameters(){
		return parameters;
	}

	public int getResults(){
		return results;
	}

	public List<Object> getErrors(){
		return errors;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeTypedList(response);
		parcel.writeString(get);
		parcel.writeParcelable(paging, i);
		parcel.writeParcelable(parameters, i);
		parcel.writeInt(results);
	}
}