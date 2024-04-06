package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapabilityRequest;
import com.pragma.bootcamp.domain.model.Capability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICapabilityRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "technologies", ignore = true)
    Capability addRequestToCapability(AddCapabilityRequest addCapabilityRequest);
}
