package com.core.exception;

public class S3UploadException extends RuntimeException {
    public S3UploadException(String msg) { super(msg); }
}