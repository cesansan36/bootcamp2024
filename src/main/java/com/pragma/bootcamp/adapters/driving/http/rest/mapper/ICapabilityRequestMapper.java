package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapabilityRequest;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapabilityRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "technologiesNames", target = "technologies", qualifiedByName = "mapTechnologiesNames")
    Capability addRequestToCapability(AddCapabilityRequest addCapabilityRequest);

    @Named("mapTechnologiesNames")
    default List<Technology> mapTechnologies(List<String> technologiesNames) {
        return technologiesNames
                .stream()
                .map(technologyName -> new Technology(0L, technologyName, "transfer"))
                .toList();
    }
}
