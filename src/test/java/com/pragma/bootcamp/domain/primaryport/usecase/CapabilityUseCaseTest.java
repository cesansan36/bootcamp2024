package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.exception.ElementNotFoundException;
import com.pragma.bootcamp.domain.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.ITechnologyPersistencePort;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CapabilityUseCaseTest {

    private CapabilityUseCase capabilityUseCase;

    ICapabilityPersistencePort capabilityPersistencePort;
    ITechnologyPersistencePort technologyPersistencePort;

    @BeforeEach
    void setUp() {
        capabilityPersistencePort = mock(ICapabilityPersistencePort.class);
        technologyPersistencePort = mock(ITechnologyPersistencePort.class);
        capabilityUseCase = new CapabilityUseCase(capabilityPersistencePort, technologyPersistencePort);
    }

    @Test
    void saveCapabilitySuccess() {
        int technologyAmount = 4;
        Capability sentCapability = TestDataDomain.getCapability(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, technologyAmount);
        List<Technology> foundTechnologies = TestDataDomain.getListOfValidTechnologies(technologyAmount);

        when(capabilityPersistencePort.getCapability(anyString())).thenReturn(Optional.empty());
        when(technologyPersistencePort.getTechnology(anyString())).thenAnswer(new Answer<Optional<Technology>>() {
                    private int count = 0;
                    @Override
                    public Optional<Technology> answer(InvocationOnMock invocation) {
                        if (count < foundTechnologies.size()) {
                            return Optional.of(foundTechnologies.get(count++));
                        }
                        return Optional.empty();
                    }
                }
        );

        capabilityUseCase.saveCapability(sentCapability);

        verify(capabilityPersistencePort, times(1)).getCapability(anyString());
        verify(technologyPersistencePort, times(technologyAmount)).getTechnology(anyString());
        verify(capabilityPersistencePort, times(1)).saveCapability(any(Capability.class));
    }

    @Test
    void saveCapabilityFailureCapabilityAlreadyExists() {
        int technologyAmount = 4;
        Capability sentCapability = TestDataDomain.getCapability(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, technologyAmount);
        Capability foundCapability = TestDataDomain.getCapability(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, technologyAmount);

        when(capabilityPersistencePort.getCapability(anyString())).thenReturn(Optional.of(foundCapability));

        assertThrows(RegistryAlreadyExistsException.class, () -> capabilityUseCase.saveCapability(sentCapability));

        verify(capabilityPersistencePort, times(1)).getCapability(anyString());
    }

    @Test
    void saveCapabilityFailureTechnologyNotFound() {
        int technologyAmount = 4;
        Capability sentCapability = TestDataDomain.getCapability(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, technologyAmount);

        when(capabilityPersistencePort.getCapability(anyString())).thenReturn(Optional.empty());
        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> capabilityUseCase.saveCapability(sentCapability));

        verify(capabilityPersistencePort, times(1)).getCapability(anyString());
        verify(technologyPersistencePort, times(1)).getTechnology(anyString());
    }

    @Test
    void getCapabilitySuccess() {
        Capability capability = TestDataDomain.getCapability(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4);

        when(capabilityPersistencePort.getCapability(anyString())).thenReturn(Optional.of(capability));

        Capability found = capabilityUseCase.getCapability("some name");
        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.CAPABILITY, 1), found.getName()),
                () -> assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.CAPABILITY, 1), found.getDescription()),
                () -> verify(capabilityPersistencePort, times(1)).getCapability(anyString())
        );
    }

    @Test
    void getCapabilityFailure() {
        when(capabilityPersistencePort.getCapability(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> capabilityUseCase.getCapability("some name"));
        verify(capabilityPersistencePort, times(1)).getCapability(anyString());
    }

    @Test
    void getAllCapabilities() {
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(2, 4);

        when(capabilityPersistencePort.getAllCapabilities(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(capabilities);

        List<Capability> founds = capabilityUseCase.getAllCapabilities(1,2,true, true);

        assertAll(
                () -> {
                    for (var i = 0 ; i < founds.size(); i++) {
                        assertEquals(i + 1, founds.get(i).getId());
                        assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.CAPABILITY, i + 1), founds.get(i).getName());
                        assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.CAPABILITY, i + 1), founds.get(i).getDescription());
                    }
                },
                () -> verify(capabilityPersistencePort, times(1)).getAllCapabilities(anyInt(), anyInt(), anyBoolean(), anyBoolean())
        );
    }
}
