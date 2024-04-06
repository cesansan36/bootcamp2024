package com.pragma.bootcamp.domain.secondaryport;

import com.pragma.bootcamp.domain.model.Capability;

import java.util.List;

public interface ICapabilityPersistencePort {

    void saveCapability(Capability capability);
    Capability getCapability(String name);

    List<Capability> getAllCapabilities(Integer page, Integer size, boolean isAscending, boolean isSortByTechnologiesAmount);
}
