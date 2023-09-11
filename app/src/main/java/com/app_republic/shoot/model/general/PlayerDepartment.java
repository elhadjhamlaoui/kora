package com.app_republic.shoot.model.general;

import android.os.Parcel;
import android.os.Parcelable;


public class PlayerDepartment implements Parcelable {

    private String name, id, logo;
    private boolean isSelected;


    public PlayerDepartment(String name, String id, String logo, boolean isSelected) {
        this.name = name;
        this.id = id;
        this.logo = logo;
        this.isSelected = isSelected;
    }


    protected PlayerDepartment(Parcel in) {
        name = in.readString();
        id = in.readString();
        logo = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<PlayerDepartment> CREATOR = new Creator<PlayerDepartment>() {
        @Override
        public PlayerDepartment createFromParcel(Parcel in) {
            return new PlayerDepartment(in);
        }

        @Override
        public PlayerDepartment[] newArray(int size) {
            return new PlayerDepartment[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(logo);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
