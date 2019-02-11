package com.aone.menurandomchoice.repository.local;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;

import java.util.List;

import androidx.annotation.NonNull;

public interface SqliteDatabaseHelper {

    void addStoreDetail();

    StoreDetail getStoreDetail();

    void updateStoreDetail(@NonNull final StoreDetail storeDetail);

}
