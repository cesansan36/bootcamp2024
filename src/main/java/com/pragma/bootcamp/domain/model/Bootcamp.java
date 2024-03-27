package com.pragma.bootcamp.domain.model;

import java.util.List;

public class Bootcamp {

    private final Long id;
    private final String name;
    private final String description;
    private List<Long> capacitiesIds;

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

    public List<Long> getCapacitiesIds() {
        return capacitiesIds;
    }

    public void setCapacitiesIds(List<Long> capacitiesIds) {
        this.capacitiesIds = capacitiesIds;
    }
}
