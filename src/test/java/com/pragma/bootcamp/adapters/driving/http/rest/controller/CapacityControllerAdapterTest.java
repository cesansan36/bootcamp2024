package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.TechnologyInCapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapacityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapacityResponseMapper;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ICapacityServicePort;
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

class CapacityControllerAdapterTest {

    private CapacityControllerAdapter capacityControllerAdapter;

    private ICapacityServicePort capacityServicePort;
    private ITechnologyServicePort technologyServicePort;
    private ICapacityRequestMapper capacityRequestMapper;
    private ICapacityResponseMapper capacityResponseMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        capacityServicePort = mock(ICapacityServicePort.class);
        technologyServicePort = mock(ITechnologyServicePort.class);
        capacityRequestMapper = mock(ICapacityRequestMapper.class);
        capacityResponseMapper = mock(ICapacityResponseMapper.class);
        capacityControllerAdapter = new CapacityControllerAdapter(capacityServicePort, technologyServicePort, capacityRequestMapper, capacityResponseMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(capacityControllerAdapter).build();
    }

    @Test
    void addCapacity() throws Exception {
        Object inputObject = new Object() {
            public final String name = "Cap 1";
            public final String description = "The cap 1";
            public final List<String> technologiesNames = Arrays.asList("Tec 1", "Tec 2", "Tec 3");
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        Capacity cap = new Capacity(1L, "Cap 1", "The cap 1");
        List<Technology> techs = TestDataDomain.getListOfValidTechnologies(3);
        cap.setTechnologies(techs);

        when(technologyServicePort.getTechnology("Tec 1")).thenReturn(techs.getFirst());
        when(technologyServicePort.getTechnology("Tec 2")).thenReturn(techs.get(1));
        when(technologyServicePort.getTechnology("Tec 3")).thenReturn(techs.getLast());
        when(capacityRequestMapper.addRequestToCapacity(any(AddCapacityRequest.class))).thenReturn(cap);

        MockHttpServletRequestBuilder request = post("/capacity/add").contentType(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(technologyServicePort, times(3)).getTechnology(anyString());
        verify(capacityRequestMapper, times(1)).addRequestToCapacity(any(AddCapacityRequest.class));
        verify(capacityServicePort, times(1)).saveCapacity(cap);
    }

    @Test
    void getCapacity() throws Exception {
        Capacity cap = TestDataDomain.getCapacityWithNoTechnologies(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        CapacityResponse capResponse = TestDataController.getCapacityResponse(1L, 2);

        when(capacityServicePort.getCapacity(anyString())).thenReturn(cap);
        when(capacityResponseMapper.toCapacityResponse(cap)).thenReturn(capResponse);

        MockHttpServletRequestBuilder request = get("/capacity/search/Cap");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.CAPACITY)))
                .andExpect(jsonPath("$.description").value(TestDataController.fieldText(1L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.CAPACITY)))
                .andExpect(jsonPath("$.technologies.size()").value(2))
                .andExpect(jsonPath("$.technologies[0].id").value(1L))
                .andExpect(jsonPath("$.technologies.[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
                .andExpect(jsonPath("$.technologies.[1].id").value(2L))
                .andExpect(jsonPath("$.technologies.[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
        ;

        verify(capacityServicePort, times(1)).getCapacity(anyString());
        verify(capacityResponseMapper, times(1)).toCapacityResponse(cap);
        /*
        Capacity cap = new Capacity(1L, "Cap", "The cap");
        TechnologyInCapacityResponse technologyInCapacityResponse1 = new TechnologyInCapacityResponse(1L, "tech 1");
        TechnologyInCapacityResponse technologyInCapacityResponse2 = new TechnologyInCapacityResponse(2L, "tech 2");
        List<TechnologyInCapacityResponse> techsInCap = Arrays.asList(technologyInCapacityResponse1, technologyInCapacityResponse2);
        CapacityResponse capResponse = new CapacityResponse(1L, "Cap", "The cap", techsInCap);

        when(capacityServicePort.getCapacity(anyString())).thenReturn(cap);
        when(capacityResponseMapper.toCapacityResponse(cap)).thenReturn(capResponse);

        MockHttpServletRequestBuilder request = get("/capacity/search/Cap");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Cap"))
                .andExpect(jsonPath("$.description").value("The cap"))
                .andExpect(jsonPath("$.technologies.size()").value(2))
                .andExpect(jsonPath("$.technologies[0].id").value(1L))
                .andExpect(jsonPath("$.technologies.[0].name").value("tech 1"))
                .andExpect(jsonPath("$.technologies.[1].id").value(2L))
                .andExpect(jsonPath("$.technologies.[1].name").value("tech 2"))
        ;

        verify(capacityServicePort, times(1)).getCapacity(anyString());
        verify(capacityResponseMapper, times(1)).toCapacityResponse(cap);*/
    }

    @Test
    void getAllCapacities() throws Exception {
        List<Capacity> caps = TestDataDomain.getListOfValidCapacities(2);
        List<CapacityResponse> responses = TestDataController.getListOfCapacityResponse(2, 2);

        when(capacityServicePort.getAllCapacities(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(caps);
        when(capacityResponseMapper.toCapacityResponseList(caps)).thenReturn(responses);

        MockHttpServletRequestBuilder request = get("/capacity/?page=0&size=2&isAscending=true&isSortByTechnologiesAmount=true");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.CAPACITY)))
                .andExpect(jsonPath("$[0].description").value(TestDataController.fieldText(1L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.CAPACITY)))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.CAPACITY)))
                .andExpect(jsonPath("$[1].description").value(TestDataController.fieldText(2L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.CAPACITY)))
                .andExpect(jsonPath("$[0].technologies.size()").value(2))
                .andExpect(jsonPath("$[1].technologies[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
        ;

        verify(capacityServicePort, times(1)).getAllCapacities(anyInt(), anyInt(), anyBoolean(), anyBoolean());
        verify(capacityResponseMapper, times(1)).toCapacityResponseList(caps);

        /*
        Capacity cap1 = new Capacity(1L, "Cap 1", "The cap 1");
        Capacity cap2 = new Capacity(2L, "Cap 2", "The cap 2");
        List<Capacity> caps = Arrays.asList(cap1, cap2);

        TechnologyInCapacityResponse technologyInCapacityResponse1 = new TechnologyInCapacityResponse(1L, "tech 1");
        TechnologyInCapacityResponse technologyInCapacityResponse2 = new TechnologyInCapacityResponse(2L, "tech 2");
        TechnologyInCapacityResponse technologyInCapacityResponse3 = new TechnologyInCapacityResponse(3L, "tech 3");
        TechnologyInCapacityResponse technologyInCapacityResponse4 = new TechnologyInCapacityResponse(4L, "tech 4");
        List<TechnologyInCapacityResponse> techsInCap1 = Arrays.asList(technologyInCapacityResponse1, technologyInCapacityResponse2);
        List<TechnologyInCapacityResponse> techsInCap2 = Arrays.asList(technologyInCapacityResponse3, technologyInCapacityResponse4);
        CapacityResponse capResponse1 = new CapacityResponse(1L, "Cap 1", "The cap 1", techsInCap1);
        CapacityResponse capResponse2 = new CapacityResponse(2L, "Cap 2", "The cap 2", techsInCap2);
        List<CapacityResponse> responses = Arrays.asList(capResponse1, capResponse2);

        when(capacityServicePort.getAllCapacities(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(caps);
        when(capacityResponseMapper.toCapacityResponseList(caps)).thenReturn(responses);

        MockHttpServletRequestBuilder request = get("/capacity/?page=0&size=2&isAscending=true&isSortByTechnologiesAmount=true");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Cap 1"))
                .andExpect(jsonPath("$[0].description").value("The cap 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Cap 2"))
                .andExpect(jsonPath("$[1].description").value("The cap 2"))
                .andExpect(jsonPath("$[0].technologies.size()").value(2))
                .andExpect(jsonPath("$[1].technologies[1].name").value("tech 4"))
        ;

        verify(capacityServicePort, times(1)).getAllCapacities(anyInt(), anyInt(), anyBoolean(), anyBoolean());
        verify(capacityResponseMapper, times(1)).toCapacityResponseList(caps);

         */
    }
}