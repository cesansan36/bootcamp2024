package com.pragma.bootcamp.domain.secondaryport;

import com.pragma.bootcamp.domain.model.Technology;

import java.util.List;
import java.util.Optional;

public interface ITechnologyPersistencePort {

    void saveTechnology(Technology technology);
    Optional<Technology> getTechnology(String name);

    Optional<Technology> getTechnologyById(Long id);

    List<Technology> getAllTechnologies(Integer page, Integer size, boolean isAscending);

    Technology updateTechnology(Technology technology);
    void deleteTechnology(Long id);
}
