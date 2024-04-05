package com.pragma.bootcamp.adapters.driving.http.rest.dto.request;

import com.pragma.bootcamp.configuration.Constants;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class AddBootcampVersionRequest {

    private final String name;
    private final Integer maxParticipants;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String bootcampName;

    public AddBootcampVersionRequest(String name, Integer maxParticipants, String startDate, String endDate, String bootcampName) {
        this.name = name;
        this.maxParticipants = maxParticipants;
        this.startDate = parseDate(startDate);
        this.endDate = parseDate(endDate);
        this.bootcampName = bootcampName;
    }

    public String getFormattedStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return startDate.format(formatter);
    }

    public String getFormattedEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return endDate.format(formatter);
    }

    public LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_USED);
        return LocalDate.parse(date, formatter);
    }

    public AddBootcampVersionRequest fixValues() {
        String newName = name;
        if (name == null || name.trim().isEmpty()) {
            newName = bootcampName + " " + getFormattedStartDate();
        }

        return new AddBootcampVersionRequest(newName, maxParticipants, getFormattedStartDate(), getFormattedEndDate(), bootcampName);
    }
}
