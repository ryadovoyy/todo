package com.ryadovoy.todo.advice;

public record ValidationError(String field, Object rejectedValue, String message) {
}
