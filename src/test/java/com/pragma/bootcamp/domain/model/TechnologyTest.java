package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechnologyTest {

    @Test
    @DisplayName("Regular behaviour")
    void regularBehaviour() {
        Technology tec = new Technology(0L, "java", "this one");

        assertAll(
                () -> assertEquals(0L, tec.getId()),
                () -> assertEquals("java", tec.getName()),
                () -> assertEquals("this one", tec.getDescription())
        );
    }
    @Test
    @DisplayName("testing setters")
    void settersBehaviour() {
        Technology tec = new Technology(0L, "java", "this one");
        tec.setDescription("this has changed");

        assertAll(
                () -> assertEquals(0L, tec.getId()),
                () -> assertEquals("java", tec.getName()),
                () -> assertEquals("this has changed", tec.getDescription())
        );
    }

    @Test
    @DisplayName("Should fail on empty name")
    void emptyNameException() {
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Technology(0L, "", "this one"));

        assertEquals("NAME", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on empty description")
    void emptyDescriptionException() {
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new Technology(0L, "Java", ""));

        assertEquals("DESCRIPTION", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on edit description longer than 90")
    void longerAfterEditingDescriptionException() {
        Technology tec = new Technology(0L, "java", "this one");

        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> tec.setDescription("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901"));

        assertEquals("DESCRIPTION", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on edit description empty")
    void emptyAfterEditingDescriptionException() {
        Technology tec = new Technology(0L, "java", "this one");

        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> tec.setDescription(""));

        assertEquals("DESCRIPTION", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on name longer than 50 chars")
    void longNameException() {
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Technology(0L, "123456789012345678901234567890123456789012345678901", "this one"));

        assertEquals("NAME", exception.getMessage());
    }
    @Test
    @DisplayName("Should fail on description longer than 90")
    void longDescriptionException() {
        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new Technology(0L, "Java", "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901"));

        assertEquals("DESCRIPTION", exception.getMessage());
    }
}