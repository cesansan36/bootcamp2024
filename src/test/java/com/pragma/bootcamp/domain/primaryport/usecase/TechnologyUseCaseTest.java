package com.pragma.bootcamp.domain.primaryport.usecase;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {

    @InjectMocks
    private TechnologyUseCase technologyUseCase;

    @Mock
    ITechnologyPersistencePort technologyPersistencePort;

    @Test
    @DisplayName("Successfully save")
    void saveTechnology() {
        Technology tec = TestDataDomain.getTechnology(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        technologyUseCase.saveTechnology(tec);

        verify(technologyPersistencePort, times(1)).saveTechnology(tec);
    }

    @Test
    @DisplayName("Fail save because technology already exists")
    void failSaveTechnology() {
        Technology tec = TestDataDomain.getTechnology(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        technologyUseCase.saveTechnology(tec);

        verify(technologyPersistencePort, times(1)).saveTechnology(tec);
    }

    @Test
    @DisplayName("Successfully get")
    void getTechnology() {
        Technology tec = TestDataDomain.getTechnology(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
//        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(tec);
//
//        Technology found = technologyUseCase.getTechnology("some name");
//        assertAll(
//                () -> assertEquals(tec.getId(), found.getId()),
//                () -> assertEquals(tec.getName(), found.getName()),
//                () -> assertEquals(tec.getDescription(), found.getDescription()),
//                () -> verify(technologyPersistencePort, times(1)).getTechnology("some name")
//        );
    }
    @Test
    @DisplayName("Fail get because technology does not exists")
    void failGetTechnology() {
        Technology tec = TestDataDomain.getTechnology(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
//        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(tec);
//
//        Technology found = technologyUseCase.getTechnology("some name");
//        assertAll(
//                () -> assertEquals(tec.getId(), found.getId()),
//                () -> assertEquals(tec.getName(), found.getName()),
//                () -> assertEquals(tec.getDescription(), found.getDescription()),
//                () -> verify(technologyPersistencePort, times(1)).getTechnology("some name")
//        );
    }

    @Test
    @DisplayName("Get list of technologies")
    void getAllTechnologies() {
        List<Technology> techs = TestDataDomain.getListOfValidTechnologies(2);

        when(technologyPersistencePort.getAllTechnologies(anyInt(), anyInt(), anyBoolean())).thenReturn(techs);

        List<Technology> founds = technologyUseCase.getAllTechnologies(1,2,true);

        assertAll(
                () -> {
                    for (var i = 0 ; i < founds.size(); i++) {
                        assertEquals(techs.get(i).getId(), founds.get(i).getId());
                        assertEquals(techs.get(i).getName(), founds.get(i).getName());
                        assertEquals(techs.get(i).getDescription(), founds.get(i).getDescription());
                    }
                },
                () -> verify(technologyPersistencePort, times(1)).getAllTechnologies(anyInt(), anyInt(), anyBoolean())
        );
    }

    @Test
    @DisplayName("Successfully update")
    void updateTechnology() {
        Technology sendTech = TestDataDomain.getTechnology(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        Technology receivedTech = TestDataDomain.getTechnology(0L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        when(technologyPersistencePort.updateTechnology(sendTech)).thenReturn(receivedTech);

        Technology found = technologyUseCase.updateTechnology(sendTech);

        assertAll(
                () -> assertEquals(receivedTech.getId(), found.getId()),
                () -> assertEquals(receivedTech.getName(), found.getName()),
                () -> assertEquals(receivedTech.getDescription(), found.getDescription()),
                () -> verify(technologyPersistencePort, times(1)).updateTechnology(sendTech)
        );
    }
    @Test
    @DisplayName("Fail update because technology does not exists")
    void failUpdateTechnology() {
    }

    @Test
    @DisplayName("Successfully delete")
    void deleteTechnology() {
        Long id = 1L;
        technologyUseCase.deleteTechnology(id);

        verify(technologyPersistencePort, times(1)).deleteTechnology(id);
    }

    @Test
    @DisplayName("Fail delete because technology does not exists")
    void failDeleteTechnology() {
    }
}
