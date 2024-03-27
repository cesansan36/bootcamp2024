package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.testdata.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TechnologyAdapterTest {

    private TechnologyAdapter technologyAdapter;
    private ITechnologyRepository technologyRepository;
    private ITechnologyEntityMapper technologyEntityMapper;

    @BeforeEach
    void SetUp() {
        technologyRepository = mock(ITechnologyRepository.class);
        technologyEntityMapper = mock(ITechnologyEntityMapper.class);

        technologyAdapter = new TechnologyAdapter(technologyRepository, technologyEntityMapper);
    }

    @Test
    @DisplayName("Should save technology correctly")
    void saveTechnologySuccess() {
        Technology tech = TestData.getTestTechnology1();

        when(technologyRepository.findByName("Java")).thenReturn(Optional.empty());

        technologyAdapter.saveTechnology(tech);

        assertAll(
                () -> verify(technologyRepository, times(1)).findByName("Java"),
                () -> verify(technologyEntityMapper, times(1)).toEntity(tech),
                () -> verify(technologyRepository, times(1)).save(any())
        );
    }
    @Test
    @DisplayName("Should throw exception because technology already exists")
    void saveTechnologyException() {
        Technology tech = TestData.getTestTechnology1();

        when(technologyRepository.findByName("Java")).thenReturn(Optional.of(new TechnologyEntity()));

        assertThrows(TechnologyAlreadyExistException.class, () -> technologyAdapter.saveTechnology(tech));
    }

    @Test
    @DisplayName("Should get a technology correctly")
    void getTechnologySuccess() {
        TechnologyEntity technologyEntity = TestData.getTestTechnologyEntity1();

        when(technologyRepository.findByNameContaining(TestData.TECHNOLOGY_NAME_1)).thenReturn(Optional.of(technologyEntity));
        when(technologyEntityMapper.toModel(technologyEntity)).thenReturn(TestData.getTestTechnology1());

        Technology found = technologyAdapter.getTechnology(TestData.TECHNOLOGY_NAME_1);

        assertAll(
                () -> assertEquals(TestData.TECHNOLOGY_ID_1, found.getId()),
                () -> assertEquals(TestData.TECHNOLOGY_NAME_1, found.getName()),
                () -> assertEquals(TestData.TECHNOLOGY_DESCRIPTION_1, found.getDescription())
        );
    }

    @Test
    @DisplayName("Should throw exception since technology doesn't exists")
    void getTechnologyException() {
        String techName = TestData.TECHNOLOGY_NAME_1;

        when(technologyRepository.findByNameContaining(techName)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyAdapter.getTechnology(techName));
    }

    @Test
    void getAllTechnologiesSuccess() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        List<TechnologyEntity> technologyEntities = new ArrayList<>();
        technologyEntities.add(TestData.getTestTechnologyEntity1());
        technologyEntities.add(TestData.getTestTechnologyEntity2());
        List<Technology> technologies = new ArrayList<>();
        technologies.add(TestData.getTestTechnology1());
        technologies.add(TestData.getTestTechnology2());

        when(technologyRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(technologyEntities));
        when(technologyEntityMapper.toModelList(technologyEntities)).thenReturn(technologies);

        List<Technology> found = technologyAdapter.getAllTechnologies(page, size, isAscending);

        assertAll(
                () -> assertEquals(technologies.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), technologies.get(i).getId());
                        assertEquals(found.get(i).getName(), technologies.get(i).getName());
                        assertEquals(found.get(i).getDescription(), technologies.get(i).getDescription());
                    }
                }
        );
    }

    @Test
    void getAllTechnologiesException() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;

        when(technologyRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThrows(NoDataFoundException.class, () -> technologyAdapter.getAllTechnologies(page, size, isAscending));
    }

    @Test
    void updateTechnologySuccess() {
        Technology technology = TestData.getTestTechnology1();
        TechnologyEntity technologyEntity = TestData.getTestTechnologyEntity1();

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.of(technologyEntity));
        when(technologyEntityMapper.toEntity(technology)).thenReturn(technologyEntity);
        when(technologyRepository.save(technologyEntity)).thenReturn(technologyEntity);
        when(technologyEntityMapper.toModel(technologyEntity)).thenReturn(technology);

        Technology updated = technologyAdapter.updateTechnology(technology);

        assertAll(
                () -> assertEquals(technology.getId(), updated.getId()),
                () -> assertEquals(technology.getName(), updated.getName()),
                () -> assertEquals(technology.getDescription(), updated.getDescription()),
                () -> verify(technologyRepository, times(1)).findById(anyLong()),
                () -> verify(technologyEntityMapper, times(1)).toEntity(technology),
                () -> verify(technologyRepository, times(1)).save(technologyEntity),
                () -> verify(technologyEntityMapper, times(1)).toModel(technologyEntity)
        );
    }

    @Test
    void updateTechnologyException() {
        Technology technology = TestData.getTestTechnology1();

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyAdapter.updateTechnology(technology));
    }

    @Test
    void deleteTechnologySuccess() {
        Long idToDelete = TestData.TECHNOLOGY_ID_1;
        TechnologyEntity technologyEntity = TestData.getTestTechnologyEntity1();

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.of(technologyEntity));

        technologyAdapter.deleteTechnology(idToDelete);

        verify(technologyRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteTechnologyException() {
        Long idToDelete = TestData.TECHNOLOGY_ID_1;

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyAdapter.deleteTechnology(idToDelete));
    }
}