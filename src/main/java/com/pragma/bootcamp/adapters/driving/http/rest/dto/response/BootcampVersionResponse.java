package com.pragma.bootcamp.adapters.driving.http.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BootcampVersionResponse {
    private final Long id;
    private final String name;
    private final Integer maxParticipants;
    private final String startDate;
    private final String endDate;
    private final String bootcampName;
}
