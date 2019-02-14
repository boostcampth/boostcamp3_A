package com.aone.menurandomchoice.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.aone.menurandomchoice.BR;

import java.io.Serializable;
import java.util.List;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class StoreDetail extends BaseObservable implements Parcelable {
    private String name;

    private String time;

    private String address;

    private String description;

    private double latitude;

    private double longitude;

    private String updateTime;

    private List<MenuDetail> menuList;

    public StoreDetail() {
    }

    protected StoreDetail(Parcel in) {
        name = in.readString();
        time = in.readString();
        address = in.readString();
        description = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        updateTime = in.readString();
        menuList = in.createTypedArrayList(MenuDetail.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(updateTime);
        dest.writeTypedList(menuList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoreDetail> CREATOR = new Creator<StoreDetail>() {
        @Override
        public StoreDetail createFromParcel(Parcel in) {
            return new StoreDetail(in);
        }

        @Override
        public StoreDetail[] newArray(int size) {
            return new StoreDetail[size];
        }
    };

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<MenuDetail> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuDetail> menuList) {
        this.menuList = menuList;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
