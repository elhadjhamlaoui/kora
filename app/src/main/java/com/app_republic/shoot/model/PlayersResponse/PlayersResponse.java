package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class PlayersResponse implements Parcelable {
	private List<ResponseItem> response;
	private String get;
	private Paging paging;
	private Parameters parameters;
	private int results;
	private List<Object> errors;

	protected PlayersResponse(Parcel in) {
		response = in.createTypedArrayList(ResponseItem.CREATOR);
		get = in.readString();
		paging = in.readParcelable(Paging.class.getClassLoader());
		parameters = in.readParcelable(Parameters.class.getClassLoader());
		results = in.readInt();
	}

	public static final Creator<PlayersResponse> CREATOR = new Creator<PlayersResponse>() {
		@Override
		public PlayersResponse createFromParcel(Parcel in) {
			return new PlayersResponse(in);
		}

		@Override
		public PlayersResponse[] newArray(int size) {
			return new PlayersResponse[size];
		}
	};

	public void setResponse(List<ResponseItem> response){
		this.response = response;
	}

	public List<ResponseItem> getResponse(){
		return response;
	}

	public void setGet(String get){
		this.get = get;
	}

	public String getGet(){
		return get;
	}

	public void setPaging(Paging paging){
		this.paging = paging;
	}

	public Paging getPaging(){
		return paging;
	}

	public void setParameters(Parameters parameters){
		this.parameters = parameters;
	}

	public Parameters getParameters(){
		return parameters;
	}

	public void setResults(int results){
		this.results = results;
	}

	public int getResults(){
		return results;
	}

	public void setErrors(List<Object> errors){
		this.errors = errors;
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