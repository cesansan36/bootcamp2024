package com.pragma.bootcamp.configuration;

public class Constants {
    private Constants() {
        throw  new IllegalStateException("utility class");
    }

    public static final String CHAR_LIMIT_SURPASSED_EXCEPTION_MESSAGE = "Field %s can not surpass the char limit";
    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE = "The element you are looking for does not exist";
    public static final String NO_DATA_FOUND_EXCEPTION_MESSAGE = "No data was found in the database";
    public static final String TECHNOLOGY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The technology can not be created because it already exists";
}
