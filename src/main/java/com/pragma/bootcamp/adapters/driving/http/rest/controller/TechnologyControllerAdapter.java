package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.UpdateTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ITechnologyResponseMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.util.ControllerConstants;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_ADD_TECHNOLOGY, description = ControllerConstants.OPERATION_DESCRIPTION_ADD_TECHNOLOGY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = ControllerConstants.RESPONSE_CREATED_DESCRIPTION),
        @ApiResponse(responseCode = "400", description = ControllerConstants.RESPONSE_BAD_REQUEST_DESCRIPTION_ADD_TECHNOLOGY)
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addTechnology(@RequestHeader("Authorization") String token, @RequestBody AddTechnologyRequest request) {
        technologyServicePort.verifyUser(token);
        technologyServicePort.saveTechnology(technologyRequestMapper.addRequestToTechnology(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_GET_ELEMENT, description = ControllerConstants.OPERATION_DESCRIPTION_GET_ELEMENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ControllerConstants.RESPONSE_OK_DESCRIPTION),
            @ApiResponse(responseCode = "404", description = ControllerConstants.RESPONSE_NOT_FOUND_DESCRIPTION, content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/search/{technologyName}")
    public ResponseEntity<TechnologyResponse> getTechnology(@PathVariable String technologyName) {
        return ResponseEntity.ok(
                technologyResponseMapper.toTechnologyResponse(
                        technologyServicePort.getTechnology(technologyName)));
    }

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_GET_LIST, description = ControllerConstants.OPERATION_DESCRIPTION_GET_LIST_TECHNOLOGIES)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ControllerConstants.RESPONSE_OK_DESCRIPTION)
    })
    @GetMapping("/")
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies(@RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "3") Integer size,
                                                                       @RequestParam(defaultValue = "true") boolean isAscending) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
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
