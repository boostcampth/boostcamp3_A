package com.aone.menurandomchoice.repository.model;

import com.aone.menurandomchoice.BR;
import java.util.List;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class StoreDetail extends BaseObservable {
    private String name;

    private String time;

    private String address;

    private String description;

    private double latitude;

    private double longitude;

    private List<MenuDetail> menuDetailList;

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

    public List<MenuDetail> getMenuDetailList() {
        return menuDetailList;
    }

    public void setMenuDetailList(List<MenuDetail> menuDetailList) {
        this.menuDetailList = menuDetailList;
    }
}
