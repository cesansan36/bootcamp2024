package com.pragma.bootcamp.adapters.driven.jpa.mysql.util;

public class AdapterConstants {
    private AdapterConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_FOR_SORTING_TECHNOLOGIES = "name";
    public static final String FIELD_NAME_OF_SORT_BY_NAME = "name";
    public static final String FIELD_NAME_OF_SORT_BY_TECHNOLOGIES = "teccount";
    public static final String GET_ALL_CAPACITIES_CUSTOM_QUERY = "SELECT capacity.*, count(capacity_technology.ID_CAPACITY) as teccount FROM capacity, capacity_technology WHERE capacity.id = capacity_technology.ID_CAPACITY GROUP BY capacity_technology.ID_CAPACITY";
}
