package com.aone.menurandomchoice.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuSearchRequest implements Parcelable {

    private double latitude;
    private double longitude;
    private int radius;
    private String category;

    public MenuSearchRequest(double latitude, double longitude, int radius, String category) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.category = category;
    }

    protected MenuSearchRequest(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        radius = in.readInt();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(radius);
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuSearchRequest> CREATOR = new Creator<MenuSearchRequest>() {
        @Override
        public MenuSearchRequest createFromParcel(Parcel in) {
            return new MenuSearchRequest(in);
        }

        @Override
        public MenuSearchRequest[] newArray(int size) {
            return new MenuSearchRequest[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRadius() {
        return radius;
    }

    public String getCategory() {
        return category;
    }

}
