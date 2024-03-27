package com.pragma.bootcamp.configuration.exceptionhandler;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistException;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import com.pragma.bootcamp.domain.exception.QuantityAboveRequiredException;
import com.pragma.bootcamp.domain.exception.QuantityBelowRequiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleElementNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                Constants.ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.CONFLICT.toString(), LocalDateTime.now()
                ));
    }
    @ExceptionHandler(TechnologyAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleTechnologyAlreadyExistException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.TECHNOLOGY_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoDataFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                Constants.NO_DATA_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()
                ));
    }
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(EmptyFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                exception.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(CharLimitSurpassedException.class)
    public ResponseEntity<ExceptionResponse> handleCharLimitSurpassedException(CharLimitSurpassedException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                exception.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(QuantityBelowRequiredException.class)
    public ResponseEntity<ExceptionResponse> handleQuantityBelowRequiredException(QuantityBelowRequiredException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                exception.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(QuantityAboveRequiredException.class)
    public ResponseEntity<ExceptionResponse> handleQuantityAboveRequiredException(QuantityAboveRequiredException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                exception.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
}
