package com.pragma.bootcamp.domain.secondaryport;

import com.pragma.bootcamp.domain.model.Technology;

import java.util.List;

public interface ITechnologyPersistencePort {

    void saveTechnology(Technology technology);
    Technology getTechnology(String name);

    List<Technology> getAllTechnologies(Integer page, Integer size, boolean isAscending);

    Technology updateTechnology(Technology technology);
    void deleteTechnology(Long id);
}
