package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampVersionEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampVersionEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampVersionRepository;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.testdata.TestDataDomain;
import com.pragma.bootcamp.testdata.TestDataDriven;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

class BootcampVersionAdapterTest {

    private BootcampVersionAdapter bootcampVersionAdapter;

    private IBootcampVersionRepository bootcampVersionRepository;
    private IBootcampVersionEntityMapper bootcampVersionEntityMapper;

    @BeforeEach
    void setUp() {
        bootcampVersionRepository = mock(IBootcampVersionRepository.class);
        bootcampVersionEntityMapper = mock(IBootcampVersionEntityMapper.class);

        bootcampVersionAdapter = new BootcampVersionAdapter(bootcampVersionRepository, bootcampVersionEntityMapper);
    }

    @Test
    void saveBootcampVersion() {
        BootcampVersion bootcampVersion = TestDataDomain.getBootcampVersion(1L, TestDataDomain.DataCase.VALID, 4, 4, 4);

        BootcampVersionEntity bootcampVersionEntity = TestDataDriven.getBootcampVersionEntity(1L, 4, 4, 4);

        when(bootcampVersionEntityMapper.toEntity(any(BootcampVersion.class))).thenReturn(bootcampVersionEntity);

        bootcampVersionAdapter.saveBootcampVersion(bootcampVersion);

        assertAll(
                () -> verify(bootcampVersionEntityMapper, times(1)).toEntity(any(BootcampVersion.class)),
                () -> verify(bootcampVersionRepository, times(1)).save(any(BootcampVersionEntity.class))
        );
    }

    @Test
    void getBootcampVersion() {
        BootcampVersionEntity bootcampVersionEntity = TestDataDriven.getBootcampVersionEntity(1L, 4, 4, 4);
        BootcampVersion retrieved = TestDataDomain.getBootcampVersion(1L, TestDataDomain.DataCase.VALID, 4, 4, 4);

        when(bootcampVersionRepository.findByName(anyString())).thenReturn(Optional.of(bootcampVersionEntity));
        when(bootcampVersionEntityMapper.toModel(any(BootcampVersionEntity.class))).thenReturn(retrieved);

        BootcampVersion found = bootcampVersionAdapter.getBootcampVersion("bootcamp").orElseThrow();

        assertAll(
                () -> assertEquals(1L, found.getId()),
                () -> assertEquals(bootcampVersionEntity.getName(), found.getName()),
                () -> assertEquals(bootcampVersionEntity.getMaxParticipants(), found.getMaxParticipants()),
                () -> assertEquals(bootcampVersionEntity.getStartDate(), found.getStartDate()),
                () -> assertEquals(bootcampVersionEntity.getEndDate(), found.getEndDate()),
                () -> assertEquals(bootcampVersionEntity.getBootcamp().getName(), found.getBootcamp().getName()),
                () -> verify(bootcampVersionRepository, times(1)).findByName(anyString()),
                () -> verify(bootcampVersionEntityMapper, times(1)).toModel(any(BootcampVersionEntity.class))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, true, NAME",
            "1, 2, false, MAX_PARTICIPANTS",
            "1, 2, false, START_DATE",
            "1, 2, false, BOOTCAMP_NAME"
    })
    void getAllBootcampVersion(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField) {
        List<BootcampVersionEntity> bootcampVersionEntities = TestDataDriven.getListOfBootcampVersionEntity(4, 4, 4, 4);
        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(4, 4, 4, 4);

        when(bootcampVersionRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(bootcampVersionEntities));
        when(bootcampVersionEntityMapper.toModelList(anyList())).thenReturn(bootcampVersions);

        List<BootcampVersion> found = bootcampVersionAdapter.getAllBootcampVersion(page, size, isAscending, sortingField);

        assertAll(
                () -> assertEquals(bootcampVersionEntities.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), bootcampVersionEntities.get(i).getId());
                        assertEquals(found.get(i).getName(), bootcampVersionEntities.get(i).getName());
                        assertEquals(found.get(i).getMaxParticipants(), bootcampVersionEntities.get(i).getMaxParticipants());
                        assertEquals(found.get(i).getStartDate(), bootcampVersionEntities.get(i).getStartDate());
                        assertEquals(found.get(i).getEndDate(), bootcampVersionEntities.get(i).getEndDate());
                        assertEquals(found.get(i).getBootcamp().getName(), bootcampVersionEntities.get(i).getBootcamp().getName());
                    }
                }
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, true, NAME, 1",
            "1, 2, false, MAX_PARTICIPANTS, 1",
            "1, 2, false, START_DATE, 1"
    })
    void getVersionsOfBootcamp(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField, Long bootcampId) {
        List<BootcampVersionEntity> bootcampVersionEntities = TestDataDriven.getListOfBootcampVersionEntity(4, 4, 4, 4);
        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(4, 4, 4, 4);

        when(bootcampVersionRepository.findByBootcampId(anyLong(),any(Pageable.class))).thenReturn(new PageImpl<>(bootcampVersionEntities));
        when(bootcampVersionEntityMapper.toModelList(anyList())).thenReturn(bootcampVersions);

        List<BootcampVersion> found = bootcampVersionAdapter.getVersionsOfBootcamp(page, size, isAscending, sortingField, bootcampId);

        assertAll(
                () -> assertEquals(bootcampVersionEntities.size(), found.size()),
                () -> {
                    for (int i = 0 ; i < found.size() ; i++) {
                        assertEquals(found.get(i).getId(), bootcampVersionEntities.get(i).getId());
                        assertEquals(found.get(i).getName(), bootcampVersionEntities.get(i).getName());
                        assertEquals(found.get(i).getMaxParticipants(), bootcampVersionEntities.get(i).getMaxParticipants());
                        assertEquals(found.get(i).getStartDate(), bootcampVersionEntities.get(i).getStartDate());
                        assertEquals(found.get(i).getEndDate(), bootcampVersionEntities.get(i).getEndDate());
                        assertEquals(found.get(i).getBootcamp().getName(), bootcampVersionEntities.get(i).getBootcamp().getName());
                    }
                }
        );
    }
}
