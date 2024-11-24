package com.pragma.bootcamp.domain.exception;

public class QuantityBelowRequiredException extends RuntimeException {
    public QuantityBelowRequiredException(String message) {
        super(message);
    }
}
