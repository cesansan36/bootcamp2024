package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.exception.ElementNotFoundException;
import com.pragma.bootcamp.domain.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.secondaryport.ITechnologyPersistencePort;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {

    @InjectMocks
    private TechnologyUseCase technologyUseCase;

    @Mock
    ITechnologyPersistencePort technologyPersistencePort;

    @Test
    @DisplayName("Successfully save")
    void saveTechnology() {
        Technology technology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(Optional.empty());

        technologyUseCase.saveTechnology(technology);

        verify(technologyPersistencePort, times(1)).getTechnology(anyString());
        verify(technologyPersistencePort, times(1)).saveTechnology(technology);
    }

    @Test
    @DisplayName("Fail save because technology already exists")
    void failSaveTechnology() {
        Technology technology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(Optional.of(technology));

        assertThrows(RegistryAlreadyExistsException.class, () -> technologyUseCase.saveTechnology(technology));
    }

    @Test
    @DisplayName("Successfully get")
    void getTechnology() {
        Technology technology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(Optional.of(technology));

        Technology found = technologyUseCase.getTechnology("some name");

        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.TECHNOLOGY, 1), found.getName()),
                () -> assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.TECHNOLOGY, 1), found.getDescription()),
                () -> verify(technologyPersistencePort, times(1)).getTechnology("some name")
        );
    }

    @Test
    @DisplayName("Fail get because technology does not exists")
    void failGetTechnology() {
        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyUseCase.getTechnology("some name"));
    }

    @Test
    @DisplayName("Get list of technologies")
    void getAllTechnologies() {
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(2);

        when(technologyPersistencePort.getAllTechnologies(anyInt(), anyInt(), anyBoolean())).thenReturn(technologies);

        List<Technology> founds = technologyUseCase.getAllTechnologies(1,2,true);

        assertAll(
                () -> {
                    for (var i = 0 ; i < founds.size(); i++) {
                        assertEquals(i + 1, founds.get(i).getId());
                        assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.TECHNOLOGY, i + 1), founds.get(i).getName());
                        assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.TECHNOLOGY, i + 1), founds.get(i).getDescription());
                    }
                },
                () -> verify(technologyPersistencePort, times(1)).getAllTechnologies(anyInt(), anyInt(), anyBoolean())
        );
    }

    @Test
    @DisplayName("Successfully update")
    void updateTechnology() {
        Technology sendTechnology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        Technology verifiedTechnology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        Technology updatedTechnology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(Optional.of(verifiedTechnology));
        when(technologyPersistencePort.updateTechnology(any(Technology.class))).thenReturn(updatedTechnology);

        Technology found = technologyUseCase.updateTechnology(sendTechnology);

        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.TECHNOLOGY, 1), found.getName()),
                () -> assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.TECHNOLOGY, 1), found.getDescription()),
                () -> verify(technologyPersistencePort, times(1)).getTechnology(anyString()),
                () -> verify(technologyPersistencePort, times(1)).updateTechnology(any(Technology.class))
        );
    }

    @Test
    @DisplayName("Fail update because technology does not exists")
    void failUpdateTechnology() {
        Technology sendTechnology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyUseCase.updateTechnology(sendTechnology));
    }

    @Test
    @DisplayName("Successfully delete")
    void deleteTechnology() {
        Long id = 1L;
        Technology technology = TestDataDomain.getTechnology(id, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyPersistencePort.getTechnologyById(anyLong())).thenReturn(Optional.of(technology));

        technologyUseCase.deleteTechnology(id);

        verify(technologyPersistencePort, times(1)).getTechnologyById(anyLong());
        verify(technologyPersistencePort, times(1)).deleteTechnology(anyLong());
    }

    @Test
    @DisplayName("Fail delete because technology does not exists")
    void failDeleteTechnology() {
        Long id = 1L;
        when(technologyPersistencePort.getTechnologyById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyUseCase.deleteTechnology(id));
    }
}
