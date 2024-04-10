package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.exception.ElementNotFoundException;
import com.pragma.bootcamp.domain.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import com.pragma.bootcamp.domain.secondaryport.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomConstants;

import java.util.List;
import java.util.Optional;

public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort) {
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public void saveTechnology(Technology technology) {

        Optional<Technology> previousTechnology = technologyPersistencePort.getTechnology(technology.getName());
        if (previousTechnology.isPresent()) {
            throw new RegistryAlreadyExistsException(
                    String.format(
                            DomConstants.REGISTRY_NAME_ALREADY_USED,
                            DomConstants.Registry.TECHNOLOGY));
        }

        technologyPersistencePort.saveTechnology(technology);
    }

    @Override
    public Technology getTechnology(String name) {
        Optional<Technology> technology = technologyPersistencePort.getTechnology(name);
        if (technology.isEmpty()) {
            throw new ElementNotFoundException();
        }
        return technology.get();
    }

    @Override
    public List<Technology> getAllTechnologies(Integer page, Integer size, boolean isAscending) {
        return technologyPersistencePort.getAllTechnologies(page, size, isAscending);
    }

    @Override
    public Technology updateTechnology(Technology technology) {
        Optional<Technology> previousTechnology = technologyPersistencePort.getTechnology(technology.getName());
        if (previousTechnology.isEmpty()) {
            throw new ElementNotFoundException();
        }

        return technologyPersistencePort.updateTechnology(technology);
    }

    @Override
    public void deleteTechnology(Long id) {
        if (technologyPersistencePort.getTechnologyById(id).isEmpty()) {
            throw new ElementNotFoundException();
        }
        technologyPersistencePort.deleteTechnology(id);
    }
}
