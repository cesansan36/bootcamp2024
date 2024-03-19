package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.CharLimitSurpassedException;
import com.pragma.bootcamp.domain.exception.EmptyFieldException;
import com.pragma.bootcamp.domain.util.DomConstants;

import static java.util.Objects.requireNonNull;

public class Technology {

    private final Long id;
    private final String name;
    private String description;

	public Technology(Long id, String name, String description) {
		if (name.trim().isEmpty()) {
			throw new EmptyFieldException(DomConstants.Field.NAME.toString());
        }
		if(name.length() > DomConstants.NAME_SIZE) {
			throw new CharLimitSurpassedException(DomConstants.Field.NAME.toString());
		}
		validateDescription(description);

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
	public void setDescription(String description) {
		validateDescription(description);
		this.description = description;
	}

	public void validateDescription(String description) {
		if (description.trim().isEmpty()) {
			throw new EmptyFieldException(DomConstants.Field.DESCRIPTION.toString());
		}
		if(description.length() > DomConstants.DESCRIPTION_SIZE) {
			throw new CharLimitSurpassedException(DomConstants.Field.DESCRIPTION.toString());
		}
	}
}
