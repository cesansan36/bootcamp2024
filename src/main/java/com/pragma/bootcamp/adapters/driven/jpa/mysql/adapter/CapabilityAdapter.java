package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapabilityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapabilityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.AdapterConstants;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CapabilityAdapter implements ICapabilityPersistencePort {
    private final ICapabilityRepository capabilityRepository;
    private final ICapabilityEntityMapper capabilityEntityMapper;
    private final ITechnologyRepository technologyRepository;

    @Override
    public void saveCapability(Capability capability) {
        if (capabilityRepository.findByName(capability.getName()).isPresent()) {
            throw new RegistryAlreadyExistsException(
                    String.format(
                            AdapterConstants.REGISTRY_NAME_ALREADY_USED,
                            AdapterConstants.Registry.CAPABILITY));
        }

        CapabilityEntity capabilityEntity = capabilityEntityMapper.toEntity(capability);
        List<TechnologyEntity> technologyEntities = new ArrayList<>();
        capabilityEntity.getTechnologies().forEach(technologyEntity ->
            technologyRepository.findByName(technologyEntity.getName()).ifPresent(technologyEntities::add)
        );

        capabilityEntity.setTechnologies(technologyEntities);

        capabilityRepository.save(capabilityEntity);
    }

    @Override
    public Capability getCapability(String name) {
        CapabilityEntity capabilityEntity = capabilityRepository.findByName(name).orElseThrow(ElementNotFoundException::new);

        return capabilityEntityMapper.toModel(capabilityEntity);
    }

    @Override
    public List<Capability> getAllCapabilities(Integer page, Integer size, boolean isAscending, boolean isSortByTechnologiesAmount) {
        String sortingField = isSortByTechnologiesAmount ? AdapterConstants.FIELD_NAME_OF_SORT_BY_TECHNOLOGIES : AdapterConstants.FIELD_NAME_OF_SORT_BY_NAME;

        Sort sort = isAscending ? Sort.by(sortingField).ascending() : Sort.by(sortingField).descending();

        Pageable pagination = PageRequest.of(page, size, sort);
        List<CapabilityEntity> capabilityEntities = capabilityRepository.findAll(pagination).getContent();

        if (capabilityEntities.isEmpty()) {
            throw new NoDataFoundException();
        }
        return capabilityEntityMapper.toModelList(capabilityEntities);
    }
}
