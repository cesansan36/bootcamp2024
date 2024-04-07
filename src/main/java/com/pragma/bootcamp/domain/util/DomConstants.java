package com.pragma.bootcamp.domain.util;

public class DomConstants {

    private DomConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field {
        NAME,
        DESCRIPTION
    }

    public static final int MAX_NAME_FIELD_SIZE = 50;
    public static final int MAX_DESCRIPTION_FIELD_SIZE = 90;
    public static final int MIN_TECHNOLOGIES_IN_CAPABILITY = 3;
    public static final int MAX_TECHNOLOGIES_IN_CAPABILITY = 20;
    public static final int MIN_CAPABILITIES_IN_BOOTCAMP = 1;
    public static final int MAX_CAPABILITIES_IN_BOOTCAMP = 4;
    public static final int MIN_PARTICIPANTS_IN_BOOTCAMP_VERSION = 1;
    public static final int MAX_PARTICIPANTS_IN_BOOTCAMP_VERSION = 999;
    public static final String FIELD_MAX_SIZE_SURPASSED_MESSAGE = "Field %1$s can not have more than %2$s characters";
    public static final String FIELD_NULL_MESSAGE = "Field %s can not be null";
    public static final String FIELD_EMPTY_MESSAGE = "Field %s can not be empty";
    public static final String BELOW_MINIMUM_AMOUNT_OF_TECHNOLOGIES_MESSAGE = "The capability needs at least %s, technologies";
    public static final String ABOVE_MINIMUM_AMOUNT_OF_TECHNOLOGIES_MESSAGE = "The capability can not have more than %s, technologies";
    public static final String BELOW_MINIMUM_AMOUNT_OF_CAPABILITIES_MESSAGE = "The bootcamp needs at least %s, capabilities";
    public static final String ABOVE_MINIMUM_AMOUNT_OF_CAPABILITIES_MESSAGE = "The bootcamp can not have more than %s, capabilities";
    public static final String BELOW_MINIMUM_AMOUNT_OF_PARTICIPANTS_MESSAGE = "The bootcamp version needs at least %s, participants";
    public static final String ABOVE_MINIMUM_AMOUNT_OF_PARTICIPANTS_MESSAGE = "The bootcamp version can not have more than %s, participants";
    public static final String DATE_FINISH_BEFORE_START_MESSAGE = "The end date should be after start date";


}
