package com.crush.exception;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException() {
        super("An error occurred while processing your request.");
    }

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
