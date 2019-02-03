package com.aone.menurandomchoice.repository.oauth;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface KakaoLoginHelper {

    void checkLoggedinAccount(@NonNull OnKakaoLoginListener onKakaoLoginListener);

    void executeDeviceKakaoAccountLogin(@NonNull OnKakaoLoginListener onKakaoLoginListener);

    void executeOtherKakaoAccountLogin(@NonNull OnKakaoLoginListener onKakaoLoginListener);

    void executeKakaoAccountLogout(@NonNull OnKakaoLogoutListener onKakaoLogoutListener);

    boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data);

}
