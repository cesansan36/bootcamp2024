package com.pragma.bootcamp.domain.primaryport;

import com.pragma.bootcamp.domain.model.Bootcamp;

import java.util.List;

public interface IBootcampServicePort {
    void saveBootcamp(Bootcamp bootcamp);
    Bootcamp getBootcamp(String name);

    List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean isAscending, boolean isSortByCapacitiesAmount);
}
