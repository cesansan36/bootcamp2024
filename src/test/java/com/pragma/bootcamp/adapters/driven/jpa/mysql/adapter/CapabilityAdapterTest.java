package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapabilityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapabilityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.testdata.TestDataDomain;
import com.pragma.bootcamp.testdata.TestDataDriven;
import java.util.Arrays;
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

class CapabilityAdapterTest {

    private CapabilityAdapter capabilityAdapter;

    private ICapabilityRepository capabilityRepository;
    private ICapabilityEntityMapper capabilityEntityMapper;
    private ITechnologyRepository technologyRepository;

    @BeforeEach
    void setUp() {
        capabilityRepository = mock(ICapabilityRepository.class);
        capabilityEntityMapper = mock(ICapabilityEntityMapper.class);
        technologyRepository = mock(ITechnologyRepository.class);

        capabilityAdapter = new CapabilityAdapter(capabilityRepository, capabilityEntityMapper, technologyRepository);
    }

    @Test
    @DisplayName("Should save capability correctly")
    void saveCapabilitySuccess() {
        Capability capability = TestDataDomain.getCapabilityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        CapabilityEntity  capabilityEntity = new CapabilityEntity();
        capabilityEntity.setId(0L);
        capabilityEntity.setName(capability.getName());
        capabilityEntity.setDescription(capability.getDescription());

        TechnologyEntity technologyEntity = new TechnologyEntity();
        technologyEntity.setId(0L);
        technologyEntity.setName(TestDataDomain.getValidName(1));
        technologyEntity.setDescription(TestDataDomain.getValidDescription(1));

        capabilityEntity.setTechnologies(Arrays.asList(technologyEntity, technologyEntity));

        when(capabilityRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(capabilityEntityMapper.toEntity(any(Capability.class))).thenReturn(capabilityEntity);
        when(technologyRepository.findByName(anyString())).thenReturn(Optional.of(technologyEntity));

        capabilityAdapter.saveCapability(capability);

        assertAll(
                () -> verify(capabilityRepository, times(1)).findByName(anyString()),
                () -> verify(capabilityEntityMapper, times(1)).toEntity(capability),
                () -> verify(capabilityRepository, times(1)).save(any())
        );
    }
    @Test
    @DisplayName("Should throw exception because capability already exists")
    void saveCapabilityException() {
        Capability capability = TestDataDomain.getCapabilityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(capabilityRepository.findByName(anyString())).thenReturn(Optional.of(new CapabilityEntity()));

        assertThrows(RegistryAlreadyExistsException.class, () -> capabilityAdapter.saveCapability(capability));
    }

    @Test
    @DisplayName("Should get a capability correctly")
    void getCapabilitySuccess() {
        Long capEntityId = 1L;
        String capEntityName = TestDataDomain.getValidName(1);
        String capEntityDescription = TestDataDomain.getValidDescription(1);
        CapabilityEntity capabilityEntity = new CapabilityEntity();
        capabilityEntity.setId(capEntityId);
        capabilityEntity.setName(capEntityName);
        capabilityEntity.setDescription(capEntityDescription);
        Capability retrieved = TestDataDomain.getCapabilityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);

        when(capabilityRepository.findByName(anyString())).thenReturn(Optional.of(capabilityEntity));
        when(capabilityEntityMapper.toModel(capabilityEntity)).thenReturn(retrieved);

        Capability found = capabilityAdapter.getCapability(capEntityName);

        assertAll(
                () -> assertEquals(capEntityId, found.getId()),
                () -> assertEquals(capEntityName, found.getName()),
                () -> assertEquals(capEntityDescription, found.getDescription())
        );
    }
    @Test
    @DisplayName("Should throw exception since capability doesn't exists")
    void getCapabilityException() {
        String capName = "Cap 1";

        when(capabilityRepository.findByNameContaining(anyString())).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> capabilityAdapter.getCapability(capName));
    }

    @Test
    @DisplayName("Should get a list og capabilities correctly")
    void getAllCapabilitiesSuccess() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        boolean isSortByTechnologiesAmount = true;
        List<CapabilityEntity> capabilityEntities = TestDataDriven.getListOfCapabilityEntity(2);
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(2);

        when(capabilityRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(capabilityEntities));
        when(capabilityEntityMapper.toModelList(capabilityEntities)).thenReturn(capabilities);

        List<Capability> found = capabilityAdapter.getAllCapabilities(page, size, isAscending, isSortByTechnologiesAmount);

        assertAll(
                () -> assertEquals(capabilities.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), capabilities.get(i).getId());
                        assertEquals(found.get(i).getName(), capabilities.get(i).getName());
                        assertEquals(found.get(i).getDescription(), capabilities.get(i).getDescription());
                    }
                }
        );
    }
    @Test
    @DisplayName("Should throw no data found exception")
    void getAllCapabilitiesException() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        boolean isSortByTechnologiesAmount = true;

        when(capabilityRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThrows(NoDataFoundException.class, () -> capabilityAdapter.getAllCapabilities(page, size, isAscending, isSortByTechnologiesAmount));
    }
}