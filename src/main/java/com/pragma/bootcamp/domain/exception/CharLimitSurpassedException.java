package com.pragma.bootcamp.domain.exception;

public class CharLimitSurpassedException extends RuntimeException{
    public CharLimitSurpassedException(String message) {
        super(message);
    }
}
