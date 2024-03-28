package com.pragma.bootcamp.adapters.driving.http.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BootcampResponse {
    private final Long id;
    private final String name;
    private final String description;

    private final List<CapacityInBootcampResponse> capacities;
}
