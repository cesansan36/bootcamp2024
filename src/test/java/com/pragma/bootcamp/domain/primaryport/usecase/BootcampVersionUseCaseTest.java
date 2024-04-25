package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.exception.ElementNotFoundException;
import com.pragma.bootcamp.domain.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.secondaryport.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.IBootcampVersionPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.IUserValidationPort;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BootcampVersionUseCaseTest {

    private BootcampVersionUseCase bootcampVersionUseCase;
    private IBootcampVersionPersistencePort bootcampVersionPersistencePort;
    private IBootcampPersistencePort bootcampPersistencePort;
    IUserValidationPort userValidationPort;
    @BeforeEach
    void setUp() {
        bootcampVersionPersistencePort = mock(IBootcampVersionPersistencePort.class);
        bootcampPersistencePort = mock(IBootcampPersistencePort.class);
        userValidationPort = mock(IUserValidationPort.class);
        bootcampVersionUseCase = new BootcampVersionUseCase(bootcampVersionPersistencePort, bootcampPersistencePort, userValidationPort);
    }

    @Test
    @DisplayName("Verify user")
    void verifyUser() {
        String token = "token";
        bootcampVersionUseCase.verifyUser(token);
        verify(userValidationPort, times(1)).validateRestricted(anyString());
    }

    @Test
    void saveBootcampVersionSuccess() {
        BootcampVersion sentBootcampVersion = TestDataDomain.getBootcampVersion(1L, TestDataDomain.DataCase.VALID, 3, 4, 4);
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 3, 4);

        when(bootcampVersionPersistencePort.getBootcampVersion(anyString())).thenReturn(Optional.empty());
        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.of(bootcamp));

        bootcampVersionUseCase.saveBootcampVersion(sentBootcampVersion);

        verify(bootcampVersionPersistencePort, times(1)).getBootcampVersion(anyString());
        verify(bootcampPersistencePort, times(1)).getBootcamp(anyString());
    }

    @Test
    void saveBootcampVersionFailVersionAlreadyExists() {
        BootcampVersion sentBootcampVersion = TestDataDomain.getBootcampVersion(1L, TestDataDomain.DataCase.VALID, 3, 4, 4);
        BootcampVersion foundBootcampVersion = TestDataDomain.getBootcampVersion(1L, TestDataDomain.DataCase.VALID, 3, 4, 4);

        when(bootcampVersionPersistencePort.getBootcampVersion(anyString())).thenReturn(Optional.of(foundBootcampVersion));

        assertThrows(RegistryAlreadyExistsException.class, () -> bootcampVersionUseCase.saveBootcampVersion(sentBootcampVersion));

        verify(bootcampVersionPersistencePort, times(1)).getBootcampVersion(anyString());

    }

    @Test
    void saveBootcampVersionFailBootcampNotFound() {
        BootcampVersion sentBootcampVersion = TestDataDomain.getBootcampVersion(1L, TestDataDomain.DataCase.VALID, 3, 4, 4);

        when(bootcampVersionPersistencePort.getBootcampVersion(anyString())).thenReturn(Optional.empty());
        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> bootcampVersionUseCase.saveBootcampVersion(sentBootcampVersion));
        verify(bootcampVersionPersistencePort, times(1)).getBootcampVersion(anyString());
        verify(bootcampPersistencePort, times(1)).getBootcamp(anyString());
    }

    @Test
    void getAllBootcampVersion() {
        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(3, 4, 4, 4);

        when(bootcampVersionPersistencePort.getAllBootcampVersion(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class))).thenReturn(bootcampVersions);

        List<BootcampVersion> found = bootcampVersionUseCase.getAllBootcampVersion(0, 5, false, Constants.SortingField.MAX_PARTICIPANTS);

        assertAll(
                () -> assertEquals(3, found.size()),
                () -> verify(bootcampVersionPersistencePort, times(1)).getAllBootcampVersion(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class)),
                () -> {
                    for (var i = 0 ; i < found.size(); i++) {
                        assertEquals(bootcampVersions.get(i).getName(), found.get(i).getName());
                        assertEquals(bootcampVersions.get(i).getMaxParticipants(), found.get(i).getMaxParticipants());
                        assertEquals(bootcampVersions.get(i).getStartDate(), found.get(i).getStartDate());
                        assertEquals(bootcampVersions.get(i).getEndDate(), found.get(i).getEndDate());
                        assertEquals(bootcampVersions.get(i).getBootcamp(), found.get(i).getBootcamp());
                    }
                });
    }

    @Test
    void getVersionsOfBootcampSuccess() {
        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(3, 4, 4, 4);
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 3, 4);

        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.of(bootcamp));
        when(bootcampVersionPersistencePort.getVersionsOfBootcamp(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class), anyLong())).thenReturn(bootcampVersions);

        List<BootcampVersion> found = bootcampVersionUseCase.getVersionsOfBootcamp(0, 5, false, Constants.SortingField.MAX_PARTICIPANTS, "some bootcamp");

        assertAll(
                () -> assertEquals(3, found.size()),
                () -> verify(bootcampVersionPersistencePort, times(1)).getVersionsOfBootcamp(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class), anyLong()),
                () -> {
                    for (var i = 0 ; i < found.size(); i++) {
                        assertEquals(bootcampVersions.get(i).getName(), found.get(i).getName());
                        assertEquals(bootcampVersions.get(i).getMaxParticipants(), found.get(i).getMaxParticipants());
                        assertEquals(bootcampVersions.get(i).getStartDate(), found.get(i).getStartDate());
                        assertEquals(bootcampVersions.get(i).getEndDate(), found.get(i).getEndDate());
                        assertEquals(bootcampVersions.get(i).getBootcamp(), found.get(i).getBootcamp());
                    }
                });
    }

    @Test
    void getVersionsOfBootcampFailNotFound() {
        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> bootcampVersionUseCase.getVersionsOfBootcamp(0, 5, false, Constants.SortingField.MAX_PARTICIPANTS, "some bootcamp"));
    }
}
