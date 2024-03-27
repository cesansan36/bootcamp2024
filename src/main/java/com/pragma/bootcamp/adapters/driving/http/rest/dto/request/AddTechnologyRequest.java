package com.pragma.bootcamp.adapters.driving.http.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddTechnologyRequest {
    private final String name;
    private final String description;
}
