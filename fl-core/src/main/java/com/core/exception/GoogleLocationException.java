package com.core.exception;

import com.mongodb.lang.Nullable;
import org.springframework.http.HttpStatus;

public class GoogleLocationException  extends ExceptionBase {

    public GoogleLocationException(@Nullable String message) {
        this.errorCode = ErrorResponseCode.GOOGLE_LOCATION;
        this.errorMessage = message;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
