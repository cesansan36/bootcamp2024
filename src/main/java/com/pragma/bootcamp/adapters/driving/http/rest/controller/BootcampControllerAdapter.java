package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampResponseMapper;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import com.pragma.bootcamp.domain.primaryport.ICapacityServicePort;
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
    private final ICapacityServicePort capacityServicePort;
    private final IBootcampRequestMapper bootcampRequestMapper;
    private final IBootcampResponseMapper bootcampResponseMapper;

    @Operation(summary = "Add a Bootcamp if not exists")
    @PostMapping("/add")
    public ResponseEntity<Void> addBootcamp(@RequestBody AddBootcampRequest request) {

        List<Capacity> caps = new ArrayList<>();

        request.getCapacitiesNames().forEach(capacityName -> {
            Capacity found = capacityServicePort.getCapacity(capacityName);
            caps.add(found);
        });

        List<Capacity> unique = caps.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Capacity::getId))), ArrayList::new));

        Bootcamp bootcamp = bootcampRequestMapper.addRequestToBootcamp(request);
        bootcamp.validateAndSetCapacities(unique);

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
    public ResponseEntity<List<BootcampResponse>> getAllBootcamps(@RequestParam Integer page,
                                                                  @RequestParam Integer size,
                                                                  @RequestParam boolean isAscending,
                                                                  @RequestParam boolean isSortByCapacitiesAmount) {
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
