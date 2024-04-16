package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
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
    void saveBootcamp() {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);

        BootcampEntity bootcampEntity = TestDataDriven.getBootcampEntity(1L, 4, 4);

        when(bootcampEntityMapper.toEntity(any(Bootcamp.class))).thenReturn(bootcampEntity);

        bootcampAdapter.saveBootcamp(bootcamp);

        assertAll(
                () -> verify(bootcampEntityMapper, times(1)).toEntity(any(Bootcamp.class)),
                () -> verify(bootcampRepository, times(1)).save(any(BootcampEntity.class))
        );
    }

    @Test
    @DisplayName("Should get a bootcamp correctly")
    void getBootcamp() {

        BootcampEntity bootcampEntity = TestDataDriven.getBootcampEntity(1L, 4, 4);
        Bootcamp retrieved = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);

        when(bootcampRepository.findByName(anyString())).thenReturn(Optional.of(bootcampEntity));
        when(bootcampEntityMapper.toModel(any(BootcampEntity.class))).thenReturn(retrieved);

        Bootcamp found = bootcampAdapter.getBootcamp("bootcamp").orElseThrow();

        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(bootcampEntity.getName(), found.getName()),
                () -> assertEquals(bootcampEntity.getDescription(), found.getDescription()),
                () -> verify(bootcampRepository, times(1)).findByName(anyString()),
                () -> verify(bootcampEntityMapper, times(1)).toModel(any(BootcampEntity.class)),
                () -> assertEquals(4, found.getCapabilities().size())
        );
    }

    @Test
    @DisplayName("Should get a list of bootcamps")
    void getAllBootcamps() {
        int page = 0;
        int size = 10;
        boolean isAscending = true;
        boolean isSortByCapabilitiesAmount = true;
        List<BootcampEntity> bootcampEntities = TestDataDriven.getListOfBootcampEntity(2, 4, 4);
        List<Bootcamp> bootcamps = TestDataDomain.getListOfValidBootcamps(2, 4, 4);

        when(bootcampRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(bootcampEntities));
        when(bootcampEntityMapper.toModelList(anyList())).thenReturn(bootcamps);

        List<Bootcamp> found = bootcampAdapter.getAllBootcamps(page, size, isAscending, isSortByCapabilitiesAmount);

        assertAll(
                () -> assertEquals(bootcampEntities.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), bootcampEntities.get(i).getId());
                        assertEquals(found.get(i).getName(), bootcampEntities.get(i).getName());
                        assertEquals(found.get(i).getDescription(), bootcampEntities.get(i).getDescription());
                    }
                }
        );
    }
}
