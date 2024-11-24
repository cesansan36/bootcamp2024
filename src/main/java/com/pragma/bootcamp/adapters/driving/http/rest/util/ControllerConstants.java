package com.pragma.bootcamp.adapters.driving.http.rest.util;

public class ControllerConstants {

    private ControllerConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Add element
    public static final String OPERATION_SUMMARY_ADD_TECHNOLOGY = "Add a new technology";
    public static final String OPERATION_SUMMARY_ADD_CAPABILITY = "Add a new capability";
    public static final String OPERATION_SUMMARY_ADD_BOOTCAMP = "Add a new bootcamp";
    public static final String OPERATION_SUMMARY_ADD_BOOTCAMP_VERSION = "Add a new bootcamp version";
    public static final String OPERATION_DESCRIPTION_ADD_TECHNOLOGY = "Needs an admin token\n\nA technology with same name must not exists";
    public static final String OPERATION_DESCRIPTION_ADD_CAPABILITY = "Needs an admin token\n\nA capability with same name must not exists";
    public static final String OPERATION_DESCRIPTION_ADD_BOOTCAMP = "Needs an admin token\n\nA bootcamp with same name must not exists";
    public static final String OPERATION_DESCRIPTION_ADD_BOOTCAMP_VERSION = "Needs an admin token\n\nA bootcamp version with same name must not exists";

    // Get single element
    public static final String OPERATION_SUMMARY_GET_ELEMENT = "Get a single element";
    public static final String OPERATION_DESCRIPTION_GET_ELEMENT = "Gets a single element by its name";

    // Get list of elements
    public static final String OPERATION_SUMMARY_GET_LIST = "Get a list of elements";
    public static final String OPERATION_DESCRIPTION_GET_LIST_TECHNOLOGIES = """
Gets a list of technologies determined by its page, size and sort order\n\n
If page is negative or null, the first page will be returned\n\n
If size is less than 1 or null, a size of 1 will be used\n\n
""";
    public static final String OPERATION_DESCRIPTION_GET_LIST_CAPABILITY = """
Gets a list of capabilities determined by its page, size, sort order and sorting field\n\n
If page is negative or null, the first page will be returned\n\n
If size is less than 1 or null, a size of 1 will be used\n\n
If isSortByTechnologiesAmount is true the list will be sorted by the amount of technologies in each capability, otherwise it will be sorted by name
""";

    public static final String OPERATION_DESCRIPTION_GET_LIST_BOOTCAMP = """
Gets a list of bootcamps determined by its page, size, sort order and sorting field\n\n
If page is negative or null, the first page will be returned\n\n
If size is less than 1 or null, a size of 1 will be used\n\n
If isSortByCapabilitiesAmount is true the list will be sorted by the amount of capabilities in each bootcamp, otherwise it will be sorted by name
""";

    public static final String OPERATION_DESCRIPTION_GET_LIST_BOOTCAMP_VERSION_ALL = """
Gets a list of bootcamp-version determined by its page, size, sort order and sorting field\n\n
If page is negative or null, the first page will be returned\n\n
If size is less than 1 or null, a size of 1 will be used\n\n
If sortingField = START_DATE, the list will be sorted by start date of bootcamp\n\n
If sortingField = MAX_PARTICIPANTS, the list will be sorted by max participants in bootcamp\n\n
If sortingField = NAME, the list will be sorted by the name of the version of the bootcamp\n\n
If sortingField = BOOTCAMP_NAME, the list will be sorted by name of the bootcamp
""";

    public static final String OPERATION_DESCRIPTION_GET_LIST_BOOTCAMP_VERSION_SINGLE = """
Gets a list of bootcamp-version determined by its page, size, sort order, sorting field and the name of bootcamp in the url\n\n
If page is negative or null, the first page will be returned\n\n
If size is less than 1 or null, a size of 1 will be used\n\n
If sortingField = START_DATE, the list will be sorted by start date of bootcamp\n\n
If sortingField = MAX_PARTICIPANTS, the list will be sorted by max participants in bootcamp\n\n
If sortingField = BOOTCAMP_NAME or NAME, the list will be sorted by name of the version of the bootcamp
""";


    // Response status
    public static final String RESPONSE_CREATED_DESCRIPTION = "Created";
    public static final String RESPONSE_NOT_FOUND_DESCRIPTION = "Not found: The element you are looking for does not exist";
    public static final String RESPONSE_OK_DESCRIPTION = "OK: gets the info of the element requested";

    public static final String RESPONSE_BAD_REQUEST_DESCRIPTION_ADD_TECHNOLOGY = """
Bad Request: possible reasons: \n\n
The technology name already exists\n\n
The field name and/or description are empty\n\n
The field name and/or description exceed the char limit""";
    public static final String RESPONSE_BAD_REQUEST_DESCRIPTION_ADD_CAPABILITY = """
Bad Request: possible reasons: \n\n
The capability name already exists\n\n
The field name and/or description are empty\n\n
The field name and/or description exceed the char limit""";
    public static final String RESPONSE_BAD_REQUEST_DESCRIPTION_ADD_BOOTCAMP = """
Bad Request: possible reasons: \n\n
The bootcamp name already exists\n\n
The field name and/or description are empty\n\n
The field name and/or description exceed the char limit""";
    public static final String RESPONSE_BAD_REQUEST_DESCRIPTION_ADD_BOOTCAMP_VERSION = """
Bad Request: possible reasons: \n\n
The bootcamp version name already exists\n\n
The field name is empty\n\n
The field name exceeds the char limit""";
}
