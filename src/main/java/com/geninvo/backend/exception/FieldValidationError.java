package com.geninvo.backend.exception;

public class FieldValidationError {

    private String field;
    private String error;

    public FieldValidationError(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }

}