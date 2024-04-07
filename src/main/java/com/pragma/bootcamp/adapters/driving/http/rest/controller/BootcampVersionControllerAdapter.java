package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampVersionResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampVersionRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampVersionResponseMapper;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import com.pragma.bootcamp.domain.primaryport.IBootcampVersionServicePort;
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

import java.util.List;

@RestController
@RequestMapping("/bootcamp_version")
@RequiredArgsConstructor

public class BootcampVersionControllerAdapter
{
    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampVersionServicePort bootcampVersionServicePort;
    private final IBootcampVersionRequestMapper bootcampVersionRequestMapper;
    private final IBootcampVersionResponseMapper bootcampVersionResponseMapper;

    @PostMapping("/add")
    public ResponseEntity<Void> addBootcampVersion(@RequestBody AddBootcampVersionRequest request) {
        request.autoNameOnEmpty();

        bootcampVersionServicePort.saveBootcampVersion(bootcampVersionRequestMapper.requestToModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/")
    public ResponseEntity<List<BootcampVersionResponse>> getAllBootcampVersion(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "3") Integer size,
                                                                               @RequestParam(defaultValue = "true") boolean isAscending,
                                                                               @RequestParam() Constants.SortingField sortingField
    ) {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 1;
        }

        return ResponseEntity.ok(
                bootcampVersionResponseMapper.toResponseList(
                        bootcampVersionServicePort.getAllBootcampVersion(page, size, isAscending, sortingField)
                )
        );
    }

    @GetMapping("/search_bootcamp/{bootcampName}")
    public ResponseEntity<List<BootcampVersionResponse>> getVersionsByBootcamp(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "3") Integer size,
                                                                               @RequestParam(defaultValue = "true") boolean isAscending,
                                                                               @RequestParam() Constants.SortingField sortingField,
                                                                               @PathVariable String bootcampName) {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 1;
        }

//        // TODO change this so it doesn't create an instance of Bootcamp
//        Bootcamp found = bootcampServicePort.getBootcamp(bootcampName);

        return ResponseEntity.ok(
            bootcampVersionResponseMapper.toResponseList(
                    bootcampVersionServicePort.getVersionsOfBootcamp(page, size, isAscending, sortingField, bootcampName)
            )
        );
    }
}
