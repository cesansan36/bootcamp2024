package com.pragma.bootcamp.adapters.driven.jpa.mysql.util;

public class AdapterConstants {
    private AdapterConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Registry {
        TECHNOLOGY,
        CAPABILITY,
        BOOTCAMP,
        BOOTCAMP_VERSION
    }

    public static final String REGISTRY_NAME_ALREADY_USED = "A %s with that name already exists";

    public static final String FIELD_NAME_FOR_SORTING_TECHNOLOGIES = "name";
    public static final String FIELD_NAME_OF_SORT_BY_NAME = "name";
    public static final String FIELD_NAME_OF_SORT_BY_TECHNOLOGIES = "teccount";
    public static final String FIELD_NAME_OF_SORT_BY_CAPABILITIES = "capcount";
    public static final String FIELD_NAME_OF_SORT_VERSION_BY_NAME = "name";
    public static final String FIELD_NAME_OF_SORT_VERSION_BY_BOOTCAMP_NAME = "bootcamp.name";
    public static final String FIELD_NAME_OF_SORT_VERSION_BY_MAX_PARTICIPANTS = "maxParticipants";
    public static final String FIELD_NAME_OF_SORT_VERSION_BY_START_DATE = "startDate";
    public static final String GET_ALL_CAPABILITIES_CUSTOM_QUERY = "SELECT capability.*, count(capability_technology.ID_CAPABILITY) as teccount FROM capability, capability_technology WHERE capability.id = capability_technology.ID_CAPABILITY GROUP BY capability_technology.ID_CAPABILITY";
    public static final String GET_ALL_BOOTCAMPS_CUSTOM_QUERY = "SELECT bootcamp.*, count(capability_bootcamp.ID_BOOTCAMP) as capcount FROM bootcamp, capability_bootcamp WHERE bootcamp.id = capability_bootcamp.ID_BOOTCAMP GROUP BY capability_bootcamp.ID_BOOTCAMP";


}
