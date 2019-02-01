package com.aone.menurandomchoice.data.network;

import androidx.annotation.NonNull;

public interface NetworkResponseListener<T> {

    void onError();
    void onResponseReceived(@NonNull final T response);

}
