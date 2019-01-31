package com.aone.menurandomchoice.data.oauth;

import android.content.Intent;

import com.aone.menurandomchoice.utils.NetworkUtil;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class KakaoLoginRepository implements KakaoLoginAPI {

    private static KakaoLoginRepository ourInstance;
    private ISessionCallback sessionCallback;
    private OnKakaoLoginListener onKakaoLoginListener;
    private OnKakaoLogoutListener onKakaoLogoutListener;

    public static KakaoLoginRepository getInstance() {
        if(ourInstance == null) {
            ourInstance = new KakaoLoginRepository();
        }

        return ourInstance;
    }

    private KakaoLoginRepository() {
        sessionCallback = new SessionCallback();
    }

    public void setOnKakaoLoginListener(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        this.onKakaoLoginListener = onKakaoLoginListener;
    }

    public void setOnKakaoLogoutListener(@NonNull OnKakaoLogoutListener onKakaoLogoutListener) {
        this.onKakaoLogoutListener = onKakaoLogoutListener;
    }

    @Override
    public void checkLoggedinAccount() {
        if(NetworkUtil.isNetworkConnecting()) {
            addCallbackToKakaoSession();

            boolean isSessionOpened = Session.getCurrentSession().checkAndImplicitOpen();
            if(!isSessionOpened) {
                clearCallbackFromKakaoSession();
                sendFailToListener(KakaoLoginError.NO_SESSION_ERROR);
            }
        } else {
            sendFailToListener(KakaoLoginError.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void executeDeviceKakaoAccountLogin() {
        if(NetworkUtil.isNetworkConnecting()) {
            openKakaoSession(AuthType.KAKAO_TALK);
        } else {
            sendFailToListener(KakaoLoginError.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void executeOtherKakaoAccountLogin() {
        if(NetworkUtil.isNetworkConnecting()) {
            openKakaoSession(AuthType.KAKAO_ACCOUNT);
        } else {
            sendFailToListener(KakaoLoginError.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data) {
        return Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void executeKakaoAccountLogout() {
        deleteLoggedInSession();
    }

    private void addCallbackToKakaoSession() {
        Session.getCurrentSession().addCallback(sessionCallback);
    }

    private void clearCallbackFromKakaoSession() {
        Session.getCurrentSession().clearCallbacks();
    }

    private void openKakaoSession(AuthType authType) {
        addCallbackToKakaoSession();
        Session.getCurrentSession().open(authType, KakaoSDK.getCurrentActivity());
    }

    private void requestUserInfoToUserManagement() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                KakaoLoginError kakaoLoginError = KakaoLoginError.convertToKakaoOauthError(errorResult);
                sendFailToListener(kakaoLoginError);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                sendLoginToListener(result);
            }
        });
    }

    private void deleteLoggedInSession() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                sendLogoutToListener();
            }
        });
    }

    private void sendLoginToListener(MeV2Response result) {
        if(onKakaoLoginListener != null) {
            onKakaoLoginListener.onLoginSuccess(result.getId());
        }
    }

    private void sendLogoutToListener() {
        if(onKakaoLogoutListener != null) {
            onKakaoLogoutListener.onLogoutSuccess();
        }
    }

    private void sendFailToListener(KakaoLoginError kakaoLoginError) {
        if(onKakaoLoginListener != null) {
            onKakaoLoginListener.onFail(kakaoLoginError);
        }
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            clearCallbackFromKakaoSession();
            requestUserInfoToUserManagement();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            clearCallbackFromKakaoSession();
            KakaoLoginError kakaoLoginError = KakaoLoginError.convertToKakaoOauthError(exception);
            sendFailToListener(kakaoLoginError);
        }

    }

}
