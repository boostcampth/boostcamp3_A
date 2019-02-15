package com.aone.menurandomchoice.repository.remote.response;

import com.aone.menurandomchoice.R;

public enum JMTErrorCode {

    REQUEST_NO_RESULT(204, R.string.JMT_response_no_result),
    WRONG_REQUEST(400, R.string.JMT_response_wrong_request_error),
    NOT_FOUND(404, R.string.JMT_response_not_found_error),
    SERVER_SYSTEM_ERROR(500, R.string.JMT_response_server_system_error),
    SERVER_DB_ERROR(600, R.string.JMT_response_server_database_error),
    UNKNOWN_ERROR(-1, R.string.JMT_response_unknown_error),
    NETWORK_NOT_CONNECT_ERROR(-2, R.string.activity_owner_toast_network_not_connect_error);

    private int statusCode;
    private int stringResourceId;

    JMTErrorCode(int statusCode, int stringResourceId) {
        this.statusCode = statusCode;
        this.stringResourceId = stringResourceId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getStringResourceId() {
        return stringResourceId;
    }

    public static JMTErrorCode convertToJMTErrorCode(int statusCode) {
        for(JMTErrorCode errorCode : JMTErrorCode.values()) {
            if(statusCode == errorCode.getStatusCode()) {
                return errorCode;
            }
        }

        return UNKNOWN_ERROR;
    }
}
