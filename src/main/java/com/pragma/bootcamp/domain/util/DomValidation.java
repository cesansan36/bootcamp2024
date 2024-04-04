package com.pragma.bootcamp.domain.util;

import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;

public class DomValidation {

    private DomValidation() { throw new IllegalStateException("Utility class"); }

    public static void validateName(String name) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(String.format(DomConstants.FIELD_EMPTY_MESSAGE, DomConstants.Field.NAME));
        }
        if(name.length() > DomConstants.MAX_NAME_FIELD_SIZE) {
            throw new CharLimitSurpassedException(String.format(DomConstants.FIELD_MAX_SIZE_SURPASSED_MESSAGE, DomConstants.Field.NAME, DomConstants.MAX_NAME_FIELD_SIZE));
        }
    }
    public static void validateDescription(String description) {
        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(String.format(DomConstants.FIELD_EMPTY_MESSAGE, DomConstants.Field.DESCRIPTION));
        }
        if(description.length() > DomConstants.MAX_DESCRIPTION_FIELD_SIZE) {
            throw new CharLimitSurpassedException(String.format(DomConstants.FIELD_MAX_SIZE_SURPASSED_MESSAGE, DomConstants.Field.DESCRIPTION, DomConstants.MAX_DESCRIPTION_FIELD_SIZE));
        }
    }
}
