package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.domain.model.Bootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBootcampRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "capacities", ignore = true)
    Bootcamp addRequestToBootcamp(AddBootcampRequest addBootcampRequest);
}
