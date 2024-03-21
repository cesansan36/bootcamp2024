package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
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
        capacityRepository.save(capacityEntityMapper.toEntity(capacity));  // ESTA
    }

    @Override
    public Capacity getCapacity(String name) {
        CapacityEntity capacity = capacityRepository.findByNameContaining(name).orElseThrow(ElementNotFoundException::new);

        return capacityEntityMapper.toModel(capacity);
    }

    @Override
    public List<Capacity> getAllCapacities(Integer page, Integer size, boolean isAscending) {
        // TODO should be able to sort by either name or number of technologies
        Sort sort = isAscending ? Sort.by("name").ascending() : Sort.by("name").descending();
        Pageable pagination = PageRequest.of(page, size, sort);
        List<CapacityEntity> capacities = capacityRepository.findAll(pagination).getContent();
        if (capacities.isEmpty()) {
            throw new NoDataFoundException();
        }
        return capacityEntityMapper.toModelList(capacities);
    }
}
