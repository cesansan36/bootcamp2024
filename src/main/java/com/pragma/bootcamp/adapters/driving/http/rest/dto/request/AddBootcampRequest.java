package com.pragma.bootcamp.adapters.driving.http.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AddBootcampRequest {
    private final String name;
    private final String description;
    private List<String> capabilitiesNames;
}
