package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.DomConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "capabilitiesNames", target = "capabilities", qualifiedByName = "mapCapabilitiesNames")
    Bootcamp addRequestToBootcamp(AddBootcampRequest addBootcampRequest);

    @Named("mapCapabilitiesNames")
    default List<Capability> mapCapabilities(List<String> capabilitiesNames) {
        List<Technology> auxTechnologies = new ArrayList<>();
        for (int i = 0; i < DomConstants.MIN_TECHNOLOGIES_IN_CAPABILITY; i++) {
            auxTechnologies.add(new Technology(0L, "transfer"+i, "transfer"));
        }
        return capabilitiesNames
                .stream()
                .map(capabilityName -> new Capability(0L, capabilityName, "transfer", auxTechnologies))
                .toList();
    }
}
