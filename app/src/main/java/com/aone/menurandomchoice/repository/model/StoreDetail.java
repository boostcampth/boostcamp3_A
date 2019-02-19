package com.aone.menurandomchoice.repository.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.aone.menurandomchoice.BR;
import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class StoreDetail extends BaseObservable implements Parcelable {

    private int storeIdx;
    private String name;
    private String opentime;
    private String closetime;
    private String address;
    private String description;
    private double latitude;
    private double longitude;
    private String updateTime;
    private List<MenuDetail> menuList;

    public StoreDetail() {
    }

    protected StoreDetail(Parcel in) {
        storeIdx = in.readInt();
        name = in.readString();
        opentime = in.readString();
        closetime = in.readString();
        address = in.readString();
        description = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        updateTime = in.readString();
        menuList = in.createTypedArrayList(MenuDetail.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(storeIdx);
        dest.writeString(name);
        dest.writeString(opentime);
        dest.writeString(closetime);
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

    @NonNull
    @Bindable
    public String getName() {
        if(name == null) {
            name = "";
        }

        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @NonNull
    @Bindable
    public String getOpentime() {
        if(TextUtils.isEmpty(opentime)) {
            opentime = GlobalApplication
                    .getGlobalApplicationContext()
                    .getString(R.string.activity_store_edit_default_starttime);
        }

        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
        notifyPropertyChanged(BR.opentime);
    }

    @NonNull
    @Bindable
    public String getClosetime() {
        if(TextUtils.isEmpty(closetime)) {
            closetime = GlobalApplication
                    .getGlobalApplicationContext()
                    .getString(R.string.activity_store_edit_default_endtime);
        }

        return closetime;
    }

    public void setClosetime(String closetime) {
        this.closetime = closetime;
        notifyPropertyChanged(BR.closetime);
    }

    @NonNull
    @Bindable
    public String getAddress() {
        if(address == null) {
            address = "";
        }

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getDescription() {
        if(description == null) {
            description = "";
        }

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public int getStoreIdx() {
        return storeIdx;
    }

    public void setStoreIdx(int storeIdx) {
        this.storeIdx = storeIdx;
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
