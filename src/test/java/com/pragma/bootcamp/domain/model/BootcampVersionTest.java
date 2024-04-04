package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.DateFinishBeforeStartException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import com.pragma.bootcamp.domain.exception.QuantityAboveRequiredException;
import com.pragma.bootcamp.domain.exception.QuantityBelowRequiredException;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BootcampVersionTest {

    @Test
    @DisplayName("Regular behaviour")
    void testRegularBehavior() {
        Long id = 1L;
        String name = "Bootcamp 2024";
        int maxParticipants = 40;
        Date startDate = TestDataDomain.getDateWithOffset(0);
        Date endDate = TestDataDomain.getDateWithOffset(1);;

        BootcampVersion bootcampVersion = new BootcampVersion(id, name, maxParticipants, startDate, endDate);

        assertEquals(id, bootcampVersion.getId());
        assertEquals(name, bootcampVersion.getName());
        assertEquals(maxParticipants, bootcampVersion.getMaxParticipants());
        assertNotNull(bootcampVersion.getStartDate());
        assertNotNull(bootcampVersion.getEndDate());
    }

    @Test
    @DisplayName("Should fail because of empty name")
    void testEmptyNameException() {
        Long id = 1L;
        String name = "";
        int maxParticipants = 40;
        Date startDate = TestDataDomain.getDateWithOffset(0);
        Date endDate = TestDataDomain.getDateWithOffset(1);

        assertThrows(EmptyFieldException.class, () -> new BootcampVersion(id, name, maxParticipants, startDate, endDate));
    }

    @Test
    @DisplayName("Should fail because name is too long")
    void testNameTooLongException() {
        Long id = 1L;
        String name = "qwertyuiopasdfghjklñzxcvbnmqwertyuiopasdfghjklñzxcvbnmqwertyuiopasdfghjklñzxcvbnmqwertyuiopasdfghjklñzxcvbnm";
        int maxParticipants = 40;
        Date startDate = TestDataDomain.getDateWithOffset(0);
        Date endDate = TestDataDomain.getDateWithOffset(1);

        assertThrows(CharLimitSurpassedException.class, () -> new BootcampVersion(id, name, maxParticipants, startDate, endDate));
    }

    @Test
    @DisplayName("Should fail because max participants is bellow the limit")
    void testBelowMinimumParticipantsException() {
        Long id = 1L;
        String name = "Bootcamp 2024";
        int maxParticipants = 0;
        Date startDate = TestDataDomain.getDateWithOffset(0);
        Date endDate = TestDataDomain.getDateWithOffset(1);

        assertThrows(QuantityBelowRequiredException.class, () -> new BootcampVersion(id, name, maxParticipants, startDate, endDate));
    }

    @Test
    @DisplayName("Should fail because max participants is above the limit")
    void testAboveMaximumParticipantsException() {
        Long id = 1L;
        String name = "Bootcamp 2024";
        int maxParticipants = 9999;
        Date startDate = TestDataDomain.getDateWithOffset(0);
        Date endDate = TestDataDomain.getDateWithOffset(1);

        assertThrows(QuantityAboveRequiredException.class, () -> new BootcampVersion(id, name, maxParticipants, startDate, endDate));
    }

    @Test
    @DisplayName("Should fail because the end date is before the start date")
    void testEndDateBeforeStartDateException() {
        Long id = 1L;
        String name = "Bootcamp 2024";
        int maxParticipants = 30;
        Date startDate = TestDataDomain.getDateWithOffset(1);
        Date endDate = TestDataDomain.getDateWithOffset(0);

        assertThrows(DateFinishBeforeStartException.class, () -> new BootcampVersion(id, name, maxParticipants, startDate, endDate));
    }
}