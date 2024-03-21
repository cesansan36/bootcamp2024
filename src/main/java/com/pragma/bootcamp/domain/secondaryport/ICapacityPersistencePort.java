package com.pragma.bootcamp.domain.secondaryport;

import com.pragma.bootcamp.domain.model.Capacity;

import java.util.List;

public interface ICapacityPersistencePort {

    void saveCapacity(Capacity capacity);
    Capacity getCapacity(String name);

    List<Capacity> getAllCapacities(Integer page, Integer size, boolean isAscending);
}
