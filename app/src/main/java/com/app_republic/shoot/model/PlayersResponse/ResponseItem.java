package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class ResponseItem implements Parcelable {
	private Player player;
	private List<StatisticsItem> statistics;

	protected ResponseItem(Parcel in) {
		player = in.readParcelable(Player.class.getClassLoader());
		statistics = in.createTypedArrayList(StatisticsItem.CREATOR);
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

	public void setPlayer(Player player){
		this.player = player;
	}

	public Player getPlayer(){
		return player;
	}

	public void setStatistics(List<StatisticsItem> statistics){
		this.statistics = statistics;
	}

	public List<StatisticsItem> getStatistics(){
		return statistics;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeParcelable(player, i);
		parcel.writeTypedList(statistics);
	}
}