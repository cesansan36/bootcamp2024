package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.QuantityAboveRequiredException;
import com.pragma.bootcamp.domain.exception.QuantityBelowRequiredException;
import com.pragma.bootcamp.domain.util.DomConstants;

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
    private List<Capacity> capacities;

    public Bootcamp(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public List<Capacity> getCapacities() {
        return capacities;
    }

    public void setCapacities(List<Capacity> capacities) {
        this.capacities = capacities;
    }

    public void validateAndSetCapacities(List<Capacity> capacities) {
        this.capacities = validateCapacities(capacities);
    }

    private List<Capacity> validateCapacities(List<Capacity> capacities) {
        List<Capacity> unique = capacities.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Capacity::getId))), ArrayList::new));

        if (unique.size() < DomConstants.MIN_CAPACITIES_IN_BOOTCAMP) {
            throw  new QuantityBelowRequiredException(String.format(
                    DomConstants.BELOW_MINIMUM_AMOUNT_OF_CAPACITIES_MESSAGE, DomConstants.MIN_CAPACITIES_IN_BOOTCAMP
            ));
        }

        if (unique.size() > DomConstants.MAX_CAPACITIES_IN_BOOTCAMP) {
            throw  new QuantityAboveRequiredException(String.format(
                    DomConstants.ABOVE_MINIMUM_AMOUNT_OF_CAPACITIES_MESSAGE, DomConstants.MAX_CAPACITIES_IN_BOOTCAMP
            ));
        }

        return unique;
    }
}
