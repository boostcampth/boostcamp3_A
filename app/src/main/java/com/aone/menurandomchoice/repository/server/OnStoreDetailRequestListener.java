package com.aone.menurandomchoice.repository.server;

import com.aone.menurandomchoice.repository.model.StoreDetail;

import androidx.annotation.NonNull;

public interface OnStoreDetailRequestListener {

    void onStoreDetailLoaded(@NonNull StoreDetail storeDetail);

    void onServerError();
}
