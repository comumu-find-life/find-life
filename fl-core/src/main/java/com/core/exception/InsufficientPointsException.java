package com.core.exception;

import java.io.IOException;

public class InsufficientPointsException extends IOException {

    public InsufficientPointsException(String message) {
        super(message);
    }
}