package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.QuantityAboveRequiredException;
import com.pragma.bootcamp.domain.exception.QuantityBelowRequiredException;
import com.pragma.bootcamp.domain.util.DomConstants;
import com.pragma.bootcamp.domain.util.DomValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class Bootcamp {

    private final Long id;
    private final String name;
    private final String description;
    private final List<Capability> capabilities;

    public Bootcamp(Long id, String name, String description, List<Capability> capabilities) {
        DomValidation.validateName(name);
        DomValidation.validateDescription(description);

        List<Capability> unique = validateCapabilities(capabilities);

        this.id = id;
        this.name = name;
        this.description = description;
        this.capabilities = unique;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    private List<Capability> validateCapabilities(List<Capability> capabilities) {
        List<Capability> unique = capabilities.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Capability::getId))), ArrayList::new));

        if (unique.size() < DomConstants.MIN_CAPABILITIES_IN_BOOTCAMP) {
            throw  new QuantityBelowRequiredException(String.format(
                    DomConstants.BELOW_MINIMUM_AMOUNT_OF_CAPABILITIES_MESSAGE, DomConstants.MIN_CAPABILITIES_IN_BOOTCAMP
            ));
        }

        if (unique.size() > DomConstants.MAX_CAPABILITIES_IN_BOOTCAMP) {
            throw  new QuantityAboveRequiredException(String.format(
                    DomConstants.ABOVE_MINIMUM_AMOUNT_OF_CAPABILITIES_MESSAGE, DomConstants.MAX_CAPABILITIES_IN_BOOTCAMP
            ));
        }

        return unique;
    }
}
