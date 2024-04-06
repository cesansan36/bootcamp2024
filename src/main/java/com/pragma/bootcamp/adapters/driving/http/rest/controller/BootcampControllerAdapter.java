package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampResponseMapper;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import com.pragma.bootcamp.domain.primaryport.ICapabilityServicePort;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/bootcamp")
@RequiredArgsConstructor
public class BootcampControllerAdapter {

    private final IBootcampServicePort bootcampServicePort;
    private final ICapabilityServicePort capabilityServicePort;
    private final IBootcampRequestMapper bootcampRequestMapper;
    private final IBootcampResponseMapper bootcampResponseMapper;

    @Operation(summary = "Add a Bootcamp if not exists")
    @PostMapping("/add")
    public ResponseEntity<Void> addBootcamp(@RequestBody AddBootcampRequest request) {

        List<Capability> caps = new ArrayList<>();

        request.getCapabilitiesNames().forEach(capabilityName -> {
            Capability found = capabilityServicePort.getCapability(capabilityName);
            caps.add(found);
        });

        List<Capability> unique = caps.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Capability::getId))), ArrayList::new));

        Bootcamp bootcamp = bootcampRequestMapper.addRequestToBootcamp(request);
        bootcamp.validateAndSetCapabilities(unique);

        bootcampServicePort.saveBootcamp(bootcamp);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search/{bootcampName}")
    public ResponseEntity<BootcampResponse> getBootcamp(@PathVariable String bootcampName) {
        return ResponseEntity.ok(
                bootcampResponseMapper.toBootcampResponse(
                        bootcampServicePort.getBootcamp(bootcampName)));
    }

    @GetMapping("/")
    public ResponseEntity<List<BootcampResponse>> getAllBootcamps(@RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "3") Integer size,
                                                                  @RequestParam(defaultValue = "true") boolean isAscending,
                                                                  @RequestParam(defaultValue = "true") boolean isSortByCapacitiesAmount) {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 1;
        }
        return ResponseEntity.ok(
                bootcampResponseMapper.toBootcampResponseList(
                        bootcampServicePort.getAllBootcamps(page, size, isAscending, isSortByCapacitiesAmount)));
    }
}
