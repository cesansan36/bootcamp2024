package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampVersionResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampVersionRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampVersionResponseMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.util.ControllerConstants;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.primaryport.IBootcampVersionServicePort;
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

import java.util.List;

@RestController
@RequestMapping("/bootcamp_version")
@RequiredArgsConstructor
public class BootcampVersionControllerAdapter
{
    private final IBootcampVersionServicePort bootcampVersionServicePort;
    private final IBootcampVersionRequestMapper bootcampVersionRequestMapper;
    private final IBootcampVersionResponseMapper bootcampVersionResponseMapper;

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_ADD_BOOTCAMP_VERSION, description = ControllerConstants.OPERATION_DESCRIPTION_ADD_BOOTCAMP_VERSION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ControllerConstants.RESPONSE_CREATED_DESCRIPTION),
            @ApiResponse(responseCode = "400", description = ControllerConstants.RESPONSE_BAD_REQUEST_DESCRIPTION_ADD_BOOTCAMP_VERSION)
    })@PostMapping("/add")
    public ResponseEntity<Void> addBootcampVersion(@RequestBody AddBootcampVersionRequest request) {
        request.autoNameOnEmpty();

        bootcampVersionServicePort.saveBootcampVersion(bootcampVersionRequestMapper.requestToModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_GET_LIST, description = ControllerConstants.OPERATION_DESCRIPTION_GET_LIST_BOOTCAMP_VERSION_ALL)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ControllerConstants.RESPONSE_OK_DESCRIPTION)
    })
    @GetMapping("/")
    public ResponseEntity<List<BootcampVersionResponse>> getAllBootcampVersion(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "3") Integer size,
                                                                               @RequestParam(defaultValue = "true") boolean isAscending,
                                                                               @RequestParam() Constants.SortingField sortingField
    ) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 1;
        }

        return ResponseEntity.ok(
                bootcampVersionResponseMapper.toResponseList(
                        bootcampVersionServicePort.getAllBootcampVersion(page, size, isAscending, sortingField)
                )
        );
    }

    @Operation(summary = ControllerConstants.OPERATION_SUMMARY_GET_LIST, description = ControllerConstants.OPERATION_DESCRIPTION_GET_LIST_BOOTCAMP_VERSION_SINGLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ControllerConstants.RESPONSE_OK_DESCRIPTION),
            @ApiResponse(responseCode = "404", description = ControllerConstants.RESPONSE_NOT_FOUND_DESCRIPTION, content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/search_bootcamp/{bootcampName}")
    public ResponseEntity<List<BootcampVersionResponse>> getVersionsByBootcamp(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "3") Integer size,
                                                                               @RequestParam(defaultValue = "true") boolean isAscending,
                                                                               @RequestParam() Constants.SortingField sortingField,
                                                                               @PathVariable String bootcampName) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 1;
        }

        return ResponseEntity.ok(
            bootcampVersionResponseMapper.toResponseList(
                    bootcampVersionServicePort.getVersionsOfBootcamp(page, size, isAscending, sortingField, bootcampName)
            )
        );
    }
}
