package com.aone.menurandomchoice.data.oauth;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface KakaoLoginHelper {

    void checkLoggedinAccount(OnKakaoLoginListener onKakaoLoginListener);

    void executeDeviceKakaoAccountLogin(OnKakaoLoginListener onKakaoLoginListener);

    void executeOtherKakaoAccountLogin(OnKakaoLoginListener onKakaoLoginListener);

    void executeKakaoAccountLogout(OnKakaoLogoutListener onKakaoLogoutListener);

    boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data);

}
