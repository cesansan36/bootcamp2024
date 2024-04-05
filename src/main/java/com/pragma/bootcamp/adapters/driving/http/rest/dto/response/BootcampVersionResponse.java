package com.pragma.bootcamp.adapters.driving.http.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BootcampVersionResponse {
    private final Long id;
    private final String name;
    private final Integer maxParticipants;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String bootcampName;
}
