package com.pragma.bootcamp.adapters.driving.http.mapper;

import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITechnologyResponseMapper {

    TechnologyResponse toTechnologyResponse(Technology technology);
    List<TechnologyResponse> toTechnologyResponseList(List<Technology> technologies);
}
