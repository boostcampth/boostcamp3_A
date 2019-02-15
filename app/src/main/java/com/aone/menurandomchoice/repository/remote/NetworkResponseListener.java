package com.aone.menurandomchoice.repository.remote;

import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;

import androidx.annotation.NonNull;

public interface NetworkResponseListener<T> {

    void onReceived(@NonNull T response);

    void onError(JMTErrorCode errorCode);

}
