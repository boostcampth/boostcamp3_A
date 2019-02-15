package com.aone.menurandomchoice.repository.model;

import com.google.gson.annotations.SerializedName;

public class KakaoAddress {

    @SerializedName("road_address")
    private Object roadAddress;

    @SerializedName("address_name")
    private String addressName;

    @SerializedName("address_type")
    private String addressType;

    private Object address;

    private double y;

    private double x;

    public Object getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(Object roadAddress) {
        this.roadAddress = roadAddress;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}