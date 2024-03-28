package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.AdapterConstants;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.secondaryport.ICapacityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CapacityAdapter implements ICapacityPersistencePort {
    private final ICapacityRepository capacityRepository;
    private final ICapacityEntityMapper capacityEntityMapper;

    @Override
    public void saveCapacity(Capacity capacity) {
        // TODO prevent creation if already exists
//        if (capacityRepository.findByName(capacity.getName()).isPresent()) {
//            throw new CapacityAlreadyExistException();
//        }
        capacityRepository.save(capacityEntityMapper.toEntity(capacity));
    }

    @Override
    public Capacity getCapacity(String name) {
        CapacityEntity capacity = capacityRepository.findByName(name).orElseThrow(ElementNotFoundException::new);

        return capacityEntityMapper.toModel(capacity);
    }

    @Override
    public List<Capacity> getAllCapacities(Integer page, Integer size, boolean isAscending, boolean isSortByTechnologiesAmount) {
        String sortingField = isSortByTechnologiesAmount ? AdapterConstants.FIELD_NAME_OF_SORT_BY_TECHNOLOGIES : AdapterConstants.FIELD_NAME_OF_SORT_BY_NAME;

        Sort sort = isAscending ? Sort.by(sortingField).ascending() : Sort.by(sortingField).descending();

        Pageable pagination = PageRequest.of(page, size, sort);
        List<CapacityEntity> capacities = capacityRepository.findAll(pagination).getContent();

        if (capacities.isEmpty()) {
            throw new NoDataFoundException();
        }
        return capacityEntityMapper.toModelList(capacities);
    }
}
