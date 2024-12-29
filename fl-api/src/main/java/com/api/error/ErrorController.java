package com.api.error;

import com.core.exception.NoDataException;
import com.common.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<Object> NoDataException(NoDataException e) {
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1000);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
