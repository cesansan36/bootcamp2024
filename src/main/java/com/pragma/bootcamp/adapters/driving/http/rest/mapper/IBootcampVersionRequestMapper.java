package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampVersionRequest;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBootcampVersionRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bootcamp", ignore = true)
    BootcampVersion requestToModel(AddBootcampVersionRequest request);
}
