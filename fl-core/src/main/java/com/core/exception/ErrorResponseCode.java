package com.core.exception;

import java.util.Arrays;

public enum ErrorResponseCode {
    NO_AUTH_TOKEN(4011),
    NOT_VALID_TOKEN(4012),
    FAIL_SEND_TOKEN(4013),
    NOT_ALLOWED(4031),
    NOT_FOUND(4041),
    INVALID_DATA(4042),
    UPLOAD_S3_ERROR(5004),
    FCM_PUSH(5005),
    GOOGLE_LOCATION(5006),
    POINT_INSUFFICIENT(5007),
    OAUTH_INVALID(5009);

    private final int code;

    ErrorResponseCode(int c) {
        this.code = c;
    }

    public static ErrorResponseCode getName(int code) {
        return Arrays.stream(ErrorResponseCode.values()).filter(c -> c.code == code).findFirst().orElse(null);
    }

    public int getCode() {
        return this.code;
    }
}