package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.secondaryport.IBootcampPersistencePort;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BootcampUseCaseTest {

    private BootcampUseCase bootcampUseCase;

    IBootcampPersistencePort bootcampPersistencePort;

    @BeforeEach
    void setUp() {
        bootcampPersistencePort = mock(IBootcampPersistencePort.class);
        bootcampUseCase = new BootcampUseCase(bootcampPersistencePort);
    }

    @Test
    void saveBootcamp() {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapabilities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        bootcampUseCase.saveBootcamp(bootcamp);

        verify(bootcampPersistencePort, times(1)).saveBootcamp(bootcamp);
    }

    @Test
    void getBootcamp() {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapabilities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(bootcamp);

        Bootcamp found = bootcampUseCase.getBootcamp("some name");
        assertAll(
                () -> assertEquals(bootcamp.getId(), found.getId()),
                () -> assertEquals(bootcamp.getName(), found.getName()),
                () -> assertEquals(bootcamp.getDescription(), found.getDescription()),
                () -> verify(bootcampPersistencePort, times(1)).getBootcamp("some name")
        );
    }

    @Test
    void getAllBootcamps() {
        List<Bootcamp> bootcamps = TestDataDomain.getListOfValidBootcamps(2);

        when(bootcampPersistencePort.getAllBootcamps(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(bootcamps);

        List<Bootcamp> founds = bootcampUseCase.getAllBootcamps(1,2,true, true);

        assertAll(
                () -> {
                    for (var i = 0 ; i < founds.size(); i++) {
                        assertEquals(bootcamps.get(i).getId(), founds.get(i).getId());
                        assertEquals(bootcamps.get(i).getName(), founds.get(i).getName());
                        assertEquals(bootcamps.get(i).getDescription(), founds.get(i).getDescription());
                    }
                },
                () -> verify(bootcampPersistencePort, times(1)).getAllBootcamps(anyInt(), anyInt(), anyBoolean(), anyBoolean())
        );
    }
}