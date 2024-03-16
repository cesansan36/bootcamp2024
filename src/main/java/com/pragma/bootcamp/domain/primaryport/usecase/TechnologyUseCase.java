package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import com.pragma.bootcamp.domain.secondaryport.ITechnologyPersistencePort;

import java.util.List;

public class TechnologyUseCase implements ITechnologyServicePort {

    private ITechnologyPersistencePort technologyPersistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort) {
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public void saveTechnology(Technology technology) {
        technologyPersistencePort.saveTechnology(technology);
    }

    @Override
    public Technology getTechnology(String name) {
        return technologyPersistencePort.getTechnology(name);
    }

    @Override
    public List<Technology> getAllTechnologies(Integer page, Integer size, boolean isAscending) {
        return technologyPersistencePort.getAllTechnologies(page, size, isAscending);
    }

    @Override
    public Technology updateTechnology(Technology technology) {
        return technologyPersistencePort.updateTechnology(technology);
    }

    @Override
    public void deleteTechnology(Long id) {
        technologyPersistencePort.deleteTechnology(id);
    }
}
