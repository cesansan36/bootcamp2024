package com.pragma.bootcamp.testdata;

import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDataDomain {
    private TestDataDomain() {throw new IllegalStateException("Utility class");}

    public enum DataCase {
        VALID,
        EMPTY,
        TOO_LONG
    }

    public static final String VALID_NAME = "Valid name %s";
    public static final String VALID_DESCRIPTION = "Valid description %s";
    public static final int VALID_MAX_PARTICIPANTS = 10;
    public static final String TOO_LONG_NAME = "123456789012345678901234567890123456789012345678901";
    public static final String TOO_LONG_DESCRIPTION = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    public static final String EMPTY_STRING = "";


    public static String getValidName(Integer id) {
        return String.format(VALID_NAME, id);
    }
    public static String getValidDescription(Integer id) {
        return String.format(VALID_DESCRIPTION, id);
    }

    public static LocalDate getDateWithOffset(int daysOffset) {
        LocalDate date = LocalDate.now().plusDays(daysOffset);
        return date;
    }
    public static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return LocalDate.parse(date, formatter);
    }
    public static Technology getTechnology(Long id, DataCase nameCase, DataCase descriptionCase) {
        String name = nameCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_NAME : VALID_NAME);
        String description = descriptionCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_DESCRIPTION : VALID_DESCRIPTION);

        if (nameCase == DataCase.VALID) {
            name = String.format(name, id);
        }
        if (descriptionCase == DataCase.VALID) {
            description = String.format(description, id);
        }

        return new Technology(id, name, description);
    }
    public static List<Technology> getListOfValidTechnologies(Integer num) {
        List<Technology> techs = new ArrayList<>();
        for (Long i = 0L ; i < num ; i++) {
            techs.add(getTechnology(i, DataCase.VALID, DataCase.VALID));
        }
        return techs;
    }
    public static Capacity getCapacityWithNoTechnologies(Long id, DataCase nameCase, DataCase descriptionCase) {
        String name = nameCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_NAME : VALID_NAME);
        String description = descriptionCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_DESCRIPTION : VALID_DESCRIPTION);

        if (nameCase == DataCase.VALID) {
            name = String.format(name, id);
        }
        if (descriptionCase == DataCase.VALID) {
            description = String.format(description, id);
        }

        return new Capacity(id, name, description);
    }
    public static List<Capacity> getListOfValidCapacities(Integer num) {
        List<Capacity> caps = new ArrayList<>();
        for (Long i = 0L ; i < num ; i++) {
            Capacity cap = getCapacityWithNoTechnologies(i, DataCase.VALID, DataCase.VALID);
            cap.setTechnologies(getListOfValidTechnologies(3));
            caps.add(cap);
        }
        return caps;
    }
    public static Bootcamp getBootcampWithNoCapacities(Long id, DataCase nameCase, DataCase descriptionCase) {
        String name = nameCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_NAME : VALID_NAME);
        String description = descriptionCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_DESCRIPTION : VALID_DESCRIPTION);

        if (nameCase == DataCase.VALID) {
            name = String.format(name, id);
        }
        if (descriptionCase == DataCase.VALID) {
            description = String.format(description, id);
        }

        return new Bootcamp(id, name, description);
    }
    public static List<Bootcamp> getListOfValidBootcamps(Integer num) {
        List<Bootcamp> bootcamps = new ArrayList<>();
        for (Long i = 0L ; i < num ; i++) {
            Bootcamp bootcamp = getBootcampWithNoCapacities(i, DataCase.VALID, DataCase.VALID);
            bootcamp.setCapacities(getListOfValidCapacities(3));
            bootcamps.add(bootcamp);
        }
        return bootcamps;
    }

    public static BootcampVersion getBootcampVersionWithNoBootcamp(Long id, DataCase nameCase, int separationBetweenDates) {
        String name = nameCase == DataCase.EMPTY ? EMPTY_STRING : (nameCase == DataCase.TOO_LONG ? TOO_LONG_NAME : VALID_NAME);

        if (nameCase == DataCase.VALID) {
            name = String.format(name, id);
        }
        LocalDate startDate = getDateWithOffset(0);
        LocalDate endDate = getDateWithOffset(separationBetweenDates);

        return new BootcampVersion(id, name, VALID_MAX_PARTICIPANTS, startDate, endDate);
    }

    public static List<BootcampVersion> getListOfValidBootcampVersions(Integer num, int separationBetweenDates) {
        List<BootcampVersion> bootcampVersions = new ArrayList<>();
        for (Long i = 0L ; i < num ; i++) {
            BootcampVersion bootcampVersion = getBootcampVersionWithNoBootcamp(i, DataCase.VALID, separationBetweenDates);
            Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapacities(i, DataCase.VALID, DataCase.VALID);
            bootcampVersion.setBootcamp(bootcamp);
            bootcampVersions.add(bootcampVersion);
        }
        return bootcampVersions;
    }
}
