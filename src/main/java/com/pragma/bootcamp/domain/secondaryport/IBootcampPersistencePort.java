package com.pragma.bootcamp.domain.secondaryport;

import com.pragma.bootcamp.domain.model.Bootcamp;

import java.util.List;

public interface IBootcampPersistencePort {
    void saveBootcamp(Bootcamp bootcamp);
    Bootcamp getBootcamp(String name);

    List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean isAscending, boolean isSortByCapacitiesAmount);
}
