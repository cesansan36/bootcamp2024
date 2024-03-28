package com.pragma.bootcamp.adapters.driving.http.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AddBootcampRequest {
    private final String name;
    private final String description;
    private final List<String> capacitiesNames;
}
