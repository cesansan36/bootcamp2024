package com.pragma.bootcamp.domain.secondaryport;

import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.BootcampVersion;

import java.util.List;
import java.util.Optional;

public interface IBootcampVersionPersistencePort {

    void saveBootcampVersion(BootcampVersion bootcampVer);

    Optional<BootcampVersion> getBootcampVersion(String name);
    List<BootcampVersion> getAllBootcampVersion(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField);
    List<BootcampVersion> getVersionsOfBootcamp(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField, Long bootcampId);
}
