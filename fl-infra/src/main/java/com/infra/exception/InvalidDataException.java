package com.infra.exception;

import androidx.annotation.Nullable;
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
