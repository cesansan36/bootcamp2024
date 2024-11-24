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

class CapabilityTest {
    @Test
    @DisplayName("Regular behaviour")
    void regularBehaviour() {
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(4);

        Capability capability = new Capability(1L, "Backend", "Handles server-side logic", technologies);

        assertAll(
                () -> assertEquals(1L, capability.getId()),
                () -> assertEquals("Backend", capability.getName()),
                () -> assertEquals("Handles server-side logic", capability.getDescription()),
                () -> assertEquals(technologies, capability.getTechnologies()),
                () -> assertEquals(4, capability.getTechnologies().size()),
                () -> assertEquals(technologies.getFirst(), capability.getTechnologies().getFirst()),
                () -> assertEquals(technologies.get(1).getId(), capability.getTechnologies().get(1).getId()),
                () -> assertEquals(technologies.get(2).getName(), capability.getTechnologies().get(2).getName()),
                () -> assertEquals(technologies.get(3).getDescription(), capability.getTechnologies().get(3).getDescription())
        );
    }

    @Test
    @DisplayName("Should fail on empty name")
    void emptyNameException() {
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(4);
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Capability(1L, "", "Handles server-side logic", technologies));

        assertEquals("Field NAME can not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on empty description")
    void emptyDescriptionException() {
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(4);
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Capability(1L, "Backend", "", technologies));

        assertEquals("Field DESCRIPTION can not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on bellow minimum technologies")
    void emptyTechnologiesException() {
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(1);

        assertThrows(QuantityBelowRequiredException.class, () -> new Capability(1L, "Backend", "Handles server-side logic", technologies));
    }

    @Test
    @DisplayName("Should fail on name longer than 50 chars")
    void longNameException() {
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(4);
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Capability(1L, "123456789012345678901234567890123456789012345678901", "Handles server-side logic", technologies));

        assertEquals("Field NAME can not have more than 50 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on description longer than 90")
    void longDescriptionException() {
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(4);
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Capability(1L, "Backend", "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901", technologies));

        assertEquals("Field DESCRIPTION can not have more than 90 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on technologies quantity above required")
    void quantityBelowRequiredException() {
        List<Technology> tooMuchTechnologies = TestDataDomain.getListOfValidTechnologies(30);

        assertThrows(QuantityAboveRequiredException.class, () -> new Capability(1L, "Backend", "Handles server-side logic", tooMuchTechnologies));
    }
}
