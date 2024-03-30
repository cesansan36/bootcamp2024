package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapacityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapacityResponseMapper;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ICapacityServicePort;
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
@RequestMapping("/capacity")
@RequiredArgsConstructor
public class CapacityControllerAdapter {
    private final ICapacityServicePort capacityServicePort;
    private final ITechnologyServicePort technologyServicePort;
    private final ICapacityRequestMapper capacityRequestMapper;
    private final ICapacityResponseMapper capacityResponseMapper;

    @PostMapping("/add")
    public ResponseEntity<Void> addCapacity(@RequestBody AddCapacityRequest request) {

        List<Technology> techs = new ArrayList<>();

        request.getTechnologiesNames().forEach(technologyName -> {
            Technology found = technologyServicePort.getTechnology(technologyName);
            techs.add(found);
        });

        List<Technology> unique = techs.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Technology::getId))), ArrayList::new));

        Capacity capacity = capacityRequestMapper.addRequestToCapacity(request);
        capacity.validateAndSetTechnologies(unique);

        capacityServicePort.saveCapacity(capacity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search/{capacityName}")
    public ResponseEntity<CapacityResponse> getCapacity(@PathVariable String capacityName) {
        return ResponseEntity.ok(
                capacityResponseMapper.toCapacityResponse(
                        capacityServicePort.getCapacity(capacityName)));
    }

    @GetMapping("/")
    public ResponseEntity<List<CapacityResponse>> getAllCapacities(@RequestParam(defaultValue = "0") Integer page,
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
                capacityResponseMapper.toCapacityResponseList(
                        capacityServicePort.getAllCapacities(page, size, isAscending, isSortByTechnologiesAmount)));
    }
}
