package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "capabilitiesNames", target = "capabilities", qualifiedByName = "mapCapabilitiesNames")
    Bootcamp addRequestToBootcamp(AddBootcampRequest addBootcampRequest);

    @Named("mapCapabilitiesNames")
    default List<Capability> mapCapabilities(List<String> capabilitiesNames) {
        return capabilitiesNames
                .stream()
                .map(capabilityName -> new Capability(0L, capabilityName, "transfer"))
                .toList();
    }
}
