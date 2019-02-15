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

    public String getAddressName() {
        return addressName;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

}
