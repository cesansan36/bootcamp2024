package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapabilityRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapabilityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapabilityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapabilityResponseMapper;
import com.pragma.bootcamp.domain.primaryport.ICapabilityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/capability")
@RequiredArgsConstructor
public class CapabilityControllerAdapter {
    private final ICapabilityServicePort capabilityServicePort;
    private final ICapabilityRequestMapper capabilityRequestMapper;
    private final ICapabilityResponseMapper capabilityResponseMapper;

    @PostMapping("/add")
    public ResponseEntity<Void> addCapability(@RequestBody AddCapabilityRequest request) {
        request.setTechnologiesNames(new ArrayList<>(new HashSet<>(request.getTechnologiesNames())));
        capabilityServicePort.saveCapability(capabilityRequestMapper.addRequestToCapability(request));
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
