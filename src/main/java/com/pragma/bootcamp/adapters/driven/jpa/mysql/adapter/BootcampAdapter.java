package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
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
import java.util.Optional;

@RequiredArgsConstructor
public class BootcampAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final IBootcampEntityMapper bootcampEntityMapper;

    @Override
    public void saveBootcamp(Bootcamp bootcamp) {
        bootcampRepository.save(bootcampEntityMapper.toEntity(bootcamp));
    }

    @Override
    public Optional<Bootcamp> getBootcamp(String name) {
        return bootcampRepository.findByName(name).map(bootcampEntityMapper::toModel);
    }

    @Override
    public List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean isAscending, boolean isSortByCapabilitiesAmount) {
        String sortingField = isSortByCapabilitiesAmount ? AdapterConstants.FIELD_NAME_OF_SORT_BY_CAPABILITIES : AdapterConstants.FIELD_NAME_OF_SORT_BY_NAME;

        Sort sort = isAscending ? Sort.by(sortingField).ascending() : Sort.by(sortingField).descending();

        Pageable pagination = PageRequest.of(page, size, sort);
        List<BootcampEntity> bootcamps = bootcampRepository.findAll(pagination).getContent();

        if (bootcamps.isEmpty()) {
            throw new NoDataFoundException();
        }
        return bootcampEntityMapper.toModelList(bootcamps);
    }
}
