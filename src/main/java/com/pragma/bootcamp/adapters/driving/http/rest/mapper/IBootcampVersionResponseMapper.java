package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampVersionResponse;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampVersionResponseMapper {

    @Mapping(source = "bootcamp.name", target = "bootcampName")
    BootcampVersionResponse toResponse(BootcampVersion bootcampVersion);
    List<BootcampVersionResponse> toResponseList(List<BootcampVersion> bootcampVersions);
}
