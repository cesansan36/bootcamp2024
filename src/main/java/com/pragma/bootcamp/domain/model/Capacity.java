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

public class Capacity {

    private final Long id;
    private final String name;
    private final String description;

    private List<Technology> technologies;

    public Capacity(Long id, String name, String description) {
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

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {

        this.technologies = technologies;
    }

    public void validateAndSetTechnologies(List<Technology> technologies) {
        this.technologies = validateTechnologies(technologies);
    }

    private List<Technology> validateTechnologies(List<Technology> technologies) {
        List<Technology> unique = technologies.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Technology::getId))), ArrayList::new));

        if (unique.size() < DomConstants.MIN_TECHNOLOGIES_IN_CAPACITY) {
            throw new QuantityBelowRequiredException(String.format(DomConstants.BELOW_MINIMUM_AMOUNT_OF_TECHNOLOGIES_MESSAGE, DomConstants.MIN_TECHNOLOGIES_IN_CAPACITY));
        }
        if (unique.size() > DomConstants.MAX_TECHNOLOGIES_IN_CAPACITY) {
            throw new QuantityAboveRequiredException(String.format(DomConstants.ABOVE_MINIMUM_AMOUNT_OF_TECHNOLOGIES_MESSAGE, DomConstants.MAX_TECHNOLOGIES_IN_CAPACITY));
        }
        return unique;
    }
}
