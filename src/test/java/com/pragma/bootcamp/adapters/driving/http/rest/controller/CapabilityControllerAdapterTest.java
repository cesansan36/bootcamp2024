package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapabilityRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapabilityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapabilityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapabilityResponseMapper;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ICapabilityServicePort;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import com.pragma.bootcamp.testdata.TestDataController;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CapabilityControllerAdapterTest {

    private CapabilityControllerAdapter capabilityControllerAdapter;

    private ICapabilityServicePort capabilityServicePort;
    private ICapabilityRequestMapper capabilityRequestMapper;
    private ICapabilityResponseMapper capabilityResponseMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        capabilityServicePort = mock(ICapabilityServicePort.class);
        capabilityRequestMapper = mock(ICapabilityRequestMapper.class);
        capabilityResponseMapper = mock(ICapabilityResponseMapper.class);
        capabilityControllerAdapter = new CapabilityControllerAdapter(capabilityServicePort, capabilityRequestMapper, capabilityResponseMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(capabilityControllerAdapter).build();
    }

    @Test
    void addCapability() throws Exception {
        Object inputObject = new Object() {
            public final String name = "Cap 1";
            public final String description = "The cap 1";
            public final List<String> technologiesNames = Arrays.asList("Tec 1", "Tec 2", "Tec 3");
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        Capability cap = new Capability(0L, "Cap 1", "transfer");
        List<Technology> techs = TestDataDomain.getListOfValidTechnologies(3);
        cap.setTechnologies(techs);

        when(capabilityRequestMapper.addRequestToCapability(any(AddCapabilityRequest.class))).thenReturn(cap);

        MockHttpServletRequestBuilder request = post("/capability/add").contentType(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(capabilityRequestMapper, times(1)).addRequestToCapability(any(AddCapabilityRequest.class));
        verify(capabilityServicePort, times(1)).saveCapability(cap);
    }

    @Test
    void getCapability() throws Exception {
        Capability cap = TestDataDomain.getCapabilityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        CapabilityResponse capResponse = TestDataController.getCapabilityResponse(1L, 2);

        when(capabilityServicePort.getCapability(anyString())).thenReturn(cap);
        when(capabilityResponseMapper.toCapabilityResponse(cap)).thenReturn(capResponse);

        MockHttpServletRequestBuilder request = get("/capability/search/Cap");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.CAPABILITY)))
                .andExpect(jsonPath("$.description").value(TestDataController.fieldText(1L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.CAPABILITY)))
                .andExpect(jsonPath("$.technologies.size()").value(2))
                .andExpect(jsonPath("$.technologies[0].id").value(1L))
                .andExpect(jsonPath("$.technologies.[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
                .andExpect(jsonPath("$.technologies.[1].id").value(2L))
                .andExpect(jsonPath("$.technologies.[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
        ;

        verify(capabilityServicePort, times(1)).getCapability(anyString());
        verify(capabilityResponseMapper, times(1)).toCapabilityResponse(cap);
    }

    @Test
    void getAllCapabilities() throws Exception {
        List<Capability> caps = TestDataDomain.getListOfValidCapabilities(2);
        List<CapabilityResponse> responses = TestDataController.getListOfCapabilityResponse(2, 2);

        when(capabilityServicePort.getAllCapabilities(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(caps);
        when(capabilityResponseMapper.toCapabilityResponseList(caps)).thenReturn(responses);

        MockHttpServletRequestBuilder request = get("/capability/?page=0&size=2&isAscending=true&isSortByTechnologiesAmount=true");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.CAPABILITY)))
                .andExpect(jsonPath("$[0].description").value(TestDataController.fieldText(1L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.CAPABILITY)))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.CAPABILITY)))
                .andExpect(jsonPath("$[1].description").value(TestDataController.fieldText(2L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.CAPABILITY)))
                .andExpect(jsonPath("$[0].technologies.size()").value(2))
                .andExpect(jsonPath("$[1].technologies[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
        ;

        verify(capabilityServicePort, times(1)).getAllCapabilities(anyInt(), anyInt(), anyBoolean(), anyBoolean());
        verify(capabilityResponseMapper, times(1)).toCapabilityResponseList(caps);
    }
}