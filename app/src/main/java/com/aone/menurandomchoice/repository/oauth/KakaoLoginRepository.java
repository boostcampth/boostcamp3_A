package com.aone.menurandomchoice.repository.oauth;

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

public class KakaoLoginRepository implements KakaoLoginHelper {

    private static KakaoLoginRepository ourInstance;

    public static KakaoLoginRepository getInstance() {
        if(ourInstance == null) {
            ourInstance = new KakaoLoginRepository();
        }

        return ourInstance;
    }

    private KakaoLoginRepository() {
    }

    @Override
    public void checkLoggedinAccount(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        addCallbackToCurrentSession(onKakaoLoginListener);
        if (!Session.getCurrentSession().checkAndImplicitOpen()) {
            clearCallbackOfCurrentSession();
            onKakaoLoginListener.onFail(KakaoLoginError.NO_SESSION_ERROR);
        }
    }

    @Override
    public void executeDeviceKakaoAccountLogin(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        openKakaoSession(AuthType.KAKAO_TALK, onKakaoLoginListener);
    }

    @Override
    public void executeOtherKakaoAccountLogin(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        openKakaoSession(AuthType.KAKAO_ACCOUNT, onKakaoLoginListener);
    }

    @Override
    public void executeKakaoAccountLogout(@NonNull final OnKakaoLogoutListener onKakaoLogoutListener) {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                sendLogoutToListener(onKakaoLogoutListener);
            }
        });
    }

    @Override
    public boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data) {
        return Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);
    }

    private void openKakaoSession(@NonNull AuthType authType, final OnKakaoLoginListener onKakaoLoginListener) {
        if(NetworkUtil.isNetworkConnecting()) {
            addCallbackToCurrentSession(onKakaoLoginListener);
            Session.getCurrentSession().open(authType, KakaoSDK.getCurrentActivity());
        } else {
            clearCallbackOfCurrentSession();
            sendFailToListener(KakaoLoginError.NETWORK_NOT_CONNECT_ERROR, onKakaoLoginListener);
        }
    }

    private void addCallbackToCurrentSession(final OnKakaoLoginListener onKakaoLoginListener) {
        clearCallbackOfCurrentSession();

        Session.getCurrentSession().addCallback(new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                clearCallbackOfCurrentSession();
                requestUserInfoToUserManagement(onKakaoLoginListener);
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                clearCallbackOfCurrentSession();
                KakaoLoginError kakaoLoginError = KakaoLoginError.convertToKakaoOauthError(exception);
                sendFailToListener(kakaoLoginError, onKakaoLoginListener);
            }
        });
    }

    private void clearCallbackOfCurrentSession() {
        Session.getCurrentSession().clearCallbacks();
    }

    private void requestUserInfoToUserManagement(final OnKakaoLoginListener onKakaoLoginListener) {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                KakaoLoginError kakaoLoginError = KakaoLoginError.convertToKakaoOauthError(errorResult);
                sendFailToListener(kakaoLoginError, onKakaoLoginListener);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                sendLoginToListener(result, onKakaoLoginListener);
            }
        });
    }

    private void sendLoginToListener(MeV2Response result, OnKakaoLoginListener onKakaoLoginListener) {
        if(onKakaoLoginListener != null) {
            onKakaoLoginListener.onKakaoLoginSuccess(result.getId());
        }
    }

    private void sendLogoutToListener(OnKakaoLogoutListener onKakaoLogoutListener) {
        if(onKakaoLogoutListener != null) {
            onKakaoLogoutListener.onKakaoLogoutSuccess();
        }
    }

    private void sendFailToListener(KakaoLoginError kakaoLoginError, OnKakaoLoginListener onKakaoLoginListener) {
        if(onKakaoLoginListener != null) {
            onKakaoLoginListener.onFail(kakaoLoginError);
        }
    }

}
