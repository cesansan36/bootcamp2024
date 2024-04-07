package com.pragma.bootcamp.domain.primaryport;

import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;

import java.util.List;

public interface IBootcampVersionServicePort {
    void saveBootcampVersion(BootcampVersion bootcampVer);

    List<BootcampVersion> getAllBootcampVersion(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField);

    List<BootcampVersion> getVersionsOfBootcamp(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField, String bootcamp);
}
