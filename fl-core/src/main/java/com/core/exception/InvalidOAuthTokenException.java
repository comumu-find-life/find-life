package com.core.exception;

public class InvalidOAuthTokenException extends RuntimeException {
    public InvalidOAuthTokenException(String msg) { super(msg); }
}
