package com.aone.menurandomchoice.repository.server;

import androidx.annotation.NonNull;

public interface OnStoreUpdatedCheckListener {

    void onAlreadyStoreUpdated();

    void onNotUpdated(@NonNull OnStoreDetailRequestListener onStoreDetailRequestListener);
}
