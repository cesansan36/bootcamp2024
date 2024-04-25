package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.exception.ElementNotFoundException;
import com.pragma.bootcamp.domain.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.secondaryport.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.IUserValidationPort;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

class BootcampUseCaseTest {

    private BootcampUseCase bootcampUseCase;

    IBootcampPersistencePort bootcampPersistencePort;
    ICapabilityPersistencePort capabilityPersistencePort;
    IUserValidationPort userValidationPort;

    @BeforeEach
    void setUp() {
        bootcampPersistencePort = mock(IBootcampPersistencePort.class);
        capabilityPersistencePort = mock(ICapabilityPersistencePort.class);
        userValidationPort = mock(IUserValidationPort.class);
        bootcampUseCase = new BootcampUseCase(bootcampPersistencePort, capabilityPersistencePort, userValidationPort);
    }

    @Test
    @DisplayName("Verify user")
    void verifyUser() {
        String token = "token";
        bootcampUseCase.verifyUser(token);
        verify(userValidationPort, times(1)).validateRestricted(anyString());
    }

    @Test
    void saveBootcampSuccess() {
        int capabilityAmount = 3;
        int technologyAmount = 4;
        Bootcamp sentBootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, capabilityAmount, technologyAmount);
        List<Capability> foundCapabilities = TestDataDomain.getListOfValidCapabilities(capabilityAmount, technologyAmount);

        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.empty());
        when(capabilityPersistencePort.getCapability(anyString())).thenAnswer(new Answer<Optional<Capability>>() {
            private int count = 0;
            @Override
            public Optional<Capability> answer(InvocationOnMock invocation) {
                if (count < foundCapabilities.size()) {
                    return Optional.of(foundCapabilities.get(count++));
                }
                return Optional.empty();
            }
        });

        bootcampUseCase.saveBootcamp(sentBootcamp);

        verify(bootcampPersistencePort, times(1)).getBootcamp(anyString());
        verify(capabilityPersistencePort, times(capabilityAmount)).getCapability(anyString());
        verify(bootcampPersistencePort, times(1)).saveBootcamp(any(Bootcamp.class));
    }

    @Test
    void saveBootcampFailureBootcampAlreadyExists() {
        int capabilityAmount = 3;
        int technologyAmount = 4;
        Bootcamp sentBootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, capabilityAmount, technologyAmount);
        Bootcamp foundBootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, capabilityAmount, technologyAmount);

        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.of(foundBootcamp));

        assertThrows(RegistryAlreadyExistsException.class, () -> bootcampUseCase.saveBootcamp(sentBootcamp));

        verify(bootcampPersistencePort, times(1)).getBootcamp(anyString());
    }

    @Test
    void saveBootcampFailureCapabilityNotFound() {
        int capabilityAmount = 3;
        int technologyAmount = 4;
        Bootcamp sentBootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, capabilityAmount, technologyAmount);

        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.empty());
        when(capabilityPersistencePort.getCapability(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> bootcampUseCase.saveBootcamp(sentBootcamp));

        verify(bootcampPersistencePort, times(1)).getBootcamp(anyString());
        verify(capabilityPersistencePort, times(1)).getCapability(anyString());
    }

    @Test
    void getBootcampSuccess() {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 3, 4);

        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.of(bootcamp));

        Bootcamp found = bootcampUseCase.getBootcamp("some name");
        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.BOOTCAMP, 1), found.getName()),
                () -> assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.BOOTCAMP, 1), found.getDescription()),
                () -> verify(bootcampPersistencePort, times(1)).getBootcamp(anyString())
        );
    }

    @Test
    void getBootcampFailure() {
        when(bootcampPersistencePort.getBootcamp(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> bootcampUseCase.getBootcamp("some name"));
        verify(bootcampPersistencePort, times(1)).getBootcamp(anyString());
    }

    @Test
    void getAllBootcamps() {
        List<Bootcamp> bootcamps = TestDataDomain.getListOfValidBootcamps(4, 4, 4);

        when(bootcampPersistencePort.getAllBootcamps(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(bootcamps);

        List<Bootcamp> founds = bootcampUseCase.getAllBootcamps(1, 10, true, true);

        assertAll(
                () -> {
                    for (var i = 0 ; i < founds.size(); i++) {
                        assertEquals(i + 1, founds.get(i).getId());
                        assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.BOOTCAMP, i + 1), founds.get(i).getName());
                        assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.BOOTCAMP, i + 1), founds.get(i).getDescription());
                    }
                },
                () -> verify(bootcampPersistencePort, times(1)).getAllBootcamps(anyInt(), anyInt(), anyBoolean(), anyBoolean())
        );
    }
}
