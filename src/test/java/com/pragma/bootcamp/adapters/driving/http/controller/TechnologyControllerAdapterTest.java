package com.pragma.bootcamp.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.request.UpdateTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TechnologyControllerAdapterTest {

    private TechnologyControllerAdapter technologyControllerAdapter;

    private ITechnologyServicePort technologyServicePort;
    private ITechnologyRequestMapper technologyRequestMapper;
    private ITechnologyResponseMapper technologyResponseMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        technologyServicePort = mock(ITechnologyServicePort.class);
        technologyRequestMapper = mock(ITechnologyRequestMapper.class);
        technologyResponseMapper = mock(ITechnologyResponseMapper.class);
        technologyControllerAdapter = new TechnologyControllerAdapter(technologyServicePort, technologyRequestMapper, technologyResponseMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(technologyControllerAdapter).build();
    }

    @Test
    void addTechnology() throws Exception {

        Object inputObject = new Object() {
            public final String name = "java";
            public final String description = "Not python";
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        Technology tech = new Technology(1L, "Java", "Not python");

        when(technologyRequestMapper.addRequestToTechnology(any(AddTechnologyRequest.class))).thenReturn(tech);

        System.out.println(inputJson);
        MockHttpServletRequestBuilder request = post("/technology/add").contentType(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        //verify(technologyPersistencePort, times(1)).getAllTechnologies(anyInt(), anyInt(), anyBoolean())
        verify(technologyRequestMapper, times(1)).addRequestToTechnology(any(AddTechnologyRequest.class));
        verify(technologyServicePort, times(1)).saveTechnology(tech);
    }

    @Test
    void getTechnology() throws Exception {
        Technology tech = new Technology(1L, "java", "Not Python");
        TechnologyResponse techResponse = new TechnologyResponse(1L, "java", "Not Python");

        when(technologyServicePort.getTechnology(anyString())).thenReturn(tech);
        when(technologyResponseMapper.toTechnologyResponse(tech)).thenReturn(techResponse);

        MockHttpServletRequestBuilder request = get("/technology/search/java");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("java"))
                .andExpect(jsonPath("$.description").value("Not Python"));

        verify(technologyServicePort, times(1)).getTechnology(anyString());
        verify(technologyResponseMapper, times(1)).toTechnologyResponse(tech);
    }

    @Test
    void getAllTechnologies() throws Exception {
        Technology tech1 = new Technology(1L, "java", "Not Python");
        Technology tech2 = new Technology(2L, "python", "Not Java");
        List<Technology> techs = Arrays.asList(tech1, tech2);

        TechnologyResponse techResponse1 = new TechnologyResponse(1L, "java", "Not Python");
        TechnologyResponse techResponse2 = new TechnologyResponse(2L, "python", "Not Java");

        List<TechnologyResponse> responses = Arrays.asList(techResponse1, techResponse2);

        when(technologyServicePort.getAllTechnologies(anyInt(), anyInt(), anyBoolean())).thenReturn(techs);
        when(technologyResponseMapper.toTechnologyResponseList(techs)).thenReturn(responses);

        MockHttpServletRequestBuilder request = get("/technology/?page=0&size=2&isAscending=true");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("java"))
                .andExpect(jsonPath("$[0].description").value("Not Python"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("python"))
                .andExpect(jsonPath("$[1].description").value("Not Java"));

        verify(technologyServicePort, times(1)).getAllTechnologies(anyInt(), anyInt(), anyBoolean());
        verify(technologyResponseMapper, times(1)).toTechnologyResponseList(techs);
    }

    @Test
    void updateTechnology() throws Exception {
        Object inputObject = new Object() {
            public final String name = "java";
            public final String description = "Not python";
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        Technology tech = new Technology(1L, "java", "Not Python");
        TechnologyResponse techResponse = new TechnologyResponse(1L, "java", "Not Python");

        when(technologyRequestMapper.updateRequestToTechnology(any(UpdateTechnologyRequest.class))).thenReturn(tech);
        when(technologyServicePort.updateTechnology(tech)).thenReturn(tech);
        when(technologyResponseMapper.toTechnologyResponse(tech)).thenReturn(techResponse);

        MockHttpServletRequestBuilder request = put("/technology/").contentType(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("java"))
                .andExpect(jsonPath("$.description").value("Not Python"));

        verify(technologyRequestMapper, times(1)).updateRequestToTechnology(any(UpdateTechnologyRequest.class));
        verify(technologyServicePort, times(1)).updateTechnology(tech);
        verify(technologyResponseMapper, times(1)).toTechnologyResponse(tech);
    }

    @Test
    void deleteTechnology() throws Exception {

        MockHttpServletRequestBuilder request = delete("/technology/delete/1");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(technologyServicePort, times(1)).deleteTechnology(anyLong());
    }
}