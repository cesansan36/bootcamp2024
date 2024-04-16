package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapabilityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapabilityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.AdapterConstants;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CapabilityAdapter implements ICapabilityPersistencePort {
    private final ICapabilityRepository capabilityRepository;
    private final ICapabilityEntityMapper capabilityEntityMapper;

    @Override
    public void saveCapability(Capability capability) {
        capabilityRepository.save(capabilityEntityMapper.toEntity(capability));
    }

    @Override
    public Optional<Capability> getCapability(String name) {
        return capabilityRepository.findByName(name).map(capabilityEntityMapper::toModel);
    }

    @Override
    public List<Capability> getAllCapabilities(Integer page, Integer size, boolean isAscending, boolean isSortByTechnologiesAmount) {
        String sortingField = isSortByTechnologiesAmount ? AdapterConstants.FIELD_NAME_OF_SORT_BY_TECHNOLOGIES : AdapterConstants.FIELD_NAME_OF_SORT_BY_NAME;
        Sort sort = isAscending ? Sort.by(sortingField).ascending() : Sort.by(sortingField).descending();
        Pageable pagination = PageRequest.of(page, size, sort);
        List<CapabilityEntity> capabilityEntities = capabilityRepository.findAll(pagination).getContent();

        return capabilityEntityMapper.toModelList(capabilityEntities);
    }
}
