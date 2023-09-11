package com.app_republic.shoot.model.PlayerSquads;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class ResponseItem implements Parcelable {
	private List<PlayersItem> players;
	private Team team;

	protected ResponseItem(Parcel in) {
		players = in.createTypedArrayList(PlayersItem.CREATOR);
		team = in.readParcelable(Team.class.getClassLoader());
	}

	public static final Creator<ResponseItem> CREATOR = new Creator<ResponseItem>() {
		@Override
		public ResponseItem createFromParcel(Parcel in) {
			return new ResponseItem(in);
		}

		@Override
		public ResponseItem[] newArray(int size) {
			return new ResponseItem[size];
		}
	};

	public void setPlayers(List<PlayersItem> players){
		this.players = players;
	}

	public List<PlayersItem> getPlayers(){
		return players;
	}

	public void setTeam(Team team){
		this.team = team;
	}

	public Team getTeam(){
		return team;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeTypedList(players);
		parcel.writeParcelable(team, i);
	}
}