package com.pragma.bootcamp.domain.exception;

public class EmptyFieldException extends RuntimeException {
	public EmptyFieldException(String message) {
		super(message);
	}
}
