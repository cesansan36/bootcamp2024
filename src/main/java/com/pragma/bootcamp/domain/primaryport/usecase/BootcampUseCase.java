package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import com.pragma.bootcamp.domain.secondaryport.IBootcampPersistencePort;

import java.util.List;

public class BootcampUseCase implements IBootcampServicePort {
    private final IBootcampPersistencePort bootcampPersistencePort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
    }

    @Override
    public void saveBootcamp(Bootcamp bootcamp) {
        bootcampPersistencePort.saveBootcamp(bootcamp);
    }

    @Override
    public Bootcamp getBootcamp(String name) {
        return bootcampPersistencePort.getBootcamp(name);
    }

    @Override
    public List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean isAscending, boolean isSortByCapacitiesAmount) {
        return bootcampPersistencePort.getAllBootcamps(page, size, isAscending, isSortByCapacitiesAmount);
    }
}
