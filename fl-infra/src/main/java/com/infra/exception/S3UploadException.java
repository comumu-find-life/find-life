package com.infra.exception;

import androidx.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class S3UploadException  extends ExceptionBase {

    public S3UploadException(@Nullable String message) {
        this.errorCode = ErrorResponseCode.UPLOAD_S3_ERROR;
        this.errorMessage = message;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
