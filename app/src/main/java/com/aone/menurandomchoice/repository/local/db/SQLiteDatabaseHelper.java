package com.aone.menurandomchoice.repository.local.db;

import com.aone.menurandomchoice.repository.model.StoreDetail;

import androidx.annotation.NonNull;

public interface SQLiteDatabaseHelper {

    void addDefaultStoreDetail();

    StoreDetail getStoreDetail();

    void updateStoreDetail(@NonNull final StoreDetail storeDetail);

    String getUpdateTimeFromSQLite();
}
