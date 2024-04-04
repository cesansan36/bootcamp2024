package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.DateFinishBeforeStartException;
import com.pragma.bootcamp.domain.exception.QuantityAboveRequiredException;
import com.pragma.bootcamp.domain.exception.QuantityBelowRequiredException;
import com.pragma.bootcamp.domain.util.DomConstants;
import com.pragma.bootcamp.domain.util.DomValidation;

import java.util.Date;

public class BootcampVersion {
    private final Long id;
    private final String name;
    private final int maxParticipants;
    private final Date  startDate;
    private final Date endDate;
    private Bootcamp bootcamp;

    public BootcampVersion(Long id, String name, int maxParticipants, Date  startDate, Date  endDate) {
        DomValidation.validateName(name);

        this.id = id;
        this.name = name;
        this.maxParticipants = maxParticipants;
        this.startDate = startDate;
        this.endDate = endDate;

        validate();
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public Date  getStartDate() {
        return startDate;
    }

    public Date  getEndDate() {
        return endDate;
    }

    public Bootcamp getBootcamp() {
        return bootcamp;
    }

    public void setBootcamp(Bootcamp bootcamp) {
        this.bootcamp = bootcamp;
    }

    public void validate() {
        if (maxParticipants < DomConstants.MIN_PARTICIPANTS_IN_BOOTCAMP_VERSION) {
            throw new QuantityBelowRequiredException(String.format(DomConstants.BELOW_MINIMUM_AMOUNT_OF_PARTICIPANTS_MESSAGE, DomConstants.MIN_PARTICIPANTS_IN_BOOTCAMP_VERSION));
        }
        if (maxParticipants > DomConstants.MAX_PARTICIPANTS_IN_BOOTCAMP_VERSION) {
            throw new QuantityAboveRequiredException(String.format(DomConstants.ABOVE_MINIMUM_AMOUNT_OF_PARTICIPANTS_MESSAGE, DomConstants.MAX_PARTICIPANTS_IN_BOOTCAMP_VERSION));
        }
        if (!endDate.after(startDate)) {
            throw new DateFinishBeforeStartException(DomConstants.DATE_FINISH_BEFORE_START_MESSAGE);
        }
    }
}
