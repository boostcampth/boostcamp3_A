package com.aone.menurandomchoice.repository.local.db;

import com.aone.menurandomchoice.repository.model.StoreDetail;

import androidx.annotation.NonNull;

public interface SqliteDatabaseHelper {

    void addStoreDetail();

    StoreDetail getStoreDetail();

    void updateStoreDetail(@NonNull final StoreDetail storeDetail);

}
