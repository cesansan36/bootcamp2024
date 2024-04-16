package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.exception.ElementNotFoundException;
import com.pragma.bootcamp.domain.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ICapabilityServicePort;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CapabilityUseCase implements ICapabilityServicePort {

    private final ICapabilityPersistencePort capabilityPersistencePort;
    private final ITechnologyPersistencePort technologyPersistencePort;

    public CapabilityUseCase(ICapabilityPersistencePort capabilityPersistencePort, ITechnologyPersistencePort technologyPersistencePort) {
        this.capabilityPersistencePort = capabilityPersistencePort;
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public void saveCapability(Capability capability) {

        Optional<Capability> previousCapability = capabilityPersistencePort.getCapability(capability.getName());
        if (previousCapability.isPresent()) {
            throw new RegistryAlreadyExistsException(
                    String.format(
                            DomConstants.REGISTRY_NAME_ALREADY_USED,
                            DomConstants.Registry.CAPABILITY));
        }

        List<Technology> technologies = new ArrayList<>();
        capability.getTechnologies().forEach(technology -> {
            Optional<Technology> foundTechnology = technologyPersistencePort.getTechnology(technology.getName());
            if (foundTechnology.isEmpty()) {
                throw new ElementNotFoundException();
            }
            technologies.add(foundTechnology.get());
        });
        Capability capabilityWithFoundTechnologies = new Capability(capability.getId(), capability.getName(), capability.getDescription(), technologies);

        capabilityPersistencePort.saveCapability(capabilityWithFoundTechnologies);
    }

    @Override
    public Capability getCapability(String name) {
        Optional<Capability> capability = capabilityPersistencePort.getCapability(name);
        if (capability.isEmpty()) {
            throw new ElementNotFoundException();
        }
        return capability.get();
    }

    @Override
    public List<Capability> getAllCapabilities(Integer page, Integer size, boolean isAscending, boolean isSortByTechnologiesAmount) {
        return capabilityPersistencePort.getAllCapabilities(page, size, isAscending, isSortByTechnologiesAmount);
    }
}
