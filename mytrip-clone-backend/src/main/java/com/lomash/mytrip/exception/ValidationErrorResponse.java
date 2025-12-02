package com.lomash.mytrip.exception;

import java.util.Map;

public class ValidationErrorResponse {

    private boolean success = false;
    private String message = "Validation failed";
    private Map<String, String> errors;

    public ValidationErrorResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
