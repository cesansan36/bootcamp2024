package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapacityResponse;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacityResponseMapper {

    CapacityResponse toCapacityResponse(Capacity capacity);
    List<CapacityResponse> toCapacityResponseList(List<Capacity> technologies);
}
