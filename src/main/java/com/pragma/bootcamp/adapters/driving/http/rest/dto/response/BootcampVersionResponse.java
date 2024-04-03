package com.pragma.bootcamp.adapters.driving.http.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class BootcampVersionResponse {
    private final Long id;
    private final String name;
    private final Integer maxParticipants;
    private final Date startDate;
    private final Date endDate;
    private final String bootcampName;
}
