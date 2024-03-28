package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.TechnologyResponse;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacityResponseMapper {

    CapacityResponse toCapacityResponse(Capacity capacity);
    List<CapacityResponse> toCapacityResponseList(List<Capacity> capacities);
}
