package com.aone.menurandomchoice.data.oauth;

public interface OnKakaoLoginListener {

    void onLoginSuccess(long userId);

    void onFail(KakaoLoginError kakaoLoginError);

}

