package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapabilityResponse;
import com.pragma.bootcamp.domain.model.Capability;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapabilityResponseMapper {

    CapabilityResponse toCapabilityResponse(Capability capability);
    List<CapabilityResponse> toCapabilityResponseList(List<Capability> capabilities);
}
