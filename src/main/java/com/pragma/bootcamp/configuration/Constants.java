package com.pragma.bootcamp.configuration;

public class Constants {
    private Constants() {
        throw  new IllegalStateException("utility class");
    }

    public enum SortingField {
        NAME,
        BOOTCAMP_NAME,
        MAX_PARTICIPANTS,
        START_DATE
    }

    public static final String ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE = "The element you are looking for does not exist";
    public static final String NO_DATA_FOUND_EXCEPTION_MESSAGE = "No data was found in the database";
    public static final String DATE_FORMAT_USED = "yyyy-MM-dd";
    public static final String TIME_ZONE = "America/Bogota";
}
