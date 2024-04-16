package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import com.pragma.bootcamp.domain.exception.QuantityAboveRequiredException;
import com.pragma.bootcamp.domain.exception.QuantityBelowRequiredException;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BootcampTest {
    @Test
    @DisplayName("Regular behaviour")
    void regularBehaviour() {
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(4, 4);

        Bootcamp bootcamp = new Bootcamp(1L, "Bootcamp A", "Description A", capabilities);

        assertAll(
                () -> assertEquals(1L, bootcamp.getId()),
                () -> assertEquals("Bootcamp A", bootcamp.getName()),
                () -> assertEquals("Description A", bootcamp.getDescription()),
                () -> assertEquals(capabilities, bootcamp.getCapabilities()),
                () -> assertEquals(4, bootcamp.getCapabilities().size()),
                () -> assertEquals(capabilities.getFirst(), bootcamp.getCapabilities().getFirst()),
                () -> assertEquals(capabilities.get(1).getId(), bootcamp.getCapabilities().get(1).getId()),
                () -> assertEquals(capabilities.get(2).getName(), bootcamp.getCapabilities().get(2).getName()),
                () -> assertEquals(capabilities.get(3).getDescription(), bootcamp.getCapabilities().get(3).getDescription())
        );
    }

    @Test
    @DisplayName("Should fail on empty name")
    void emptyNameException() {
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(4, 4);
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Bootcamp(1L, "", "Description A", capabilities));

        assertEquals("Field NAME can not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on empty description")
    void emptyDescriptionException() {
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(4, 4);
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Bootcamp(1L, "Bootcamp A", "", capabilities));

        assertEquals("Field DESCRIPTION can not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on bellow minimum capabilities")
    void emptyCapabilitiesException() {
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(0, 4);

        assertThrows(QuantityBelowRequiredException.class, () -> new Bootcamp(1L, "Bootcamp A", "Description A", capabilities));
    }

    @Test
    @DisplayName("Should fail on name longer than 50 chars")
    void longNameException() {
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(4, 4);
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Bootcamp(1L, "123456789012345678901234567890123456789012345678901", "Description A", capabilities));

        assertEquals("Field NAME can not have more than 50 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on description longer than 90")
    void longDescriptionException() {
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(4, 4);
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Bootcamp(1L, "Bootcamp A", "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901", capabilities));

        assertEquals("Field DESCRIPTION can not have more than 90 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on capabilities quantity above required")
    void quantityAboveRequiredException() {
        List<Capability> tooMuchCapabilities = TestDataDomain.getListOfValidCapabilities(30, 4);

        assertThrows(QuantityAboveRequiredException.class, () -> new Bootcamp(1L, "Bootcamp A", "Description A", tooMuchCapabilities));
    }
}
