package com.aone.menurandomchoice.data.oauth;

public interface OnKakaoLoginListener {

    void onKakaoLoginSuccess(long userId);

    void onFail(KakaoLoginError kakaoLoginError);

}

