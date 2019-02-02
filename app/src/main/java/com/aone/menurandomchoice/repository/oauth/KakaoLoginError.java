package com.aone.menurandomchoice.repository.oauth;

import com.aone.menurandomchoice.R;
import com.kakao.network.ErrorResult;
import com.kakao.util.exception.KakaoException;

public enum KakaoLoginError {


    SERVER_ERROR(R.string.activity_owner_toast_server_error),
    EXCEED_REQUEST_COUNT_ERROR(R.string.activity_owner_toast_exceed_request_count_error),
    AUTHORIZATION_FAIL_ERROR(R.string.activity_owner_toast_authorization_fail_error),
    SERVER_CHECK_ERROR(R.string.activity_owner_toast_server_check_error),
    SYSTEM_ERROR(R.string.activity_owner_toast_system_error),
    CANCELED_OPERATION_ERROR(R.string.activity_owner_toast_canceled_operation_error),
    NETWORK_NOT_CONNECT_ERROR(R.string.activity_owner_toast_network_not_connect_error),
    UNKNOWN_ERROR(R.string.activity_owner_toast_unknown_error);

    private int stringResourceId;

    KakaoLoginError(int stringResourceId) {
        this.stringResourceId = stringResourceId;
    }

    public int getStringResourceId() {
        return stringResourceId;
    }

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