package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICapacityRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "technologies", ignore = true)
    Capacity addRequestToCapacity(AddCapacityRequest addCapacityRequest);
}
