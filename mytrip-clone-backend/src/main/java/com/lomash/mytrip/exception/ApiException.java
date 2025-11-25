package com.lomash.mytrip.exception;


public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}