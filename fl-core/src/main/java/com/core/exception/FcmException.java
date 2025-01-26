package com.core.exception;

import com.mongodb.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FcmException extends ExceptionBase {

    public FcmException(@Nullable String message) {
        this.errorCode = ErrorResponseCode.FCM_PUSH;
        this.errorMessage = message;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}