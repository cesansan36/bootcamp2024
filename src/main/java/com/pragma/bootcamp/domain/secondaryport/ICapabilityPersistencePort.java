package com.pragma.bootcamp.domain.secondaryport;

import com.pragma.bootcamp.domain.model.Capability;

import java.util.List;
import java.util.Optional;

public interface ICapabilityPersistencePort {

    void saveCapability(Capability capability);
    Optional<Capability> getCapability(String name);

    List<Capability> getAllCapabilities(Integer page, Integer size, boolean isAscending, boolean isSortByTechnologiesAmount);
}
