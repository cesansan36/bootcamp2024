package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import com.pragma.bootcamp.domain.exception.QuantityAboveRequiredException;
import com.pragma.bootcamp.domain.exception.QuantityBelowRequiredException;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BootcampTest {
    @Test
    @DisplayName("Regular behaviour")
    void regularBehaviour() {
        Bootcamp bootcamp = new Bootcamp(0L, "Bootcamp_1", "Bootcamp 1");

        assertAll(
                () -> assertEquals(0L, bootcamp.getId()),
                () -> assertEquals("Bootcamp_1", bootcamp.getName()),
                () -> assertEquals("Bootcamp 1", bootcamp.getDescription())
        );
    }

    @Test
    @DisplayName("Should fail on empty name")
    void emptyNameException() {
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Bootcamp(0L, "", "bootcamp_1"));

        assertEquals("Field NAME can not be empty", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on empty description")
    void emptyDescriptionException() {
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Bootcamp(0L, "bootcamp 1", ""));

        assertEquals("Field DESCRIPTION can not be empty", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on name longer than 50 chars")
    void longNameException() {
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Bootcamp(0L, TestDataDomain.TOO_LONG_NAME, "bootcamp_1"));

        assertEquals("Field NAME can not have more than 50 characters", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on description longer than 90")
    void longDescriptionException() {
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Bootcamp(0L, "bootcamp 1", TestDataDomain.TOO_LONG_DESCRIPTION));

        assertEquals("Field DESCRIPTION can not have more than 90 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Setting and getting capabilities with no validation")
    void addingCapacities () {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapabilities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        List<Capability> caps = TestDataDomain.getListOfValidCapabilities(3);
        bootcamp.setCapabilities(caps);

        List<Capability> receivedCaps = bootcamp.getCapabilities();

        assertAll(
                () -> assertEquals(caps.size(), receivedCaps.size()),
                () -> {
                    for (int i = 0 ; i < receivedCaps.size() ; i++) {
                        assertEquals(caps.get(i).getId(), receivedCaps.get(i).getId());
                        assertEquals(caps.get(i).getName(), receivedCaps.get(i).getName());
                        assertEquals(caps.get(i).getDescription(), receivedCaps.get(i).getDescription());
                    }
                }
        );
    }
    @Test
    @DisplayName("Setting and getting capabilities with validation - success")
    void addingAndValidatingCapacitiesSuccess () {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapabilities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        List<Capability> caps = TestDataDomain.getListOfValidCapabilities(4);

        bootcamp.validateAndSetCapabilities(caps);

        assertEquals(caps.size(), bootcamp.getCapabilities().size());
    }
    @Test
    @DisplayName("Fail validation because too few capabilities")
    void addTooFewCapacities () {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapabilities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        List<Capability> caps = TestDataDomain.getListOfValidCapabilities(0);

        assertThrows(QuantityBelowRequiredException.class, () -> bootcamp.validateAndSetCapabilities(caps));
    }
    @Test
    @DisplayName("Fail validation because too many capabilities")
    void addTooManyCapacities () {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapabilities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        List<Capability> caps = TestDataDomain.getListOfValidCapabilities(5);

        assertThrows(QuantityAboveRequiredException.class, () -> bootcamp.validateAndSetCapabilities(caps));
    }
}