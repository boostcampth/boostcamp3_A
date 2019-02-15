package com.aone.menurandomchoice.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAccessInfo implements Parcelable {

    private int accessStoreIndex;
    private boolean isOwner;

    public UserAccessInfo(int accessStoreIndex, boolean isOwner) {
        this.accessStoreIndex = accessStoreIndex;
        this.isOwner = isOwner;
    }

    protected UserAccessInfo(Parcel in) {
        accessStoreIndex = in.readInt();
        isOwner = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(accessStoreIndex);
        dest.writeByte((byte) (isOwner ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserAccessInfo> CREATOR = new Creator<UserAccessInfo>() {
        @Override
        public UserAccessInfo createFromParcel(Parcel in) {
            return new UserAccessInfo(in);
        }

        @Override
        public UserAccessInfo[] newArray(int size) {
            return new UserAccessInfo[size];
        }
    };

    public int getAccessStoreIndex() {
        return accessStoreIndex;
    }

    public boolean isOwner() {
        return isOwner;
    }
}
