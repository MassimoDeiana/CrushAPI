package com.crush.exception;

public class InvalidJwtAuthenticationException extends RuntimeException{

    public InvalidJwtAuthenticationException() {
        super("Invalid JWT authentication.");
    }

    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }

    public InvalidJwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

}
