package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampVersionRequest;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface IBootcampVersionRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "bootcampName", target = "bootcamp", qualifiedByName = "mapBootcamp")
    @Mapping(target = "startDate", qualifiedByName = "mapStartDate")
    @Mapping(target = "endDate", qualifiedByName = "mapEndDate")
    BootcampVersion requestToModel(AddBootcampVersionRequest request);

    @Named("mapBootcamp")
    default Bootcamp mapBootcamp(String bootcampName) {
        return new Bootcamp(0L, bootcampName, "transfer");
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
