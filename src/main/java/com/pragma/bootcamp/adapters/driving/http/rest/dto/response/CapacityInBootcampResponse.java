package com.pragma.bootcamp.adapters.driving.http.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CapacityInBootcampResponse {
    private final Long id;
    private final String name;
    private final List<TechnologyInCapacityResponse> capacities;
}
