package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Score  implements Parcelable{
	private Halftime halftime;
	private Penalty penalty;
	private Fulltime fulltime;
	private Extratime extratime;

	protected Score(Parcel in) {
		halftime = in.readParcelable(Halftime.class.getClassLoader());
		penalty = in.readParcelable(Penalty.class.getClassLoader());
		fulltime = in.readParcelable(Fulltime.class.getClassLoader());
		extratime = in.readParcelable(Extratime.class.getClassLoader());
	}

	public static final Creator<Score> CREATOR = new Creator<Score>() {
		@Override
		public Score createFromParcel(Parcel in) {
			return new Score(in);
		}

		@Override
		public Score[] newArray(int size) {
			return new Score[size];
		}
	};

	public void setHalftime(Halftime halftime){
		this.halftime = halftime;
	}

	public Halftime getHalftime(){
		return halftime;
	}

	public void setPenalty(Penalty penalty){
		this.penalty = penalty;
	}

	public Penalty getPenalty(){
		return penalty;
	}

	public void setFulltime(Fulltime fulltime){
		this.fulltime = fulltime;
	}

	public Fulltime getFulltime(){
		return fulltime;
	}

	public void setExtratime(Extratime extratime){
		this.extratime = extratime;
	}

	public Extratime getExtratime(){
		return extratime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeParcelable(halftime, i);
		parcel.writeParcelable(penalty, i);
		parcel.writeParcelable(fulltime, i);
		parcel.writeParcelable(extratime, i);
	}
}
