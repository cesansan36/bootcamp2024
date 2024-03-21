package com.pragma.bootcamp.domain.primaryport;

import com.pragma.bootcamp.domain.model.Capacity;

import java.util.List;

public interface ICapacityServicePort {

    void saveCapacity(Capacity capacity);
    Capacity getCapacity(String name);

    List<Capacity> getAllTechnologies(Integer page, Integer size, boolean isAscending);
}
