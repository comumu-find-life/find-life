package com.core.exception;

import com.mongodb.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundDataException extends ExceptionBase{

    public NotFoundDataException(@Nullable String message) {
        this.errorCode = ErrorResponseCode.NOT_FOUND;
        this.errorMessage = message;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
