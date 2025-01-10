package com.api.exception;

import com.api.security.exception.InvalidTokenException;
import com.core.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestControllerAdvice
public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<Object> NoDataExceptionError(NoDataException e) {
        logError("NoDataException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1001);  // No data found
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<Object> DuplicateIdExceptionError(DuplicateIdException e) {
        logError("DuplicateIdException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1002);  // Duplicate ID error
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Object> EmailExceptionError(EmailException e) {
        logError("EmailException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1003);  // Email error
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FcmException.class)
    public ResponseEntity<Object> FcmExceptionError(FcmException e) {
        logError("FcmException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1004);  // Firebase Cloud Messaging error
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GoogleLocationException.class)
    public ResponseEntity<Object> GoogleLocationExceptionError(GoogleLocationException e) {
        logError("GoogleLocationException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1005);  // Google location service error
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InsufficientPointsException.class)
    public ResponseEntity<Object> InsufficientPointsExceptionError(InsufficientPointsException e) {
        logError("InsufficientPointsException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1006);  // Insufficient points error
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Object> InvalidDataExceptionError(InvalidDataException e) {
        logError("InvalidDataException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1007);  // Invalid data error
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOAuthTokenException.class)
    public ResponseEntity<Object> InvalidOAuthTokenExceptionError(InvalidOAuthTokenException e) {
        logError("InvalidOAuthTokenException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1008);  // Invalid OAuth token error
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> JwtExceptionError(JwtException e) {
        logError("JwtException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1009);  // JWT token error
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> InvalidTokenExceptionError(InvalidTokenException e) {
        logError("InvalidTokenException", e);
        ErrorResponse response = new ErrorResponse(false, e.getMessage(), 1010);  // JWT token error
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    private void logError(String exceptionType, Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Principal principal = request.getUserPrincipal();

        String username = (principal != null) ? principal.getName() : "Anonymous";
        String clientIp = request.getRemoteAddr();
        String requestUrl = request.getRequestURI();

        logger.error("{} 발생 - 사용자: {}, IP: {}, URL: {}, 오류 메시지: {}",
                exceptionType, username, clientIp, requestUrl, e.getMessage(), e);
    }
}
