package com.crush.exception;

public class UserAlreadyVerifiedException extends RuntimeException {

    public UserAlreadyVerifiedException() {
        super("User already verified.");
    }

    public UserAlreadyVerifiedException(String message) {
        super(message);
    }

}
