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
        openKakaoSession(KakaoLoginType.LOGGEDIN, onKakaoLoginListener);
    }

    @Override
    public void executeDeviceKakaoAccountLogin(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        openKakaoSession(KakaoLoginType.KAKAO_TALK, onKakaoLoginListener);
    }

    @Override
    public void executeOtherKakaoAccountLogin(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        openKakaoSession(KakaoLoginType.KAKAO_ACCOUNT, onKakaoLoginListener);
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

    private void openKakaoSession(@NonNull KakaoLoginType kakaoLoginType, final OnKakaoLoginListener onKakaoLoginListener) {
        if(NetworkUtil.isNetworkConnecting()) {
            addCallbackToCurrentSession(kakaoLoginType, onKakaoLoginListener);
            openCurrentSession(kakaoLoginType);
        } else {
            clearCallbackOfCurrentSession();
            sendFailToListener(KakaoLoginError.NETWORK_NOT_CONNECT_ERROR, onKakaoLoginListener);
        }
    }

    private void addCallbackToCurrentSession(final KakaoLoginType kakaoLoginType, final OnKakaoLoginListener onKakaoLoginListener) {
        clearCallbackOfCurrentSession();

        Session.getCurrentSession().addCallback(new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                clearCallbackOfCurrentSession();
                requestUserInfoToUserManagement(kakaoLoginType, onKakaoLoginListener);
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

    private void openCurrentSession(KakaoLoginType kakaoLoginType) {
        Session currentSession = Session.getCurrentSession();
        switch (kakaoLoginType) {
            case LOGGEDIN:
                if (!currentSession.checkAndImplicitOpen()) {
                    clearCallbackOfCurrentSession();
                }
                break;
            case KAKAO_TALK:
                currentSession.open(AuthType.KAKAO_TALK, KakaoSDK.getCurrentActivity());
                break;
            case KAKAO_ACCOUNT:
                currentSession.open(AuthType.KAKAO_ACCOUNT, KakaoSDK.getCurrentActivity());
                break;
            default:
                throw new IllegalStateException("used an invalid argument when opening the kakao session");
        }
    }

    private void requestUserInfoToUserManagement(final KakaoLoginType kakaoLoginType, final OnKakaoLoginListener onKakaoLoginListener) {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                KakaoLoginError kakaoLoginError = KakaoLoginError.convertToKakaoOauthError(errorResult);
                sendFailToListener(kakaoLoginError, onKakaoLoginListener);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                sendLoginToListener(result, kakaoLoginType, onKakaoLoginListener);
            }
        });
    }

    private void sendLoginToListener(MeV2Response result, KakaoLoginType kakaoLoginType, OnKakaoLoginListener onKakaoLoginListener) {
        if(onKakaoLoginListener != null) {
            onKakaoLoginListener.onKakaoLoginSuccess(result.getId(), kakaoLoginType);
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
