package com.api.exception;

import com.core.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionResolver {
    private static final Logger logger = LogManager.getLogger(ExceptionResolver.class);

    @ExceptionHandler({AuthException.class, NotFoundDataException.class, InvalidDataException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleClientException(Exception exception) {
        return new ErrorResponse((ExceptionBase) exception);
    }

    @ExceptionHandler({
            InsufficientPointsException.class,
            GoogleLocationException.class,
            FcmException.class,
            S3UploadException.class,
            InvalidOAuthTokenException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleServerException(HttpServletRequest request, Exception exception) {
        //서버 오류만 로그 기록
        logException(request, (ExceptionBase)exception);
        return new ErrorResponse((ExceptionBase) exception);
    }

    private void logException(HttpServletRequest request, ExceptionBase exception) {
        String username = getAuthenticatedUsername();
        logger.error("Request URL: {}, Method: {}, Username: {}, Exception: {}",
                request.getRequestURL(),
                request.getMethod(),
                username,
                exception.getErrorMessage());
    }

    private String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "Anonymous";
    }
}