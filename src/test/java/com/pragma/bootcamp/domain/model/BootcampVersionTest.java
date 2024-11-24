package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.DateFinishBeforeStartException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import com.pragma.bootcamp.domain.exception.QuantityAboveRequiredException;
import com.pragma.bootcamp.domain.exception.QuantityBelowRequiredException;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BootcampVersionTest {

    @Test
    @DisplayName("Regular behaviour")
    void regularBehaviour() {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);
        BootcampVersion bootcampVersion = new BootcampVersion(1L, "Version 1", 100, LocalDate.now(), LocalDate.now().plusDays(30), bootcamp);

        assertAll(
                () -> assertEquals(1L, bootcampVersion.getId()),
                () -> assertEquals("Version 1", bootcampVersion.getName()),
                () -> assertEquals(100, bootcampVersion.getMaxParticipants()),
                () -> assertEquals(LocalDate.now(), bootcampVersion.getStartDate()),
                () -> assertEquals(LocalDate.now().plusDays(30), bootcampVersion.getEndDate()),
                () -> assertEquals(bootcamp, bootcampVersion.getBootcamp()),
                () -> assertEquals(bootcamp.getCapabilities(), bootcampVersion.getBootcamp().getCapabilities()),
                () -> assertEquals(4, bootcampVersion.getBootcamp().getCapabilities().size()),
                () -> assertEquals(bootcamp.getCapabilities().getFirst(), bootcampVersion.getBootcamp().getCapabilities().getFirst()),
                () -> assertEquals(bootcamp.getCapabilities().get(1).getId(), bootcampVersion.getBootcamp().getCapabilities().get(1).getId()),
                () -> assertEquals(bootcamp.getCapabilities().get(2).getName(), bootcampVersion.getBootcamp().getCapabilities().get(2).getName()),
                () -> assertEquals(bootcamp.getCapabilities().get(3).getDescription(), bootcampVersion.getBootcamp().getCapabilities().get(3).getDescription())
        );
    }

    @Test
    @DisplayName("Should fail on name longer than 50 chars")
    void longNameException() {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);

        CharLimitSurpassedException exception = assertThrows(CharLimitSurpassedException.class, () -> new BootcampVersion(1L, "123456789012345678901234567890123456789012345678901", 100, startDate, endDate, bootcamp));

        assertEquals("Field NAME can not have more than 50 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on empty name")
    void emptyNameException() {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);

        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () -> new BootcampVersion(1L, "", 100, startDate, endDate, bootcamp));

        assertEquals("Field NAME can not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail on participants below required")
    void quantityBelowRequiredException() {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);

        assertThrows(QuantityBelowRequiredException.class, () -> new BootcampVersion(1L, "Version 1", 0, startDate, endDate, bootcamp));
    }

    @Test
    @DisplayName("Should fail on participants above permitted")
    void quantityAboveRequiredException() {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);

        assertThrows(QuantityAboveRequiredException.class, () -> new BootcampVersion(1L, "Version 1", 1000, startDate, endDate, bootcamp));
    }

    @Test
    @DisplayName("Should fail on end date before start date")
    void invalidDatesException() {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(1);

        assertThrows(DateFinishBeforeStartException.class, () -> new BootcampVersion(1L, "Version 1", 100, startDate, endDate, bootcamp));
    }
}
