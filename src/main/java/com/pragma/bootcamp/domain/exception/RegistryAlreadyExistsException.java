package com.pragma.bootcamp.domain.exception;

public class RegistryAlreadyExistsException extends RuntimeException {

    public RegistryAlreadyExistsException(String message) {
        super(message);
    }
}
