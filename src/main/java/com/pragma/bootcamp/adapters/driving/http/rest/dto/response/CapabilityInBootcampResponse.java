package com.pragma.bootcamp.adapters.driving.http.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CapabilityInBootcampResponse {
    private final Long id;
    private final String name;
    private final List<TechnologyInCapabilityResponse> technologies;
}
