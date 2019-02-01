package com.aone.menurandomchoice.repository.server;

import androidx.annotation.NonNull;

public interface ServerDataHelper {

    void requestSignedUpCheck(long userId, @NonNull OnSignedUpCheckListener onSignedUpCheckListener);

    void requestSignUp(long userId, @NonNull String accessKey, @NonNull OnSignUpRequestListener onSignUpRequestListener);

}
