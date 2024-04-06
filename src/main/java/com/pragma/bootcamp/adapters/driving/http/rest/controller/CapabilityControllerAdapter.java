package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapabilityRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapabilityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapabilityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapabilityResponseMapper;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ICapabilityServicePort;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@RestController
@RequestMapping("/capability")
@RequiredArgsConstructor
public class CapabilityControllerAdapter {
    private final ICapabilityServicePort capabilityServicePort;
    private final ITechnologyServicePort technologyServicePort;
    private final ICapabilityRequestMapper capabilityRequestMapper;
    private final ICapabilityResponseMapper capabilityResponseMapper;

    @PostMapping("/add")
    public ResponseEntity<Void> addCapability(@RequestBody AddCapabilityRequest request) {

        List<Technology> techs = new ArrayList<>();
        request.getTechnologiesNames().forEach(technologyName -> {
            Technology found = technologyServicePort.getTechnology(technologyName);
            techs.add(found);
        });
        List<Technology> unique = techs.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Technology::getId))), ArrayList::new));

        Capability capability = capabilityRequestMapper.addRequestToCapability(request);
        capability.validateAndSetTechnologies(unique);

        capabilityServicePort.saveCapability(capability);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search/{capabilityName}")
    public ResponseEntity<CapabilityResponse> getCapability(@PathVariable String capabilityName) {
        return ResponseEntity.ok(
                capabilityResponseMapper.toCapabilityResponse(
                        capabilityServicePort.getCapability(capabilityName)));
    }

    @GetMapping("/")
    public ResponseEntity<List<CapabilityResponse>> getAllCapabilities(@RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "3") Integer size,
                                                                       @RequestParam(defaultValue = "true") boolean isAscending,
                                                                       @RequestParam(defaultValue = "true") boolean isSortByTechnologiesAmount) {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 1;
        }
        return ResponseEntity.ok(
                capabilityResponseMapper.toCapabilityResponseList(
                        capabilityServicePort.getAllCapabilities(page, size, isAscending, isSortByTechnologiesAmount)));
    }
}
