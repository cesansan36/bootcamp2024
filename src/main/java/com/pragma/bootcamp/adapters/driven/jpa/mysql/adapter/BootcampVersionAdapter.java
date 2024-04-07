package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampVersionEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampVersionEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampVersionRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.AdapterConstants;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.secondaryport.IBootcampVersionPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class BootcampVersionAdapter implements IBootcampVersionPersistencePort {

    private final IBootcampVersionRepository bootcampVersionRepository;
    private final IBootcampVersionEntityMapper bootcampVersionEntityMapper;
    private final IBootcampRepository bootcampRepository;


    @Override
    public void saveBootcampVersion(BootcampVersion bootcampVersion) {
        if (bootcampVersionRepository.findByName(bootcampVersion.getName()).isPresent()) {
            throw new RegistryAlreadyExistsException(
                    String.format(
                            AdapterConstants.REGISTRY_NAME_ALREADY_USED,
                            AdapterConstants.Registry.BOOTCAMP_VERSION));
        }


        BootcampVersionEntity bootcampVersionEntity = bootcampVersionEntityMapper.toEntity(bootcampVersion);
        BootcampEntity bootcampEntity = bootcampRepository.findByName(bootcampVersion.getBootcamp().getName()).orElseThrow(ElementNotFoundException::new);
        bootcampVersionEntity.setBootcamp(bootcampEntity);

        bootcampVersionRepository.save(bootcampVersionEntity);
    }

    @Override
    public List<BootcampVersion> getAllBootcampVersion(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField) {
        String sortingFieldName;
        if (sortingField == Constants.SortingField.START_DATE) {
            sortingFieldName = AdapterConstants.FIELD_NAME_OF_SORT_VERSION_BY_START_DATE;
        } else if (sortingField == Constants.SortingField.MAX_PARTICIPANTS){
            sortingFieldName = AdapterConstants.FIELD_NAME_OF_SORT_VERSION_BY_MAX_PARTICIPANTS;
        }
        else if (sortingField == Constants.SortingField.NAME) {
            sortingFieldName = AdapterConstants.FIELD_NAME_OF_SORT_VERSION_BY_NAME;
        }
        else {
            sortingFieldName = AdapterConstants.FIELD_NAME_OF_SORT_VERSION_BY_BOOTCAMP_NAME;
        }

        Sort sort = isAscending ? Sort.by(sortingFieldName).ascending() : Sort.by(sortingFieldName).descending();
        Pageable pagination = PageRequest.of(page, size, sort);

        List<BootcampVersionEntity> bootcampVersionEntities = bootcampVersionRepository.findAll(pagination).getContent();

        return bootcampVersionEntityMapper.toModelList(bootcampVersionEntities);
    }

    @Override
    public List<BootcampVersion> getVersionsOfBootcamp(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField, String bootcamp) {
        String sortingFieldName;
        if (sortingField == Constants.SortingField.START_DATE) {
            sortingFieldName = AdapterConstants.FIELD_NAME_OF_SORT_VERSION_BY_START_DATE;
        } else {
            if (sortingField == Constants.SortingField.MAX_PARTICIPANTS)
                sortingFieldName = AdapterConstants.FIELD_NAME_OF_SORT_VERSION_BY_MAX_PARTICIPANTS;
            else sortingFieldName = AdapterConstants.FIELD_NAME_OF_SORT_VERSION_BY_NAME;
        }

        Sort sort = isAscending ? Sort.by(sortingFieldName).ascending() : Sort.by(sortingFieldName).descending();
        Pageable pagination = PageRequest.of(page, size, sort);

        BootcampEntity bootcampEntity = bootcampRepository.findByName(bootcamp).orElseThrow(ElementNotFoundException::new);

        List<BootcampVersionEntity> bootcampVersionEntities = bootcampVersionRepository.findByBootcampId(bootcampEntity.getId(), pagination).getContent();

        return bootcampVersionEntityMapper.toModelList(bootcampVersionEntities);
    }
}
