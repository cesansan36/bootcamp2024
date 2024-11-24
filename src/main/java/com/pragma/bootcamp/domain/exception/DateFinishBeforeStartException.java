package com.pragma.bootcamp.domain.exception;

public class DateFinishBeforeStartException extends RuntimeException {
    public DateFinishBeforeStartException(String message) {
        super(message);
    }
}
