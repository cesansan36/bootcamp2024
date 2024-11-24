package com.pragma.bootcamp.domain.exception;

public class QuantityAboveRequiredException extends RuntimeException {
    public QuantityAboveRequiredException(String message) {
        super(message);
    }
}
