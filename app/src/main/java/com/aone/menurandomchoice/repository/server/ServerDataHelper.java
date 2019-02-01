package com.aone.menurandomchoice.repository.server;

import androidx.annotation.NonNull;

public interface ServerDataHelper {

    void requestSignUpCheck(long userId, @NonNull OnSignedUpCheckListener onSignedUpCheckListener);

    void requestSignUp(long userId, String accessKey, OnSignUpRequestListener onSignUpRequestListener);

}
