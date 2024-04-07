package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.secondaryport.IBootcampVersionPersistencePort;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BootcampVersionUseCaseTest {

    private BootcampVersionUseCase bootcampVersionUseCase;
    private IBootcampVersionPersistencePort bootcampVersionPersistencePort;
    @BeforeEach
    void setUp() {
        bootcampVersionPersistencePort = mock(IBootcampVersionPersistencePort.class);
        bootcampVersionUseCase = new BootcampVersionUseCase(bootcampVersionPersistencePort);
    }
    @Test
    void saveBootcampVersion() {
        BootcampVersion bootcampVersion = TestDataDomain.getBootcampVersionWithNoBootcamp(1L, TestDataDomain.DataCase.VALID, 3);

        bootcampVersionUseCase.saveBootcampVersion(bootcampVersion);

        verify(bootcampVersionPersistencePort, times(1)).saveBootcampVersion(bootcampVersion);
    }

    @Test
    void getAllBootcampVersion() {
        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(2, 3);

        when(bootcampVersionPersistencePort.getAllBootcampVersion(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class))).thenReturn(bootcampVersions);

        List<BootcampVersion> found = bootcampVersionUseCase.getAllBootcampVersion(1,2,true, Constants.SortingField.MAX_PARTICIPANTS);

        assertAll(
                () -> assertEquals(bootcampVersions.size(), found.size()),
                () -> assertEquals(bootcampVersions.getFirst().getId(), found.getFirst().getId()),
                () -> assertEquals(bootcampVersions.getFirst().getName(), found.getFirst().getName()),
                () -> assertEquals(bootcampVersions.getFirst().getMaxParticipants(), found.getFirst().getMaxParticipants()),
                () -> assertEquals(bootcampVersions.getFirst().getStartDate(), found.getFirst().getStartDate()),
                () -> assertEquals(bootcampVersions.getFirst().getEndDate(), found.getFirst().getEndDate()),
                () -> assertEquals(bootcampVersions.getFirst().getBootcamp().getId(), found.getFirst().getBootcamp().getId()),
                () -> assertEquals(bootcampVersions.get(1).getId(), found.get(1).getId()),
                () -> assertEquals(bootcampVersions.get(1).getName(), found.get(1).getName()),
                () -> assertEquals(bootcampVersions.get(1).getMaxParticipants(), found.get(1).getMaxParticipants()),
                () -> assertEquals(bootcampVersions.get(1).getStartDate(), found.get(1).getStartDate()),
                () -> assertEquals(bootcampVersions.get(1).getEndDate(), found.get(1).getEndDate()),
                () -> assertEquals(bootcampVersions.get(1).getBootcamp().getId(), found.get(1).getBootcamp().getId()),
                () -> verify(bootcampVersionPersistencePort, times(1)).getAllBootcampVersion(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class))
        );
    }

    @Test
    void getVersionsOfBootcamp() {
        // TODO Repair this test
        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(2, 3);
        when(bootcampVersionPersistencePort.getVersionsOfBootcamp(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class), anyString())).thenReturn(bootcampVersions);

        List<BootcampVersion> found = bootcampVersionUseCase.getVersionsOfBootcamp(1,2,true, Constants.SortingField.MAX_PARTICIPANTS, "bootcamp 1");

        assertAll(
                () -> assertEquals(bootcampVersions.size(), found.size()),
                () -> assertEquals(bootcampVersions.getFirst().getId(), found.getFirst().getId()),
                () -> assertEquals(bootcampVersions.getFirst().getName(), found.getFirst().getName()),
                () -> assertEquals(bootcampVersions.getFirst().getMaxParticipants(), found.getFirst().getMaxParticipants()),
                () -> assertEquals(bootcampVersions.getFirst().getStartDate(), found.getFirst().getStartDate()),
                () -> assertEquals(bootcampVersions.getFirst().getEndDate(), found.getFirst().getEndDate()),
                () -> assertEquals(bootcampVersions.getFirst().getBootcamp().getId(), found.getFirst().getBootcamp().getId()),
                () -> assertEquals(bootcampVersions.get(1).getId(), found.get(1).getId()),
                () -> assertEquals(bootcampVersions.get(1).getName(), found.get(1).getName()),
                () -> assertEquals(bootcampVersions.get(1).getMaxParticipants(), found.get(1).getMaxParticipants()),
                () -> assertEquals(bootcampVersions.get(1).getStartDate(), found.get(1).getStartDate()),
                () -> assertEquals(bootcampVersions.get(1).getEndDate(), found.get(1).getEndDate()),
                () -> assertEquals(bootcampVersions.get(1).getBootcamp().getId(), found.get(1).getBootcamp().getId()),
                () -> verify(bootcampVersionPersistencePort, times(1)).getVersionsOfBootcamp(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class), anyString())
        );
    }
}