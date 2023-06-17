package com.crush.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(ApiExceptionHandler.class.getName());

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiException> handleRuntimeException(RuntimeException e) {

        logException(e);

        return buildResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiException> handleUserNotFoundException(UserNotFoundException e) {

        logException(e);

        return buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }


    private void logException(Exception e) {
        LOGGER.info("Exception: " + e.getMessage());
    }

    private ResponseEntity<ApiException> buildResponseEntity(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ApiException(message, httpStatus), httpStatus);
    }

}
