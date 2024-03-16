package com.pragma.bootcamp.adapters.driving.http.mapper;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.request.UpdateTechnologyRequest;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITechnologyRequestMapper {

    @Mapping(target = "id", ignore = true)
    // @Mapping(source = "name", target = "name")
    Technology addRequestToTechnology(AddTechnologyRequest addTechnologyRequest);

    Technology updateRequestToTechnology(UpdateTechnologyRequest updateTechnologyRequest);
}
