package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.testdata.TestDataDomain;
import com.pragma.bootcamp.testdata.TestDataDriven;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
        Technology tech = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyRepository.findByName(anyString())).thenReturn(Optional.empty());

        technologyAdapter.saveTechnology(tech);

        assertAll(
                () -> verify(technologyRepository, times(1)).findByName(anyString()),
                () -> verify(technologyEntityMapper, times(1)).toEntity(tech),
                () -> verify(technologyRepository, times(1)).save(any())
        );
    }
    @Test
    @DisplayName("Should throw exception because technology already exists")
    void saveTechnologyException() {
        Technology tech = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyRepository.findByName(anyString())).thenReturn(Optional.of(new TechnologyEntity()));

        assertThrows(RegistryAlreadyExistsException.class, () -> technologyAdapter.saveTechnology(tech));
    }

    @Test
    @DisplayName("Should get a technology correctly")
    void getTechnologySuccess() {
        Long techEntityId = 1L;
        String techEntityName = TestDataDomain.getValidName(1);
        String techEntityDescription = TestDataDomain.getValidDescription(1);
        TechnologyEntity technologyEntity = new TechnologyEntity();
        technologyEntity.setId(techEntityId);
        technologyEntity.setName(techEntityName);
        technologyEntity.setDescription(techEntityDescription);
        Technology retrieved = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyRepository.findByName(anyString())).thenReturn(Optional.of(technologyEntity));
        when(technologyEntityMapper.toModel(technologyEntity)).thenReturn(retrieved);

//        Technology found = technologyAdapter.getTechnology(techEntityName);
//
//        assertAll(
//                () -> assertEquals(techEntityId, found.getId()),
//                () -> assertEquals(techEntityName, found.getName()),
//                () -> assertEquals(techEntityDescription, found.getDescription())
//        );
    }

    @Test
    @DisplayName("Should throw exception since technology doesn't exists")
    void getTechnologyException() {
        String techName = "Java";

        when(technologyRepository.findByNameContaining(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyAdapter.getTechnology(techName));
    }

    @Test
    void getAllTechnologiesSuccess() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        List<TechnologyEntity> technologyEntities = TestDataDriven.getListOfTechnologyEntity(2);
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(2);

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
        Technology technology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        TechnologyEntity technologyEntity = TestDataDriven.getTechnologyEntity(1L);

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
        Technology technology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyAdapter.updateTechnology(technology));
    }

    @Test
    void deleteTechnologySuccess() {
        Long idToDelete = 1L;
        TechnologyEntity technologyEntity = TestDataDriven.getTechnologyEntity(1L);

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.of(technologyEntity));

        technologyAdapter.deleteTechnology(idToDelete);

        verify(technologyRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteTechnologyException() {
        Long idToDelete = 1L;

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> technologyAdapter.deleteTechnology(idToDelete));
    }
}
