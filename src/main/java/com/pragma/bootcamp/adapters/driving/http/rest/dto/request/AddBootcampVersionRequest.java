package com.pragma.bootcamp.adapters.driving.http.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddBootcampVersionRequest {

    private String name;
    private final Integer maxParticipants;
    private final String startDate;
    private final String endDate;
    private final String bootcampName;

    public void autoNameOnEmpty() {
        if (name == null || name.trim().isEmpty()) {
            name = bootcampName + " - " + startDate;
        }
    }
}
