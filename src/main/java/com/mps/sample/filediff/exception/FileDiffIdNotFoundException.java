package com.mps.sample.filediff.exception;

public class FileDiffIdNotFoundException extends Exception {

    public FileDiffIdNotFoundException(String message) {
        super(message);
    }

    public FileDiffIdNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
