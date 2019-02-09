package com.aone.menurandomchoice.repository.network;

import androidx.annotation.NonNull;

public interface NetworkResponseListener<T> {

    void onError();

    void onReceived(@NonNull final T response);

}
