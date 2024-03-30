package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.domain.model.Bootcamp;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BootcampAdapterTest {

    private BootcampAdapter bootcampAdapter;

    private IBootcampRepository bootcampRepository;
    private IBootcampEntityMapper bootcampEntityMapper;

    @BeforeEach
    void setUp() {
        bootcampRepository = mock(IBootcampRepository.class);
        bootcampEntityMapper = mock(IBootcampEntityMapper.class);

        bootcampAdapter = new BootcampAdapter(bootcampRepository, bootcampEntityMapper);
    }

    @Test
    @DisplayName("Should save bootcamp correctly")
    void saveBootcampSuccess() {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapacities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(bootcampRepository.findByName(anyString())).thenReturn(Optional.empty());

        bootcampAdapter.saveBootcamp(bootcamp);

        assertAll(
                () -> verify(bootcampRepository, times(1)).findByName(anyString()),
                () -> verify(bootcampEntityMapper, times(1)).toEntity(bootcamp),
                () -> verify(bootcampRepository, times(1)).save(any())
        );
    }
    @Test
    @DisplayName("Should throw exception because bootcamp already exists")
    void saveBootcampException() {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapacities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(bootcampRepository.findByName(anyString())).thenReturn(Optional.of(new BootcampEntity()));

        assertThrows(RegistryAlreadyExistsException.class, () -> bootcampAdapter.saveBootcamp(bootcamp));
    }

    @Test
    @DisplayName("Should get a bootcamp correctly")
    void getBootcampSuccess() {
        Long bootcampEntityId = 1L;
        String bootcampEntityName = TestDataDomain.getValidName(1);
        String bootcampEntityDescription = TestDataDomain.getValidDescription(1);
        BootcampEntity bootcampEntity = new BootcampEntity();
        bootcampEntity.setId(bootcampEntityId);
        bootcampEntity.setName(bootcampEntityName);
        bootcampEntity.setDescription(bootcampEntityDescription);
        Bootcamp retrieved = TestDataDomain.getBootcampWithNoCapacities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(bootcampRepository.findByName(anyString())).thenReturn(Optional.of(bootcampEntity));
        when(bootcampEntityMapper.toModel(bootcampEntity)).thenReturn(retrieved);

        Bootcamp found = bootcampAdapter.getBootcamp(bootcampEntityName);

        assertAll(
                () -> assertEquals(bootcampEntityId, found.getId()),
                () -> assertEquals(bootcampEntityName, found.getName()),
                () -> assertEquals(bootcampEntityDescription, found.getDescription())
        );
    }
    @Test
    @DisplayName("Should throw exception since bootcamp doesn't exists")
    void getBootcampException() {
        String capName = "Bootcamp 1";

        when(bootcampRepository.findByNameContaining(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> bootcampAdapter.getBootcamp(capName));
    }

    @Test
    @DisplayName("Should get a list og bootcamps correctly")
    void getAllBootcampsSuccess() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        boolean isSortByCapacitiesAmount = true;
        List<BootcampEntity> bootcampEntities = TestDataDriven.getListOfBootcampEntity(2);
        List<Bootcamp> bootcamps = TestDataDomain.getListOfValidBootcamps(2);

        when(bootcampRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(bootcampEntities));
        when(bootcampEntityMapper.toModelList(bootcampEntities)).thenReturn(bootcamps);

        List<Bootcamp> found = bootcampAdapter.getAllBootcamps(page, size, isAscending, isSortByCapacitiesAmount);

        assertAll(
                () -> assertEquals(bootcamps.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), bootcamps.get(i).getId());
                        assertEquals(found.get(i).getName(), bootcamps.get(i).getName());
                        assertEquals(found.get(i).getDescription(), bootcamps.get(i).getDescription());
                    }
                }
        );
    }
    @Test
    @DisplayName("Should throw no data found exception")
    void getAllBootcampsException() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        boolean isSortByCapacitiesAmount = true;

        when(bootcampRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThrows(NoDataFoundException.class, () -> bootcampAdapter.getAllBootcamps(page, size, isAscending, isSortByCapacitiesAmount));
    }
}