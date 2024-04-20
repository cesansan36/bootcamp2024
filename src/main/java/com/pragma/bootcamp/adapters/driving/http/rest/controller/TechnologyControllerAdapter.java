package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.UpdateTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ITechnologyResponseMapper;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/technology")
public class TechnologyControllerAdapter {
    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyRequestMapper technologyRequestMapper;
    private final ITechnologyResponseMapper technologyResponseMapper;

    public TechnologyControllerAdapter(ITechnologyServicePort technologyServicePort, ITechnologyRequestMapper technologyRequestMapper, ITechnologyResponseMapper technologyResponseMapper) {
        this.technologyServicePort = technologyServicePort;
        this.technologyRequestMapper = technologyRequestMapper;
        this.technologyResponseMapper = technologyResponseMapper;
    }

    @Operation(summary = "Add a Technology if not exists")
    @PostMapping("/add")
    public ResponseEntity<Void> addTechnology(@RequestHeader("Authorization") String token, @RequestBody AddTechnologyRequest request) {
        technologyServicePort.verifyUser(token);
        technologyServicePort.saveTechnology(technologyRequestMapper.addRequestToTechnology(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Looks for specific technology by its name")
    @GetMapping("/search/{technologyName}")
    public ResponseEntity<TechnologyResponse> getTechnology(@PathVariable String technologyName) {
        return ResponseEntity.ok(
                technologyResponseMapper.toTechnologyResponse(
                        technologyServicePort.getTechnology(technologyName)));
    }

    @Operation(summary = "Get all technologies paginated and sorted")
    @GetMapping("/")
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies(@RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "3") Integer size,
                                                                       @RequestParam(defaultValue = "true") boolean isAscending) {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 1;
        }
        return ResponseEntity.ok(
                technologyResponseMapper.toTechnologyResponseList(
                        technologyServicePort.getAllTechnologies(page, size, isAscending)));
    }

    @Operation(summary = "Updates a Technology")
    @PutMapping("/")
    public ResponseEntity<TechnologyResponse> updateTechnology(@RequestBody UpdateTechnologyRequest request) {

        return ResponseEntity.ok(
                technologyResponseMapper.toTechnologyResponse(
                    technologyServicePort.updateTechnology((
                            technologyRequestMapper.updateRequestToTechnology(request)))
        ));
    }

    @Operation(summary = "Deletes a Technology by its id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        technologyServicePort.deleteTechnology(id);
        return ResponseEntity.noContent().build();
    }
}
