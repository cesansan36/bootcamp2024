package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import com.pragma.bootcamp.domain.util.DomConstants;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class Technology {

    private final Long id;
    private final String name;
    private final String description;
	private List<CapacityEntity> capacities;

	public Technology(Long id, String name, String description) {
		if (name.trim().isEmpty()) {
			throw new EmptyFieldException(DomConstants.Field.NAME.toString());
        }
        if (description.trim().isEmpty()) {
			throw new EmptyFieldException(DomConstants.Field.DESCRIPTION.toString());
        }
		if(name.length() > DomConstants.NAME_SIZE) {
			throw new CharLimitSurpassedException(DomConstants.Field.NAME.toString());
		}
		if(description.length() > DomConstants.DESCRIPTION_SIZE) {
			throw new CharLimitSurpassedException(DomConstants.Field.DESCRIPTION.toString());
		}
		this.id = id;
		this.name = requireNonNull(name, DomConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomConstants.FIELD_NAME_NULL_MESSAGE);
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}

	public List<CapacityEntity> getCapacities() {
		return capacities;
	}

	public void setCapacities(List<CapacityEntity> capacities) {
		this.capacities = capacities;
	}
}
