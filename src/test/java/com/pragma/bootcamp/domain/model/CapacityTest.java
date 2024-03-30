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

class CapacityTest {
    @Test
    @DisplayName("Regular behaviour")
    void regularBehaviour() {
        Capacity cap = new Capacity(0L, "Capacity_1", "Capacity 1");

        assertAll(
                () -> assertEquals(0L, cap.getId()),
                () -> assertEquals("Capacity_1", cap.getName()),
                () -> assertEquals("Capacity 1", cap.getDescription())
        );
    }

    @Test
    @DisplayName("Should fail on empty name")
    void emptyNameException() {
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Capacity(0L, "", "capacity_1"));

        assertEquals("Field NAME can not be empty", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on empty description")
    void emptyDescriptionException() {
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Capacity(0L, "capacity 1", ""));

        assertEquals("Field DESCRIPTION can not be empty", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on name longer than 50 chars")
    void longNameException() {
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Capacity(0L, TestDataDomain.TOO_LONG_NAME, "capacity_1"));

        assertEquals("Field NAME can not have more than 50 characters", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on description longer than 90")
    void longDescriptionException() {
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Capacity(0L, "capacity 1", TestDataDomain.TOO_LONG_DESCRIPTION));

        assertEquals("Field DESCRIPTION can not have more than 90 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Setting and getting technologies with no validation")
    void addingTechnologies () {
        Capacity cap = TestDataDomain.getCapacityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        List<Technology> techs = TestDataDomain.getListOfValidTechnologies(3);
        cap.setTechnologies(techs);

        List<Technology> receivedTechs = cap.getTechnologies();

        assertAll(
                () -> assertEquals(techs.size(), receivedTechs.size()),
                () -> {
                    for (int i = 0 ; i < receivedTechs.size() ; i++) {
                        assertEquals(techs.get(i).getId(), receivedTechs.get(i).getId());
                        assertEquals(techs.get(i).getName(), receivedTechs.get(i).getName());
                        assertEquals(techs.get(i).getDescription(), receivedTechs.get(i).getDescription());
                    }
                }
        );
    }
    @Test
    @DisplayName("Setting and getting technologies with validation - success")
    void addingAndValidatingTechnologiesSuccess () {
        Capacity cap = TestDataDomain.getCapacityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        List<Technology> techs = TestDataDomain.getListOfValidTechnologies(4);

        cap.validateAndSetTechnologies(techs);

        assertEquals(techs.size(), cap.getTechnologies().size());
    }
    @Test
    @DisplayName("Fail validation because too few technologies")
    void addTooFewTechnologies () {
        Capacity cap = TestDataDomain.getCapacityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        List<Technology> techs = TestDataDomain.getListOfValidTechnologies(2);

        assertThrows(QuantityBelowRequiredException.class, () -> cap.validateAndSetTechnologies(techs));
    }
    @Test
    @DisplayName("Fail validation because too many technologies")
    void addTooManyTechnologies () {
        Capacity cap = TestDataDomain.getCapacityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        List<Technology> techs = TestDataDomain.getListOfValidTechnologies(21);

        assertThrows(QuantityAboveRequiredException.class, () -> cap.validateAndSetTechnologies(techs));
    }
}