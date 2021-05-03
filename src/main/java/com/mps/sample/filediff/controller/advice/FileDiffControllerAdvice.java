package com.mps.sample.filediff.controller.advice;

import com.mps.sample.filediff.exception.FileDiffAlreadyExistsException;
import com.mps.sample.filediff.exception.FileDiffIdNotFoundException;
import com.mps.sample.filediff.exception.FileDiffInvalidPayloadException;
import com.mps.sample.filediff.exception.FileDiffNotReadyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FileDiffControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({FileDiffInvalidPayloadException.class})
    public ResponseEntity<String> handleInvalidPayloadException(Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FileDiffAlreadyExistsException.class})
    public ResponseEntity<String> handleAlreadyExistsException(Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({FileDiffIdNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({FileDiffNotReadyException.class})
    public ResponseEntity<String> handleNotReadyException(Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_GATEWAY);
    }
}
