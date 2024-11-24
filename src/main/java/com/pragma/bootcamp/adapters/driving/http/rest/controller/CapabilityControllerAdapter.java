package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapabilityRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapabilityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapabilityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapabilityResponseMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.util.ControllerConstants;
import com.pragma.bootcamp.domain.primaryport.ICapabilityServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_ADD_CAPABILITY, description = ControllerConstants.OPERATION_DESCRIPTION_ADD_CAPABILITY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ControllerConstants.RESPONSE_CREATED_DESCRIPTION),
            @ApiResponse(responseCode = "400", description = ControllerConstants.RESPONSE_BAD_REQUEST_DESCRIPTION_ADD_CAPABILITY)
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addCapability(@RequestBody AddCapabilityRequest request) {
        request.setTechnologiesNames(new ArrayList<>(new HashSet<>(request.getTechnologiesNames())));
        capabilityServicePort.saveCapability(capabilityRequestMapper.addRequestToCapability(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_GET_ELEMENT, description = ControllerConstants.OPERATION_DESCRIPTION_GET_ELEMENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ControllerConstants.RESPONSE_OK_DESCRIPTION),
            @ApiResponse(responseCode = "404", description = ControllerConstants.RESPONSE_NOT_FOUND_DESCRIPTION, content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/search/{capabilityName}")
    public ResponseEntity<CapabilityResponse> getCapability(@PathVariable String capabilityName) {
        return ResponseEntity.ok(
                capabilityResponseMapper.toCapabilityResponse(
                        capabilityServicePort.getCapability(capabilityName)));
    }

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_GET_LIST, description = ControllerConstants.OPERATION_DESCRIPTION_GET_LIST_CAPABILITY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ControllerConstants.RESPONSE_OK_DESCRIPTION)
    })
    @GetMapping("/")
    public ResponseEntity<List<CapabilityResponse>> getAllCapabilities(@RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "3") Integer size,
                                                                       @RequestParam(defaultValue = "true") boolean isAscending,
                                                                       @RequestParam(defaultValue = "true") boolean isSortByTechnologiesAmount) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 1;
        }

        return ResponseEntity.ok(
                capabilityResponseMapper.toCapabilityResponseList(
                        capabilityServicePort.getAllCapabilities(page, size, isAscending, isSortByTechnologiesAmount)));
    }
}
