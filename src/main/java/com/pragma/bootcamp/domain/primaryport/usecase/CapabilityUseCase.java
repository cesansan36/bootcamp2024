package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.primaryport.ICapabilityServicePort;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;

import java.util.List;

public class CapabilityUseCase implements ICapabilityServicePort {

    private final ICapabilityPersistencePort capabilityPersistencePort;

    public CapabilityUseCase(ICapabilityPersistencePort capabilityPersistencePort) {
        this.capabilityPersistencePort = capabilityPersistencePort;
    }

    @Override
    public void saveCapability(Capability capability) {
        capabilityPersistencePort.saveCapability(capability);
    }

    @Override
    public Capability getCapability(String name) {
        return capabilityPersistencePort.getCapability(name);
    }

    @Override
    public List<Capability> getAllCapabilities(Integer page, Integer size, boolean isAscending, boolean isSortByTechnologiesAmount) {
        return capabilityPersistencePort.getAllCapabilities(page, size, isAscending, isSortByTechnologiesAmount);
    }
}
