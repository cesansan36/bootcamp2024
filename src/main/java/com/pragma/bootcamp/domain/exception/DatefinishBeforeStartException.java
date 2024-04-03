package com.pragma.bootcamp.domain.exception;

public class DatefinishBeforeStartException extends RuntimeException {
    public DatefinishBeforeStartException(String message) {
        super(message);
    }
}
