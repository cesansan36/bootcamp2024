package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.primaryport.ICapacityServicePort;
import com.pragma.bootcamp.domain.secondaryport.ICapacityPersistencePort;

import java.util.List;

public class CapacityUseCase implements ICapacityServicePort {

    private ICapacityPersistencePort capacityPersistencePort;

    public CapacityUseCase(ICapacityPersistencePort capacityPersistencePort) {
        this.capacityPersistencePort = capacityPersistencePort;
    }

    @Override
    public void saveCapacity(Capacity capacity) {
        capacityPersistencePort.saveCapacity(capacity);
    }

    @Override
    public Capacity getCapacity(String name) {
        return capacityPersistencePort.getCapacity(name);
    }

    @Override
    public List<Capacity> getAllTechnologies(Integer page, Integer size, boolean isAscending, boolean isSortByTechnologiesAmount) {
        return capacityPersistencePort.getAllCapacities(page, size, isAscending, isSortByTechnologiesAmount);
    }
}
