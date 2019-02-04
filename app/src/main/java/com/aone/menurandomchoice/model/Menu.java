package com.aone.menurandomchoice.model;

public class Menu {
    private String name;
    private int price;
    private String photoUrl;
    private String description;
    private String category;
    private int sequence;

    public Menu(String name, int price, String photoUrl, String description, String category, int sequence) {
        this.name = name;
        this.price = price;
        this.photoUrl = photoUrl;
        this.description = description;
        this.category = category;
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
