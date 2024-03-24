package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import com.pragma.bootcamp.domain.util.DomConstants;


import static java.util.Objects.requireNonNull;

public class Technology {

    private final Long id;
    private final String name;
    private final String description;

	public Technology(Long id, String name, String description) {
		if (name.trim().isEmpty()) {
			throw new EmptyFieldException(String.format(DomConstants.FIELD_EMPTY_MESSAGE, DomConstants.Field.NAME));
        }
        if (description.trim().isEmpty()) {
			throw new EmptyFieldException(String.format(DomConstants.FIELD_EMPTY_MESSAGE, DomConstants.Field.DESCRIPTION));
        }
		if(name.length() > DomConstants.MAX_TECHNOLOGY_NAME_SIZE) {
			throw new CharLimitSurpassedException(String.format(DomConstants.FIELD_MAX_SIZE_SURPASSED_MESSAGE, DomConstants.Field.NAME, DomConstants.MAX_TECHNOLOGY_NAME_SIZE));
		}
		if(description.length() > DomConstants.MAX_TECHNOLOGY_DESCRIPTION_SIZE) {
			throw new CharLimitSurpassedException(String.format(DomConstants.FIELD_MAX_SIZE_SURPASSED_MESSAGE, DomConstants.Field.DESCRIPTION, DomConstants.MAX_TECHNOLOGY_DESCRIPTION_SIZE));
		}
		this.id = id;
		this.name = requireNonNull(name, String.format(DomConstants.FIELD_NULL_MESSAGE, DomConstants.Field.NAME));
        this.description = requireNonNull(description, String.format(DomConstants.FIELD_NULL_MESSAGE, DomConstants.Field.DESCRIPTION));
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
}
