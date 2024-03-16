package com.pragma.bootcamp.domain.util;

public class DomConstants {

    private DomConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field {
        NAME,
        DESCRIPTION
    }

    public static final int NAME_SIZE = 50;
    public static final int DESCRIPTION_SIZE = 90;
    public static final String FIELD_NAME_NULL_MESSAGE = " field 'name' can not be empty";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = " field 'description' can not be empty";
}
