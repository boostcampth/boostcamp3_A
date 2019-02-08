package com.aone.menurandomchoice.repository.server;

import com.aone.menurandomchoice.repository.model.StoreDetail;

public interface OnStoreDetailRequestListener {

    void onStoreDetailLoaded(StoreDetail storeDetail);

    void onServerError();
}
