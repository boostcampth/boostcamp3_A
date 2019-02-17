package com.aone.menurandomchoice.repository.model;

public class MenuLocation {

    private double latitude;
    private double longitude;
    private String category;

    public MenuLocation(double latitude, double longitude, String category) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCategory() {
        return category;
    }

}
