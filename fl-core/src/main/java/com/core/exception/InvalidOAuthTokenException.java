package com.core.exception;

import com.mongodb.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidOAuthTokenException  extends ExceptionBase {

    public InvalidOAuthTokenException(@Nullable String message) {
        this.errorCode = ErrorResponseCode.OAUTH_INVALID;
        this.errorMessage = message;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
