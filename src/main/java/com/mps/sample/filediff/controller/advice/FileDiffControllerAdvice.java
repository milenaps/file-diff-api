package com.mps.sample.filediff.controller.advice;

import com.mps.sample.filediff.controller.dto.ErrorResponse;
import com.mps.sample.filediff.exception.FileDiffAlreadyExistsException;
import com.mps.sample.filediff.exception.FileDiffIdNotFoundException;
import com.mps.sample.filediff.exception.FileDiffNotReadyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FileDiffControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex) {
        return getResponseEntity(HttpStatus.NOT_ACCEPTABLE, ex);
    }

    @ExceptionHandler({FileDiffAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(Exception ex) {
        return getResponseEntity(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler({FileDiffIdNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        return getResponseEntity(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({FileDiffNotReadyException.class})
    public ResponseEntity<ErrorResponse> handleNotReadyException(Exception ex) {
        return getResponseEntity(HttpStatus.BAD_GATEWAY, ex);
    }

    private ResponseEntity<ErrorResponse> getResponseEntity(HttpStatus status, Exception ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(status.value(), ex.getMessage()), status);
    }
}
