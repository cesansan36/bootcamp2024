package com.pragma.bootcamp.adapters.driving.http.rest.dto.request;

import com.pragma.bootcamp.configuration.Constants;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Getter
public class AddBootcampVersionRequest {

    private final String name;
    private final Integer maxParticipants;
    private final Date  startDate;
    private final Date endDate;
    private final String bootcampName;

    public AddBootcampVersionRequest(String name, Integer maxParticipants, String startDate, String endDate, String bootcampName) throws ParseException {
        this.name = name;
        this.maxParticipants = maxParticipants;
        this.startDate = parseDate(startDate);
        this.endDate = parseDate(endDate);
        this.bootcampName = bootcampName;
    }

    public String getFormattedStartDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_USED);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        return simpleDateFormat.format(startDate);
    }

    public String getFormattedEndDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_USED);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        return simpleDateFormat.format(endDate);
    }

    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_USED);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        return simpleDateFormat.parse(date);
    }

    public AddBootcampVersionRequest fixValues() throws ParseException {
        String newName = name;
        if (name == null || name.trim().isEmpty()) {
            newName = bootcampName + " " + getFormattedStartDate();
        }

        return new AddBootcampVersionRequest(newName, maxParticipants, getFormattedStartDate(), getFormattedEndDate(), bootcampName);
    }
}
