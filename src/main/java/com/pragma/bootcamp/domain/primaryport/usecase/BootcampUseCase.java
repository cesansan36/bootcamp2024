package com.pragma.bootcamp.domain.primaryport.usecase;

import com.pragma.bootcamp.domain.exception.ElementNotFoundException;
import com.pragma.bootcamp.domain.exception.RegistryAlreadyExistsException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import com.pragma.bootcamp.domain.secondaryport.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;
import com.pragma.bootcamp.domain.util.DomConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BootcampUseCase implements IBootcampServicePort {
    private final IBootcampPersistencePort bootcampPersistencePort;
    private final ICapabilityPersistencePort capabilityPersistencePort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, ICapabilityPersistencePort capabilityPersistencePort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.capabilityPersistencePort = capabilityPersistencePort;
    }

    @Override
    public void saveBootcamp(Bootcamp bootcamp) {

        Optional<Bootcamp> previousBootcamp = bootcampPersistencePort.getBootcamp(bootcamp.getName());
        if (previousBootcamp.isPresent()) {
            throw new RegistryAlreadyExistsException(
                    String.format(
                            DomConstants.REGISTRY_NAME_ALREADY_USED,
                            DomConstants.Registry.BOOTCAMP));
        }

        List<Capability> capabilities = new ArrayList<>();
        bootcamp.getCapabilities().forEach(capability -> {
            Optional<Capability> previousCapability = capabilityPersistencePort.getCapability(capability.getName());
            if (previousCapability.isEmpty()) {
                throw new ElementNotFoundException();
            }
            capabilities.add(previousCapability.get());
        });

        Bootcamp bootcampWithFoundCapabilities = new Bootcamp(bootcamp.getId(), bootcamp.getName(), bootcamp.getDescription(), capabilities);

        bootcampPersistencePort.saveBootcamp(bootcampWithFoundCapabilities);
    }

    @Override
    public Bootcamp getBootcamp(String name) {
        Optional<Bootcamp> bootcamp = bootcampPersistencePort.getBootcamp(name);
        if (bootcamp.isEmpty()) {
            throw new ElementNotFoundException();
        }
        return bootcamp.get();
    }

    @Override
    public List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean isAscending, boolean isSortByCapabilitiesAmount) {
        return bootcampPersistencePort.getAllBootcamps(page, size, isAscending, isSortByCapabilitiesAmount);
    }
}
