package com.pragma.bootcamp.adapters.driving.http.rest.mapper;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampResponse;
import com.pragma.bootcamp.domain.model.Bootcamp;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampResponseMapper {

    BootcampResponse toBootcampResponse(Bootcamp bootcamp);
    List<BootcampResponse> toBootcampResponseList(List<Bootcamp> bootcamps);
}
