package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.util.DomConstants;
import com.pragma.bootcamp.domain.util.DomValidation;


import static java.util.Objects.requireNonNull;

public class Technology {

	private final Long id;
	private final String name;
	private final String description;

	public Technology(Long id, String name, String description) {
		DomValidation.validateName(name);
		DomValidation.validateDescription(description);

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
