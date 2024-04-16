package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void saveTechnology() {
        Technology tech = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        TechnologyEntity technologyEntity = new TechnologyEntity(1L, "some name", "some description", Collections.emptyList());

        when(technologyEntityMapper.toEntity(any(Technology.class))).thenReturn(technologyEntity);

        technologyAdapter.saveTechnology(tech);

        assertAll(
                () -> verify(technologyEntityMapper, times(1)).toEntity(any(Technology.class)),
                () -> verify(technologyRepository, times(1)).save(any(TechnologyEntity.class))
        );
    }

    @Test
    @DisplayName("Should get a technology correctly by name")
    void getTechnology() {
        TechnologyEntity technologyEntity = TestDataDriven.getTechnologyEntity(1L);
        Technology retrievedTechnology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyRepository.findByName(anyString())).thenReturn(Optional.of(technologyEntity));
        when(technologyEntityMapper.toModel(any(TechnologyEntity.class))).thenReturn(retrievedTechnology);

        Technology found = technologyAdapter.getTechnology("some name").orElseThrow();

        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.TECHNOLOGY, 1), found.getName()),
                () -> assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.TECHNOLOGY, 1), found.getDescription()),
                () -> verify(technologyRepository, times(1)).findByName(anyString()),
                () -> verify(technologyEntityMapper, times(1)).toModel(any(TechnologyEntity.class))
        );
    }

    @Test
    @DisplayName("Should get a technology correctly by id")
    void getTechnologyById() {
        TechnologyEntity technologyEntity = TestDataDriven.getTechnologyEntity(1L);
        Technology retrievedTechnology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.of(technologyEntity));
        when(technologyEntityMapper.toModel(any(TechnologyEntity.class))).thenReturn(retrievedTechnology);

        Technology found = technologyAdapter.getTechnologyById(1L).orElseThrow();

        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(TestDataDomain.getValidName(TestDataDomain.Element.TECHNOLOGY, 1), found.getName()),
                () -> assertEquals(TestDataDomain.getValidDescription(TestDataDomain.Element.TECHNOLOGY, 1), found.getDescription()),
                () -> verify(technologyRepository, times(1)).findById(anyLong()),
                () -> verify(technologyEntityMapper, times(1)).toModel(any(TechnologyEntity.class))
        );
    }

    @Test
    void getAllTechnologies() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        List<TechnologyEntity> technologyEntities = TestDataDriven.getListOfTechnologyEntity(2);
        List<Technology> technologies = TestDataDomain.getListOfValidTechnologies(2);

        when(technologyRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(technologyEntities));
        when(technologyEntityMapper.toModelList(anyList())).thenReturn(technologies);

        List<Technology> found = technologyAdapter.getAllTechnologies(page, size, isAscending);

        assertAll(
                () -> assertEquals(technologies.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), technologies.get(i).getId());
                        assertEquals(found.get(i).getName(), technologies.get(i).getName());
                        assertEquals(found.get(i).getDescription(), technologies.get(i).getDescription());
                    }
                },
                () -> verify(technologyRepository, times(1)).findAll(any(Pageable.class)),
                () -> verify(technologyEntityMapper, times(1)).toModelList(anyList())
        );
    }

    @Test
    void updateTechnology() {
        Technology technology = TestDataDomain.getTechnology(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        TechnologyEntity technologyEntity = TestDataDriven.getTechnologyEntity(1L);

        when(technologyEntityMapper.toEntity(any(Technology.class))).thenReturn(technologyEntity);
        when(technologyRepository.save(any(TechnologyEntity.class))).thenReturn(technologyEntity);
        when(technologyEntityMapper.toModel(any(TechnologyEntity.class))).thenReturn(technology);

        Technology updated = technologyAdapter.updateTechnology(technology);

        assertAll(
                () -> assertEquals(technology.getId(), updated.getId()),
                () -> assertEquals(technology.getName(), updated.getName()),
                () -> assertEquals(technology.getDescription(), updated.getDescription()),
                () -> verify(technologyEntityMapper, times(1)).toEntity(technology),
                () -> verify(technologyRepository, times(1)).save(technologyEntity),
                () -> verify(technologyEntityMapper, times(1)).toModel(technologyEntity)
        );
    }

    @Test
    void deleteTechnology() {
        TechnologyEntity technologyEntity = TestDataDriven.getTechnologyEntity(1L);

        when(technologyRepository.findById(anyLong())).thenReturn(Optional.of(technologyEntity));

        technologyAdapter.deleteTechnology(1L);

        verify(technologyRepository, times(1)).deleteById(anyLong());
    }
}
