package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampResponseMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.util.ControllerConstants;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/bootcamp")
@RequiredArgsConstructor
public class BootcampControllerAdapter {

    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampRequestMapper bootcampRequestMapper;
    private final IBootcampResponseMapper bootcampResponseMapper;

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_ADD_BOOTCAMP, description = ControllerConstants.OPERATION_DESCRIPTION_ADD_BOOTCAMP)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ControllerConstants.RESPONSE_CREATED_DESCRIPTION),
            @ApiResponse(responseCode = "400", description = ControllerConstants.RESPONSE_BAD_REQUEST_DESCRIPTION_ADD_BOOTCAMP)
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addBootcamp(@RequestBody AddBootcampRequest request) {
        request.setCapabilitiesNames(new ArrayList<>(new HashSet<>(request.getCapabilitiesNames())));
        bootcampServicePort.saveBootcamp(bootcampRequestMapper.addRequestToBootcamp(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_GET_ELEMENT, description = ControllerConstants.OPERATION_DESCRIPTION_GET_ELEMENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ControllerConstants.RESPONSE_OK_DESCRIPTION),
            @ApiResponse(responseCode = "404", description = ControllerConstants.RESPONSE_NOT_FOUND_DESCRIPTION, content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/search/{bootcampName}")
    public ResponseEntity<BootcampResponse> getBootcamp(@PathVariable String bootcampName) {
        return ResponseEntity.ok(
                bootcampResponseMapper.toBootcampResponse(
                        bootcampServicePort.getBootcamp(bootcampName)));
    }

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_GET_LIST, description = ControllerConstants.OPERATION_DESCRIPTION_GET_LIST_BOOTCAMP)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ControllerConstants.RESPONSE_OK_DESCRIPTION)
    })
    @GetMapping("/")
    public ResponseEntity<List<BootcampResponse>> getAllBootcamps(@RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "3") Integer size,
                                                                  @RequestParam(defaultValue = "true") boolean isAscending,
                                                                  @RequestParam(defaultValue = "true") boolean isSortByCapabilitiesAmount) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 1;
        }

        return ResponseEntity.ok(
                bootcampResponseMapper.toBootcampResponseList(
                        bootcampServicePort.getAllBootcamps(page, size, isAscending, isSortByCapabilitiesAmount)));
    }
}
