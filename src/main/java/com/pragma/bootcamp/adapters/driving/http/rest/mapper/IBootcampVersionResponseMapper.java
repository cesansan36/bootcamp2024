package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampVersionResponse;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampVersionResponseMapper {

    @Mapping(source = "bootcamp.name", target = "bootcampName")
    BootcampVersionResponse toResponse(BootcampVersion bootcampVersion);
    @Mapping(target = "startDate", qualifiedByName = "mapStartDate")
    @Mapping(target = "endDate", qualifiedByName = "mapEndDate")
    List<BootcampVersionResponse> toResponseList(List<BootcampVersion> bootcampVersions);

    @Named("mapStartDate")
    default String mapStartDate(LocalDate startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return startDate.format(formatter);
    }

    @Named("mapEndDate")
    default String mapEndDate(LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return endDate.format(formatter);
    }
}
