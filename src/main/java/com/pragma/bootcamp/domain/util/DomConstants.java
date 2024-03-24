package com.pragma.bootcamp.domain.util;

public class DomConstants {

    private DomConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field {
        NAME,
        DESCRIPTION
    }

    public static final int MAX_TECHNOLOGY_NAME_SIZE = 50;
    public static final int MAX_TECHNOLOGY_DESCRIPTION_SIZE = 90;
    public static final int MIN_TECHNOLOGIES_IN_CAPACITY = 3;
    public static final int MAX_TECHNOLOGIES_IN_CAPACITY = 20;
    public static final String FIELD_MAX_SIZE_SURPASSED_MESSAGE = "Field %1$s can not have more than %2$s characters";
    public static final String FIELD_NULL_MESSAGE = "Field %s can not be null";
    public static final String FIELD_EMPTY_MESSAGE = "Field %s can not be empty";
    public static final String FIELD_NAME_NULL_MESSAGE = " field 'name' can not be empty";

    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = " field 'description' can not be empty";
    public static final String BELOW_MINIMUM_AMOUNT_OF_TECHNOLOGIES_MESSAGE = "The capacity needs at least %s, technologies";
    public static final String ABOVE_MINIMUM_AMOUNT_OF_TECHNOLOGIES_MESSAGE = "The capacity can not have more than %s, technologies";


}
