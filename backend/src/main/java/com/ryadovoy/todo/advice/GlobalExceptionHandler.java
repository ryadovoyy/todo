package com.ryadovoy.todo.advice;

import com.ryadovoy.todo.task.exception.TaskNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("Validation errors occurred", HttpStatus.BAD_REQUEST);

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorResponse.addValidationError(error.getField(), error.getRejectedValue(), error.getDefaultMessage());
        });

        return createResponseEntity(errorResponse, headers, status, request);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        return handleCustomException(ex, request, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request, HttpStatusCode status) {
        ProblemDetail body = createProblemDetail(ex, status, ex.getMessage(), null, null, request);
        return handleExceptionInternal(ex, body, HttpHeaders.EMPTY, status, request);
    }
}
