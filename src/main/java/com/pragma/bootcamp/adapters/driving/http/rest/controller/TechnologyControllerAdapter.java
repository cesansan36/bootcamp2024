package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.UpdateTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ITechnologyResponseMapper;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technology")
@RequiredArgsConstructor
public class TechnologyControllerAdapter {
    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyRequestMapper technologyRequestMapper;
    private final ITechnologyResponseMapper technologyResponseMapper;

    @PostMapping("/add")
    public ResponseEntity<Void> addTechnology(@RequestBody AddTechnologyRequest request) {
        technologyServicePort.saveTechnology(technologyRequestMapper.addRequestToTechnology(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search/{technologyName}")
    public ResponseEntity<TechnologyResponse> getTechnology(@PathVariable String technologyName) {
        return ResponseEntity.ok(technologyResponseMapper.toTechnologyResponse((technologyServicePort.getTechnology(technologyName))));
    }

    @GetMapping("/")
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies(@RequestParam Integer page, @RequestParam Integer size, @RequestParam boolean isAscending) {
        return ResponseEntity.ok(technologyResponseMapper.toTechnologyResponseList(technologyServicePort.getAllTechnologies(page, size, isAscending)));
    }

    @PutMapping("/")
    public ResponseEntity<TechnologyResponse> updateTechnology(@RequestBody UpdateTechnologyRequest request) {

        return ResponseEntity.ok(
                technologyResponseMapper.toTechnologyResponse(
                    technologyServicePort.updateTechnology((
                            technologyRequestMapper.updateRequestToTechnology(request)))
        ));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        technologyServicePort.deleteTechnology(id);
        return ResponseEntity.noContent().build();
    }
}
