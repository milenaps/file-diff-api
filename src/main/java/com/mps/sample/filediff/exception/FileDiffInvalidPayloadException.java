package com.mps.sample.filediff.exception;

public class FileDiffInvalidPayloadException extends Exception {

    public FileDiffInvalidPayloadException(String message) {
        super(message);
    }

    public FileDiffInvalidPayloadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
