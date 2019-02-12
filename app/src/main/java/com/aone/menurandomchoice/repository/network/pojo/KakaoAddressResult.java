package com.aone.menurandomchoice.repository.network.pojo;

public class KakaoAddressResult {
    private Object roadAddress;
    private String addressName;
    private Object address;
    private double y;
    private double x;
    private String addressType;

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
