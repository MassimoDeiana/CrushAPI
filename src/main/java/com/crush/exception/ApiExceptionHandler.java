package com.crush.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Throwable cause = e.getRootCause();
        if (cause instanceof java.sql.SQLException sqlException) {
            String state = sqlException.getSQLState();

            // 23505 is the SQLState for unique constraint violation in PostgreSQL and H2
            if ("23505".equals(state)) {
                String message = sqlException.getMessage();

                // Adjust this condition based on your constraint name or message
                if (message.contains("PHONE_NUMBER")) {
                    return buildResponseEntity(HttpStatus.CONFLICT, "Phone number already used");
                }
            }
        }
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }


    private void logException(Exception e) {
        LOGGER.info("Exception: " + e.getMessage());
    }

    private ResponseEntity<ApiException> buildResponseEntity(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ApiException(message, httpStatus), httpStatus);
    }

}
