package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.secondaryport.ICapacityPersistencePort;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CapacityUseCaseTest {

    private CapacityUseCase capacityUseCase;

    ICapacityPersistencePort capacityPersistencePort;

    @BeforeEach
    void setUp() {
        capacityPersistencePort = mock(ICapacityPersistencePort.class);
        capacityUseCase = new CapacityUseCase(capacityPersistencePort);
    }

    @Test
    void saveCapacity() {
        Capacity cap = TestDataDomain.getCapacityWithNoTechnologies(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        capacityUseCase.saveCapacity(cap);

        verify(capacityPersistencePort, times(1)).saveCapacity(cap);
    }

    @Test
    void getCapacity() {
        Capacity cap = TestDataDomain.getCapacityWithNoTechnologies(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        when(capacityPersistencePort.getCapacity(anyString())).thenReturn(cap);

        Capacity found = capacityUseCase.getCapacity("some name");
        assertAll(
                () -> assertEquals(cap.getId(), found.getId()),
                () -> assertEquals(cap.getName(), found.getName()),
                () -> assertEquals(cap.getDescription(), found.getDescription()),
                () -> verify(capacityPersistencePort, times(1)).getCapacity("some name")
        );
    }

    @Test
    void getAllCapacities() {
        List<Capacity> caps = TestDataDomain.getListOfValidCapacities(2);

        when(capacityPersistencePort.getAllCapacities(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(caps);

        List<Capacity> founds = capacityUseCase.getAllCapacities(1,2,true, true);

        assertAll(
                () -> {
                    for (var i = 0 ; i < founds.size(); i++) {
                        assertEquals(caps.get(i).getId(), founds.get(i).getId());
                        assertEquals(caps.get(i).getName(), founds.get(i).getName());
                        assertEquals(caps.get(i).getDescription(), founds.get(i).getDescription());
                    }
                },
                () -> verify(capacityPersistencePort, times(1)).getAllCapacities(anyInt(), anyInt(), anyBoolean(), anyBoolean())
        );
    }
}