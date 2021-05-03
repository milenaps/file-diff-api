package com.mps.sample.filediff.exception;

public class FileDiffNotReadyException extends Exception {

    public FileDiffNotReadyException(String message) {
        super(message);
    }

    public FileDiffNotReadyException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
