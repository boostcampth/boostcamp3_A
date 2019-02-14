package com.aone.menurandomchoice.repository.local;

import com.aone.menurandomchoice.repository.pojo.StoreDetail;

import androidx.annotation.NonNull;

public interface SqliteDatabaseHelper {

    void addStoreDetail();

    StoreDetail getStoreDetail();

    void updateStoreDetail(@NonNull final StoreDetail storeDetail);

}
