package com.pragma.bootcamp.testdata;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TestDataController {

    private TestDataController() {throw new IllegalStateException("Utility class");}

    public static final String NAME = "%1$s name %2$s";
    public static final String DESCRIPTION = " %1$s description %2$s";

    public enum Element {
        TECHNOLOGY,
        CAPACITY,
        BOOTCAMP,
        BOOTCAMP_VERSION
    }

    public enum Fields {
        NAME,
        DESCRIPTION
    }

    public static String fieldText(Long id,Fields fields,  Element element ) {
        if (fields.equals(Fields.NAME)) {
            return String.format(NAME, element, id);
        }
        return String.format(DESCRIPTION, element, id);
    }

    public static TechnologyResponse getTechnologyResponse(Long id) {
        return new TechnologyResponse(id, String.format(NAME, Element.TECHNOLOGY, id), String.format(DESCRIPTION, Element.TECHNOLOGY, id));
    }
    public static List<TechnologyResponse> getListOfTechnologyResponse(Integer num) {
        List<TechnologyResponse> techsResponses = new ArrayList<>();
        for (Long i = 1L ; i <= num ; i++) {
            techsResponses.add(getTechnologyResponse(i));
        }
        return techsResponses;
    }
    public static TechnologyInCapacityResponse getTechnologyInCapacityResponse(Long id) {
        return new TechnologyInCapacityResponse(id, String.format(NAME, Element.TECHNOLOGY, id));
    }
    public static List<TechnologyInCapacityResponse> getListOfTechnologyInCapacityResponse(Integer num) {
        List<TechnologyInCapacityResponse> techsInCapResponses = new ArrayList<>();
        for (Long i = 1L ; i <= num ; i++) {
            techsInCapResponses.add(getTechnologyInCapacityResponse(i));
        }
        return techsInCapResponses;
    }
    public static CapacityResponse getCapacityResponse(Long id, Integer technologiesAmount) {
        List<TechnologyInCapacityResponse> techsInCapResponses = getListOfTechnologyInCapacityResponse(technologiesAmount);
        return new CapacityResponse(id, String.format(NAME, Element.CAPACITY, id), String.format(DESCRIPTION, Element.CAPACITY, id), techsInCapResponses);
    }
    public static List<CapacityResponse> getListOfCapacityResponse(Integer num, Integer technologiesAmount) {
        List<CapacityResponse> capResponses = new ArrayList<>();
        for (Long i = 1L ; i <= num ; i++) {
            capResponses.add(getCapacityResponse(i, technologiesAmount));
        }
        return capResponses;
    }
    public static CapacityInBootcampResponse getCapacityInBootcampResponse(Long id, Integer technologiesAmount) {
        return new CapacityInBootcampResponse(id, String.format(NAME, Element.CAPACITY, id), getListOfTechnologyInCapacityResponse(technologiesAmount));
    }
    public static List<CapacityInBootcampResponse> getListOfCapacityInBootcampResponse(Integer num, Integer technologiesAmount) {
        List<CapacityInBootcampResponse> capsInBootcampResponse = new ArrayList<>();
        for (Long i = 1L ; i <= num ; i++) {
            capsInBootcampResponse.add(getCapacityInBootcampResponse(i, technologiesAmount));
        }
        return capsInBootcampResponse;
    }

    public static BootcampResponse getBootcampResponse(Long id, Integer capacitiesAmount, Integer technologiesAmount) {
        List<CapacityInBootcampResponse> capsInBootcampResponse = getListOfCapacityInBootcampResponse(capacitiesAmount, technologiesAmount);
        return new BootcampResponse(id,
                String.format(NAME, Element.BOOTCAMP, id),
                capsInBootcampResponse);
    }
    public static List<BootcampResponse> getListOfBootcampResponse(Integer num, Integer capacitiesAmount, Integer technologiesAmount) {
        List<BootcampResponse> bootcampResponses = new ArrayList<>();
        for (Long i = 1L ; i <= num ; i++) {
            bootcampResponses.add(getBootcampResponse(i, capacitiesAmount, technologiesAmount));
        }
        return bootcampResponses;
    }

    public static BootcampVersionResponse getBootcampVersionResponse(Long id) throws ParseException {
        return new BootcampVersionResponse(id,
                String.format(NAME, Element.BOOTCAMP_VERSION, id),
                4,
                TestDataDomain.parseDate("2021-01-01"),
                TestDataDomain.parseDate("2021-01-03"),
                "b_a");
    }
    public static List<BootcampVersionResponse> getListOfBootcampVersionResponse(Integer num) throws ParseException {
        List<BootcampVersionResponse> bootcampVersionResponses = new ArrayList<>();
        for (Long i = 1L ; i <= num ; i++) {
            bootcampVersionResponses.add(getBootcampVersionResponse(i));
        }
        return bootcampVersionResponses;
    }
}
