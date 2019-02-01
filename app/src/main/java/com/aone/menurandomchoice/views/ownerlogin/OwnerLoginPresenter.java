package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Intent;

import com.aone.menurandomchoice.data.oauth.KakaoLoginError;
import com.aone.menurandomchoice.data.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.views.base.BasePresenter;


import androidx.annotation.Nullable;

public class OwnerLoginPresenter extends BasePresenter<OwnerLoginContract.View>
        implements OwnerLoginContract.Presenter {

    @Override
    public void handlingLoggedInAccount() {
        getRepository().checkLoggedinAccount(onKakaoLoginListener);
    }

    @Override
    public void handlingDeviceKaKaoAccountLogin() {
        getRepository().executeDeviceKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public void handlingOtherKaKaoAccountLogin() {
        getRepository().executeOtherKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data) {
        return getRepository().isNeedKakaoSDKLoginScreen(requestCode, resultCode, data);
    }

    private OnKakaoLoginListener onKakaoLoginListener = new OnKakaoLoginListener() {

        @Override
        public void onKakaoLoginSuccess(long userId) {
            //do check sign up from my server

        }

        @Override
        public void onFail(KakaoLoginError kakaoLoginError) {
            // do handling error
        }

    };

}
