package com.crush.exception;

import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(ApiExceptionHandler.class.getName());

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiException> handleRuntimeException(RuntimeException e) {

        logException(e);

        return buildResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ApiException> handleValidationException(ValidationException e) {

        logException(e);

        return buildResponseEntity(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiException> handleUserNotFoundException(UserNotFoundException e) {

        logException(e);

        return buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        logException(e);

        if (e.getRootCause() instanceof java.sql.SQLException sqlException) {
            String state = sqlException.getSQLState();

            if ("23505".equals(state)) {
                String message = sqlException.getMessage();

                if (message.contains("PHONE_NUMBER")) {
                    return buildResponseEntity(HttpStatus.CONFLICT, "Phone number already used");
                }
            }
        }
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logException(e);

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            if(errors.containsKey(fieldError.getField())) {
                errors.put(fieldError.getField(), errors.get(fieldError.getField()) + ", " + fieldError.getDefaultMessage());
            } else {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        });

        return buildResponseEntity(HttpStatus.BAD_REQUEST, errors.toString());
    }


    private void logException(Exception e) {
        LOGGER.info("Exception: " + e.getMessage());
    }

    private ResponseEntity<ApiException> buildResponseEntity(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ApiException(message, httpStatus), httpStatus);
    }

}
