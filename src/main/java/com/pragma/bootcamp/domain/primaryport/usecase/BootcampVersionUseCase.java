package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.primaryport.IBootcampVersionServicePort;
import com.pragma.bootcamp.domain.secondaryport.IBootcampVersionPersistencePort;

import java.util.List;

public class BootcampVersionUseCase implements IBootcampVersionServicePort {

    private final IBootcampVersionPersistencePort bootcampVerPersistencePort;

    public BootcampVersionUseCase(IBootcampVersionPersistencePort bootcampVerPersistencePort) {
        this.bootcampVerPersistencePort = bootcampVerPersistencePort;
    }

    @Override
    public void saveBootcampVersion(BootcampVersion bootcampVersion) {
        bootcampVersion.validate();
        bootcampVerPersistencePort.saveBootcampVersion(bootcampVersion);
    }

    @Override
    public List<BootcampVersion> getAllBootcampVersion(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField) {
        return bootcampVerPersistencePort.getAllBootcampVersion(page, size, isAscending, sortingField);
    }

    @Override
    public List<BootcampVersion> getVersionsOfBootcamp(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField, String bootcamp) {
        return bootcampVerPersistencePort.getVersionsOfBootcamp(page, size, isAscending, sortingField, bootcamp);
    }
}
