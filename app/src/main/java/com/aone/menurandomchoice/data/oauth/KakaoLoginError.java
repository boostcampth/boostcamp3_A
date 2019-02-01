package com.aone.menurandomchoice.data.oauth;

import com.kakao.network.ErrorResult;
import com.kakao.util.exception.KakaoException;

public enum KakaoLoginError {

    SERVER_ERROR,
    EXCEED_REQUEST_COUNT_ERROR,
    AUTHORIZATION_FAIL_ERROR,
    SERVER_CHECK_ERROR,
    SYSTEM_ERROR,
    CANCELED_OPERATION_ERROR,
    NETWORK_NOT_CONNECT_ERROR,
    NO_SESSION_ERROR,
    UNKNOWN_ERROR;

    public static KakaoLoginError convertToKakaoOauthError(ErrorResult errorResult) {
        if(errorResult != null) {
            switch (errorResult.getErrorCode()) {
                case -1:
                    return SERVER_ERROR;
                case -10:
                    return EXCEED_REQUEST_COUNT_ERROR;
                case -776:
                    return AUTHORIZATION_FAIL_ERROR;
                case -9798:
                    return SERVER_CHECK_ERROR;
                    default:
                        return UNKNOWN_ERROR;
            }
        } else {
            return UNKNOWN_ERROR;
        }
    }

    public static KakaoLoginError convertToKakaoOauthError(KakaoException kakaoException) {
        if(kakaoException != null) {
            switch(kakaoException.getErrorType()) {
                case JSON_PARSING_ERROR:
                    return SYSTEM_ERROR;
                case AUTHORIZATION_FAILED:
                    return AUTHORIZATION_FAIL_ERROR;
                case CANCELED_OPERATION:
                    return CANCELED_OPERATION_ERROR;
                    default:
                        return UNKNOWN_ERROR;
            }
        } else {
            return UNKNOWN_ERROR;
        }
    }

}