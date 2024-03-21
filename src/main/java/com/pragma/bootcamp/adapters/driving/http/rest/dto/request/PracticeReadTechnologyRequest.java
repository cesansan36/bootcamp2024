package com.pragma.bootcamp.adapters.driving.http.rest.dto.request;

public class PracticeReadTechnologyRequest {

    private final String name;

    public PracticeReadTechnologyRequest(String name, String description) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
