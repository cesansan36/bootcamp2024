package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.AdapterConstants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.secondaryport.IBootcampPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class BootcampAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final IBootcampEntityMapper bootcampEntityMapper;

    @Override
    public void saveBootcamp(Bootcamp bootcamp) {
        bootcampRepository.save(bootcampEntityMapper.toEntity(bootcamp));
    }

    @Override
    public Bootcamp getBootcamp(String name) {
        BootcampEntity bootcamp = bootcampRepository.findByName(name).orElseThrow(ElementNotFoundException::new);

        return bootcampEntityMapper.toModel(bootcamp);
    }

    @Override
    public List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean isAscending, boolean isSortByCapacitiesAmount) {
        String sortingField = isSortByCapacitiesAmount ? AdapterConstants.FIELD_NAME_OF_SORT_BY_CAPACITIES : AdapterConstants.FIELD_NAME_OF_SORT_BY_NAME;

        Sort sort = isAscending ? Sort.by(sortingField).ascending() : Sort.by(sortingField).descending();

        Pageable pagination = PageRequest.of(page, size, sort);
        List<BootcampEntity> bootcamps = bootcampRepository.findAll(pagination).getContent();

        if (bootcamps.isEmpty()) {
            throw new NoDataFoundException();
        }
        return bootcampEntityMapper.toModelList(bootcamps);
    }
}
