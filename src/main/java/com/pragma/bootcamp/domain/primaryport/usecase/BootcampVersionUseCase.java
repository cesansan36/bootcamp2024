package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.exception.ElementNotFoundException;
import com.pragma.bootcamp.domain.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.primaryport.IBootcampVersionServicePort;
import com.pragma.bootcamp.domain.secondaryport.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.IBootcampVersionPersistencePort;
import com.pragma.bootcamp.domain.util.DomConstants;

import java.util.List;
import java.util.Optional;

public class BootcampVersionUseCase implements IBootcampVersionServicePort {

    private final IBootcampVersionPersistencePort bootcampVersionPersistencePort;
    private final IBootcampPersistencePort bootcampPersistencePort;

    public BootcampVersionUseCase(IBootcampVersionPersistencePort bootcampVersionPersistencePort, IBootcampPersistencePort bootcampPersistencePort) {
        this.bootcampVersionPersistencePort = bootcampVersionPersistencePort;
        this.bootcampPersistencePort = bootcampPersistencePort;
    }

    @Override
    public void saveBootcampVersion(BootcampVersion bootcampVersion) {
        bootcampVersion.validate();
        Optional<BootcampVersion> previousBootcampVersion = bootcampVersionPersistencePort.getBootcampVersion(bootcampVersion.getName());
        if (previousBootcampVersion.isPresent()) {
            throw new RegistryAlreadyExistsException(
                    String.format(
                            DomConstants.REGISTRY_NAME_ALREADY_USED,
                            DomConstants.Registry.BOOTCAMP_VERSION));
        }


        Bootcamp bootcamp = bootcampPersistencePort.getBootcamp(bootcampVersion.getBootcamp().getName()).orElseThrow(ElementNotFoundException::new);
        bootcampVersion.setBootcamp(bootcamp);

        bootcampVersionPersistencePort.saveBootcampVersion(bootcampVersion);
    }

    @Override
    public List<BootcampVersion> getAllBootcampVersion(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField) {
        return bootcampVersionPersistencePort.getAllBootcampVersion(page, size, isAscending, sortingField);
    }

    @Override
    public List<BootcampVersion> getVersionsOfBootcamp(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField, String bootcamp) {
        Optional<Bootcamp> previousBootcamp = bootcampPersistencePort.getBootcamp(bootcamp);
        if (previousBootcamp.isEmpty()) {
            throw new ElementNotFoundException();
        }
        return bootcampVersionPersistencePort.getVersionsOfBootcamp(page, size, isAscending, sortingField, previousBootcamp.get().getId());
    }
}
