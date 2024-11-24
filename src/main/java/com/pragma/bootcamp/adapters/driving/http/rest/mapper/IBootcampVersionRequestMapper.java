package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampVersionRequest;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.DomConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampVersionRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "bootcampName", target = "bootcamp", qualifiedByName = "mapBootcamp")
    @Mapping(target = "startDate", qualifiedByName = "mapStartDate")
    @Mapping(target = "endDate", qualifiedByName = "mapEndDate")
    BootcampVersion requestToModel(AddBootcampVersionRequest request);

    @Named("mapBootcamp")
    default Bootcamp mapBootcamp(String bootcampName) {
        List<Technology> auxTechnologies = new ArrayList<>();
        for (int i = 0; i < DomConstants.MIN_TECHNOLOGIES_IN_CAPABILITY; i++) {
            auxTechnologies.add(new Technology(0L, "transfer"+i, "transfer"));
        }

        List<Capability> auxCapabilities = new ArrayList<>();
        for (int i = 0; i < DomConstants.MIN_CAPABILITIES_IN_BOOTCAMP; i++) {
            auxCapabilities.add(new Capability(0L, "transfer"+i, "transfer", auxTechnologies));
        }

        return new Bootcamp(0L, bootcampName, "transfer", auxCapabilities);
    }

    @Named("mapStartDate")
    default LocalDate mapStartDate(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return LocalDate.parse(startDate, formatter);
    }

    @Named("mapEndDate")
    default LocalDate mapEndDate(String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return LocalDate.parse(endDate, formatter);
    }
}
