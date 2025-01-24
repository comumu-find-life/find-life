package com.core.exception;

import com.mongodb.lang.Nullable;
import org.springframework.http.HttpStatus;

public class InvalidDataException extends ExceptionBase {

    public InvalidDataException(@Nullable String message) {
        this.errorCode = ErrorResponseCode.INVALID_DATA;
        this.errorMessage = message;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
