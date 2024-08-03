package com.ryadovoy.todo.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private final String message;
    private final int status;
    private final List<ValidationError> errors = new ArrayList<>();

    public ErrorResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus.value();
    }

    public void addValidationError(String field, Object rejectedValue, String message) {
        errors.add(new ValidationError(field, rejectedValue, message));
    }

    private record ValidationError(String field, Object rejectedValue, String message) {
    }
}
