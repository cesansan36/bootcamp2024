package com.pragma.bootcamp.testdata;

import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestDataDomain {
    private TestDataDomain() {throw new IllegalStateException("Utility class");}

    public enum DataCase {
        VALID,
        EMPTY,
        TOO_LONG
    }

    public enum Element {
        TECHNOLOGY,
        CAPABILITY,
        BOOTCAMP,
        BOOTCAMP_VERSION
    }

    public static final String VALID_NAME = "%1$s name %2$s";
    public static final String VALID_DESCRIPTION = "%1$s description %2$s";
    public static final int VALID_MAX_PARTICIPANTS = 10;
    public static final String TOO_LONG_NAME = "123456789012345678901234567890123456789012345678901";
    public static final String TOO_LONG_DESCRIPTION = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    public static final String EMPTY_STRING = "";


    public static String getValidName(Element element, int id) {
        return String.format(VALID_NAME, element, id);
    }
    public static String getValidDescription(Element element, int id) {
        return String.format(VALID_DESCRIPTION, element, id);
    }

    public static LocalDate getDateWithOffset(int daysOffset) {
        return LocalDate.now().plusDays(daysOffset);
    }
    public static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return LocalDate.parse(date, formatter);
    }
    public static Technology getTechnology(Long id, DataCase nameCase, DataCase descriptionCase) {
        String name = nameCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_NAME : VALID_NAME);
        String description = descriptionCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_DESCRIPTION : VALID_DESCRIPTION);

        if (nameCase == DataCase.VALID) {
            name = getValidName(Element.TECHNOLOGY, id.intValue());
        }
        if (descriptionCase == DataCase.VALID) {
            description = getValidDescription(Element.TECHNOLOGY, id.intValue());
        }

        return new Technology(id, name, description);
    }
    public static List<Technology> getListOfValidTechnologies(Integer technologyAmount) {
        List<Technology> techs = new ArrayList<>();
        for (Long i = 1L ; i <= technologyAmount ; i++) {
            techs.add(getTechnology(i, DataCase.VALID, DataCase.VALID));
        }
        return techs;
    }
    public static Capability getCapability(Long id, DataCase nameCase, DataCase descriptionCase, Integer technologyAmount) {
        String name = nameCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_NAME : VALID_NAME);
        String description = descriptionCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_DESCRIPTION : VALID_DESCRIPTION);

        if (nameCase == DataCase.VALID) {
            name = getValidName(Element.CAPABILITY, id.intValue());
        }
        if (descriptionCase == DataCase.VALID) {
            description = getValidDescription(Element.CAPABILITY, id.intValue());
        }

        return new Capability(id, name, description, getListOfValidTechnologies(technologyAmount));
    }
    public static List<Capability> getListOfValidCapabilities(Integer capabilityAmount, Integer technologyAmount) {
        List<Capability> caps = new ArrayList<>();
        for (Long i = 1L ; i <= capabilityAmount ; i++) {
            Capability cap = getCapability(i, DataCase.VALID, DataCase.VALID, technologyAmount);
            caps.add(cap);
        }
        return caps;
    }
    public static Bootcamp getBootcamp(Long id, DataCase nameCase, DataCase descriptionCase, Integer capabilityAmount, Integer technologyAmount) {
        String name = nameCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_NAME : VALID_NAME);
        String description = descriptionCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_DESCRIPTION : VALID_DESCRIPTION);

        if (nameCase == DataCase.VALID) {
            name = getValidName(Element.BOOTCAMP, id.intValue());
        }
        if (descriptionCase == DataCase.VALID) {
            description = getValidDescription(Element.BOOTCAMP, id.intValue());
        }

        return new Bootcamp(id, name, description, getListOfValidCapabilities(capabilityAmount, technologyAmount));
    }
    public static List<Bootcamp> getListOfValidBootcamps(Integer bootcampAmount, Integer capabilityAmount, Integer technologyAmount) {
        List<Bootcamp> bootcamps = new ArrayList<>();
        for (Long i = 1L ; i <= bootcampAmount ; i++) {
            Bootcamp bootcamp = getBootcamp(i, DataCase.VALID, DataCase.VALID, capabilityAmount, technologyAmount);
            bootcamps.add(bootcamp);
        }
        return bootcamps;
    }

    public static BootcampVersion getBootcampVersion(Long id, DataCase nameCase, int separationBetweenDates, Integer capabilityAmount, Integer technologyAmount) {
        String name = nameCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_NAME : VALID_NAME);

        if (nameCase == DataCase.VALID) {
            name = getValidName(Element.BOOTCAMP_VERSION, id.intValue());
        }
        LocalDate startDate = getDateWithOffset(0);
        LocalDate endDate = getDateWithOffset(separationBetweenDates);

        return new BootcampVersion(id, name, VALID_MAX_PARTICIPANTS, startDate, endDate, getBootcamp(id, DataCase.VALID, DataCase.VALID, capabilityAmount, technologyAmount));
    }

    public static List<BootcampVersion> getListOfValidBootcampVersions(Integer bootcampVersionAmount, int separationBetweenDates, Integer capabilityAmount, Integer technologyAmount) {
        List<BootcampVersion> bootcampVersions = new ArrayList<>();
        for (Long i = 1L ; i <= bootcampVersionAmount ; i++) {
            BootcampVersion bootcampVersion = getBootcampVersion(i, DataCase.VALID, separationBetweenDates, capabilityAmount, technologyAmount);
            bootcampVersions.add(bootcampVersion);
        }
        return bootcampVersions;
    }
}
