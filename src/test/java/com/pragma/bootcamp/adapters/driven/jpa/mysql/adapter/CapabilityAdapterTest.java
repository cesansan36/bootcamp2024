package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapabilityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapabilityRepository;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.testdata.TestDataDomain;
import com.pragma.bootcamp.testdata.TestDataDriven;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CapabilityAdapterTest {

    private CapabilityAdapter capabilityAdapter;

    private ICapabilityRepository capabilityRepository;
    private ICapabilityEntityMapper capabilityEntityMapper;

    @BeforeEach
    void setUp() {
        capabilityRepository = mock(ICapabilityRepository.class);
        capabilityEntityMapper = mock(ICapabilityEntityMapper.class);

        capabilityAdapter = new CapabilityAdapter(capabilityRepository, capabilityEntityMapper);
    }

    @Test
    @DisplayName("Should save capability correctly")
    void saveCapability() {
        Capability capability = TestDataDomain.getCapability(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4);

        CapabilityEntity capabilityEntity = TestDataDriven.getCapabilityEntity(1L, 4);

        when(capabilityEntityMapper.toEntity(any(Capability.class))).thenReturn(capabilityEntity);

        capabilityAdapter.saveCapability(capability);

        assertAll(
                () -> verify(capabilityEntityMapper, times(1)).toEntity(any(Capability.class)),
                () -> verify(capabilityRepository, times(1)).save(any(CapabilityEntity.class))
        );
    }

    @Test
    @DisplayName("Should get a capability correctly")
    void getCapability() {

        CapabilityEntity capabilityEntity = TestDataDriven.getCapabilityEntity(1L, 4);
        Capability retrieved = TestDataDomain.getCapability(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4);

        when(capabilityRepository.findByName(anyString())).thenReturn(Optional.of(capabilityEntity));
        when(capabilityEntityMapper.toModel(any(CapabilityEntity.class))).thenReturn(retrieved);

        Capability found = capabilityAdapter.getCapability("backend").orElseThrow();

        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(capabilityEntity.getName(), found.getName()),
                () -> assertEquals(capabilityEntity.getDescription(), found.getDescription()),
                () -> verify(capabilityRepository, times(1)).findByName(anyString()),
                () -> verify(capabilityEntityMapper, times(1)).toModel(any(CapabilityEntity.class)),
                ()-> assertEquals(4, found.getTechnologies().size())
        );
    }

    @Test
    @DisplayName("Should get a list of capabilities")
    void getAllCapabilities() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        boolean isSortByTechnologiesAmount = true;
        List<CapabilityEntity> capabilityEntities = TestDataDriven.getListOfCapabilityEntity(2, 4);
        List<Capability> capabilities = TestDataDomain.getListOfValidCapabilities(2, 4);

        when(capabilityRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(capabilityEntities));
        when(capabilityEntityMapper.toModelList(anyList())).thenReturn(capabilities);

        List<Capability> found = capabilityAdapter.getAllCapabilities(page, size, isAscending, isSortByTechnologiesAmount);

        assertAll(
                () -> assertEquals(capabilityEntities.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), capabilityEntities.get(i).getId());
                        assertEquals(found.get(i).getName(), capabilityEntities.get(i).getName());
                        assertEquals(found.get(i).getDescription(), capabilityEntities.get(i).getDescription());
                    }
                }
        );
    }
}
