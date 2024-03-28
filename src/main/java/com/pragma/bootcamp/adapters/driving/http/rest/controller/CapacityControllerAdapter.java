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

        List<Technology> tecs = new ArrayList<>();

        request.getTechnologiesNames().forEach(technologyName -> {
            Technology found = technologyServicePort.getTechnology(technologyName);
            tecs.add(found);
        });

        List<Technology> unique = tecs.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Technology::getId))), ArrayList::new));

        Capacity capacity = capacityRequestMapper.addRequestToCapacity(request);
        capacity.validateAndSetTechnologies(unique);

        capacityServicePort.saveCapacity(capacity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search/{capacityName}")
    public ResponseEntity<CapacityResponse> getCapacity(@PathVariable String capacityName) {
        return ResponseEntity.ok(capacityResponseMapper.toCapacityResponse(capacityServicePort.getCapacity(capacityName)));
    }

    @GetMapping("/")
    public ResponseEntity<List<CapacityResponse>> getAllCapacities(@RequestParam Integer page, @RequestParam Integer size, @RequestParam boolean isAscending, boolean isSortByTechnologiesAmount) {
        return ResponseEntity.ok(capacityResponseMapper.toCapacityResponseList(capacityServicePort.getAllCapacities(page, size, isAscending, isSortByTechnologiesAmount)));
    }
}
