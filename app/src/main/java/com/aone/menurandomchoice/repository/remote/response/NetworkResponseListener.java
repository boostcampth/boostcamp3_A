package com.aone.menurandomchoice.repository.remote.response;

import androidx.annotation.NonNull;

public interface NetworkResponseListener<T> {

    void onReceived(@NonNull T response);

    void onError();

}
