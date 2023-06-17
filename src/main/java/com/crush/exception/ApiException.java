package com.crush.exception;

import org.springframework.http.HttpStatus;

public record ApiException(String message, HttpStatus httpStatus) {

}
