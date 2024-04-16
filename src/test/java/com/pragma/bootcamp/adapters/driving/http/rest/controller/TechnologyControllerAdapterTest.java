package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.UpdateTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ITechnologyResponseMapper;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

        Technology technology = new Technology(1L, "Java", "Not python");

        when(technologyRequestMapper.addRequestToTechnology(any(AddTechnologyRequest.class))).thenReturn(technology);

        MockHttpServletRequestBuilder request = post("/technology/add").contentType(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(technologyRequestMapper, times(1)).addRequestToTechnology(any(AddTechnologyRequest.class));
        verify(technologyServicePort, times(1)).saveTechnology(any(Technology.class));
    }

    @Test
    void getTechnology() throws Exception {
        Technology technology = new Technology(1L, "java", "Not Python");
        TechnologyResponse technologyResponse = new TechnologyResponse(1L, "java", "Not Python");

        when(technologyServicePort.getTechnology(anyString())).thenReturn(technology);
        when(technologyResponseMapper.toTechnologyResponse(any(Technology.class))).thenReturn(technologyResponse);

        MockHttpServletRequestBuilder request = get("/technology/search/java");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("java"))
                .andExpect(jsonPath("$.description").value("Not Python"));

        verify(technologyServicePort, times(1)).getTechnology(anyString());
        verify(technologyResponseMapper, times(1)).toTechnologyResponse(any(Technology.class));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2, true",
            "-1, 0, false",
    })
    void getAllTechnologies(Integer page, Integer size, boolean isAscending) throws Exception {
        Technology technology1 = new Technology(1L, "java", "Not Python");
        Technology technology2 = new Technology(2L, "python", "Not Java");
        List<Technology> technologies = Arrays.asList(technology1, technology2);

        TechnologyResponse technologyResponse1 = new TechnologyResponse(1L, "java", "Not Python");
        TechnologyResponse technologyResponse2 = new TechnologyResponse(2L, "python", "Not Java");
        List<TechnologyResponse> technologyResponses = Arrays.asList(technologyResponse1, technologyResponse2);

        when(technologyServicePort.getAllTechnologies(anyInt(), anyInt(), anyBoolean())).thenReturn(technologies);
        when(technologyResponseMapper.toTechnologyResponseList(anyList())).thenReturn(technologyResponses);

        String url = "/technology/?page=%1$s&size=%2$s&isAscending=%3$s".formatted(page, size, isAscending);

        MockHttpServletRequestBuilder request = get(url);

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
        verify(technologyResponseMapper, times(1)).toTechnologyResponseList(anyList());
    }

    @Test
    void updateTechnology() throws Exception {
        Object inputObject = new Object() {
            public final String name = "java";
            public final String description = "Not python";
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        Technology technology = new Technology(1L, "java", "Not Python");
        Technology updatedTechnology = new Technology(1L, "java", "Not Python");
        TechnologyResponse technologyResponse = new TechnologyResponse(1L, "java", "Not Python");

        when(technologyRequestMapper.updateRequestToTechnology(any(UpdateTechnologyRequest.class))).thenReturn(technology);
        when(technologyServicePort.updateTechnology(any(Technology.class))).thenReturn(updatedTechnology);
        when(technologyResponseMapper.toTechnologyResponse(any(Technology.class))).thenReturn(technologyResponse);

        MockHttpServletRequestBuilder request = put("/technology/").contentType(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("java"))
                .andExpect(jsonPath("$.description").value("Not Python"));

        verify(technologyRequestMapper, times(1)).updateRequestToTechnology(any(UpdateTechnologyRequest.class));
        verify(technologyServicePort, times(1)).updateTechnology(any(Technology.class));
        verify(technologyResponseMapper, times(1)).toTechnologyResponse(any(Technology.class));
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
