package com.aone.menurandomchoice.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.aone.menurandomchoice.BR;

import java.io.Serializable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class MenuDetail extends BaseObservable implements Parcelable {

    private String name;

    private int price;

    private String photoUrl;

    private String description;

    private String category;

    private int sequence;

    public MenuDetail() {
    }

    protected MenuDetail(Parcel in) {
        name = in.readString();
        price = in.readInt();
        photoUrl = in.readString();
        description = in.readString();
        category = in.readString();
        sequence = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(photoUrl);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeInt(sequence);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuDetail> CREATOR = new Creator<MenuDetail>() {
        @Override
        public MenuDetail createFromParcel(Parcel in) {
            return new MenuDetail(in);
        }

        @Override
        public MenuDetail[] newArray(int size) {
            return new MenuDetail[size];
        }
    };

    @Bindable
    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getPrice() { return price; }

    public void setPrice(int price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getPhotoUrl() { return photoUrl; }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        notifyPropertyChanged(BR.photoUrl);
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
