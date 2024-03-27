package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.secondaryport.ITechnologyPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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
    void saveTechnology() {
        Technology tec = new Technology(7L, "C++", "between C and C#");
        technologyUseCase.saveTechnology(tec);

        verify(technologyPersistencePort, times(1)).saveTechnology(tec);
    }

    @Test
    void getTechnology() {
        Technology tec = new Technology(7L, "C++", "between C and C#");
        when(technologyPersistencePort.getTechnology(anyString())).thenReturn(tec);

        Technology found = technologyUseCase.getTechnology("some name");
        assertAll(
                () -> assertEquals(7L, found.getId()),
                () -> assertEquals("C++", found.getName()),
                () -> assertEquals("between C and C#", found.getDescription()),
                () -> verify(technologyPersistencePort, times(1)).getTechnology("some name")
        );
    }

    @Test
    void getAllTechnologies() {
        Technology tec1 = new Technology(7L, "C++", "between C and C#");
        Technology tec2 = new Technology(9L, "assembler", "bits");
        List<Technology> techs = Arrays.asList(tec1, tec2);

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
    void updateTechnology() {
        Technology tec = new Technology(7L, "C++", "between C and C#");
        when(technologyPersistencePort.updateTechnology(tec)).thenReturn(tec);

        Technology found = technologyUseCase.updateTechnology(tec);
        assertAll(
                () -> assertEquals(7L, found.getId()),
                () -> assertEquals("C++", found.getName()),
                () -> assertEquals("between C and C#", found.getDescription()),
                () -> verify(technologyPersistencePort, times(1)).updateTechnology(tec)
        );
    }

    @Test
    void deleteTechnology() {
        Long id = 1L;
        technologyUseCase.deleteTechnology(id);

        verify(technologyPersistencePort, times(1)).deleteTechnology(id);
    }
}