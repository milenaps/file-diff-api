package com.mps.sample.filediff.exception;

public class FileDiffAlreadyExistsException extends Exception {

    public FileDiffAlreadyExistsException(String message) {
        super(message);
    }

    public FileDiffAlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
