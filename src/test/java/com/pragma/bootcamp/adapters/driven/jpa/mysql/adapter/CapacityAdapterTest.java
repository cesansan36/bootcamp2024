package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.model.Capacity;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CapacityAdapterTest {

    private CapacityAdapter capacityAdapter;

    private ICapacityRepository capacityRepository;
    private ICapacityEntityMapper capacityEntityMapper;

    @BeforeEach
    void setUp() {
        capacityRepository = mock(ICapacityRepository.class);
        capacityEntityMapper = mock(ICapacityEntityMapper.class);

        capacityAdapter = new CapacityAdapter(capacityRepository, capacityEntityMapper);
    }

    @Test
    @DisplayName("Should save capacity correctly")
    void saveCapacitySuccess() {
        Capacity capacity = TestDataDomain.getCapacityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(capacityRepository.findByName(anyString())).thenReturn(Optional.empty());

        capacityAdapter.saveCapacity(capacity);

        assertAll(
                () -> verify(capacityRepository, times(1)).findByName(anyString()),
                () -> verify(capacityEntityMapper, times(1)).toEntity(capacity),
                () -> verify(capacityRepository, times(1)).save(any())
        );
    }
    @Test
    @DisplayName("Should throw exception because capacity already exists")
    void saveCapacityException() {
        Capacity capacity = TestDataDomain.getCapacityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(capacityRepository.findByName(anyString())).thenReturn(Optional.of(new CapacityEntity()));

        assertThrows(RegistryAlreadyExistsException.class, () -> capacityAdapter.saveCapacity(capacity));
    }

    @Test
    @DisplayName("Should get a capacity correctly")
    void getCapacitySuccess() {
        Long capEntityId = 1L;
        String capEntityName = TestDataDomain.getValidName(1);
        String capEntityDescription = TestDataDomain.getValidDescription(1);
        CapacityEntity capacityEntity = new CapacityEntity();
        capacityEntity.setId(capEntityId);
        capacityEntity.setName(capEntityName);
        capacityEntity.setDescription(capEntityDescription);
        Capacity retrieved = TestDataDomain.getCapacityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(capacityRepository.findByName(anyString())).thenReturn(Optional.of(capacityEntity));
        when(capacityEntityMapper.toModel(capacityEntity)).thenReturn(retrieved);

        Capacity found = capacityAdapter.getCapacity(capEntityName);

        assertAll(
                () -> assertEquals(capEntityId, found.getId()),
                () -> assertEquals(capEntityName, found.getName()),
                () -> assertEquals(capEntityDescription, found.getDescription())
        );
    }
    @Test
    @DisplayName("Should throw exception since capacity doesn't exists")
    void getCapacityException() {
        String capName = "Cap 1";

        when(capacityRepository.findByNameContaining(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> capacityAdapter.getCapacity(capName));
    }

    @Test
    @DisplayName("Should get a list og capacities correctly")
    void getAllCapacitiesSuccess() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        boolean isSortByTechnologiesAmount = true;
        List<CapacityEntity> capacityEntities = TestDataDriven.getListOfCapacityEntity(2);
        List<Capacity> capacities = TestDataDomain.getListOfValidCapacities(2);

        when(capacityRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(capacityEntities));
        when(capacityEntityMapper.toModelList(capacityEntities)).thenReturn(capacities);

        List<Capacity> found = capacityAdapter.getAllCapacities(page, size, isAscending, isSortByTechnologiesAmount);

        assertAll(
                () -> assertEquals(capacities.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), capacities.get(i).getId());
                        assertEquals(found.get(i).getName(), capacities.get(i).getName());
                        assertEquals(found.get(i).getDescription(), capacities.get(i).getDescription());
                    }
                }
        );
    }
    @Test
    @DisplayName("Should throw no data found exception")
    void getAllCapacitiesException() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        boolean isSortByTechnologiesAmount = true;

        when(capacityRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThrows(NoDataFoundException.class, () -> capacityAdapter.getAllCapacities(page, size, isAscending, isSortByTechnologiesAmount));
    }
}