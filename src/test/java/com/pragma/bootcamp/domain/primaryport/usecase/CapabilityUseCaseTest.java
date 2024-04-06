package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CapabilityUseCaseTest {

    private CapabilityUseCase capabilityUseCase;

    ICapabilityPersistencePort capabilityPersistencePort;

    @BeforeEach
    void setUp() {
        capabilityPersistencePort = mock(ICapabilityPersistencePort.class);
        capabilityUseCase = new CapabilityUseCase(capabilityPersistencePort);
    }

    @Test
    void saveCapability() {
        Capability cap = TestDataDomain.getCapabilityWithNoTechnologies(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        capabilityUseCase.saveCapability(cap);

        verify(capabilityPersistencePort, times(1)).saveCapability(cap);
    }

    @Test
    void getCapability() {
        Capability cap = TestDataDomain.getCapabilityWithNoTechnologies(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        when(capabilityPersistencePort.getCapability(anyString())).thenReturn(cap);

        Capability found = capabilityUseCase.getCapability("some name");
        assertAll(
                () -> assertEquals(cap.getId(), found.getId()),
                () -> assertEquals(cap.getName(), found.getName()),
                () -> assertEquals(cap.getDescription(), found.getDescription()),
                () -> verify(capabilityPersistencePort, times(1)).getCapability("some name")
        );
    }

    @Test
    void getAllCapabilities() {
        List<Capability> caps = TestDataDomain.getListOfValidCapabilities(2);

        when(capabilityPersistencePort.getAllCapabilities(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(caps);

        List<Capability> founds = capabilityUseCase.getAllCapabilities(1,2,true, true);

        assertAll(
                () -> {
                    for (var i = 0 ; i < founds.size(); i++) {
                        assertEquals(caps.get(i).getId(), founds.get(i).getId());
                        assertEquals(caps.get(i).getName(), founds.get(i).getName());
                        assertEquals(caps.get(i).getDescription(), founds.get(i).getDescription());
                    }
                },
                () -> verify(capabilityPersistencePort, times(1)).getAllCapabilities(anyInt(), anyInt(), anyBoolean(), anyBoolean())
        );
    }
}