package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapacityInBootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.TechnologyInCapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampResponseMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapacityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.ICapacityResponseMapper;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BootcampControllerAdapterTest {

    private BootcampControllerAdapter bootcampControllerAdapter;

    private IBootcampServicePort bootcampServicePort;
    private ICapacityServicePort capacityServicePort;
    private IBootcampRequestMapper bootcampRequestMapper;
    private IBootcampResponseMapper bootcampResponseMapper;

    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        bootcampServicePort = mock(IBootcampServicePort.class);
        capacityServicePort = mock(ICapacityServicePort.class);
        bootcampRequestMapper = mock(IBootcampRequestMapper.class);
        bootcampResponseMapper = mock(IBootcampResponseMapper.class);
        bootcampControllerAdapter = new BootcampControllerAdapter(bootcampServicePort, capacityServicePort, bootcampRequestMapper, bootcampResponseMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(bootcampControllerAdapter).build();
    }

    @Test
    void addBootcamp() throws Exception {
        Object inputObject = new Object() {
            public final String name = "Bootcamp 1";
            public final String description = "The bootcamp 1";
            public final List<String> capacitiesNames = Arrays.asList("Cap 1", "Cap 2", "Cap 3");
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        Bootcamp bootcamp = new Bootcamp(1L, "Bootcamp 1", "The bootcamp 1");
        List<Capacity> caps = TestDataDomain.getListOfValidCapacities(3);
        bootcamp.setCapacities(caps);

        when(capacityServicePort.getCapacity("Cap 1")).thenReturn(caps.getFirst());
        when(capacityServicePort.getCapacity("Cap 2")).thenReturn(caps.get(1));
        when(capacityServicePort.getCapacity("Cap 3")).thenReturn(caps.getLast());
        when(bootcampRequestMapper.addRequestToBootcamp(any(AddBootcampRequest.class))).thenReturn(bootcamp);

        MockHttpServletRequestBuilder request = post("/bootcamp/add").contentType(MediaType.APPLICATION_JSON).content(inputJson);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(capacityServicePort, times(3)).getCapacity(anyString());
        verify(bootcampRequestMapper, times(1)).addRequestToBootcamp(any(AddBootcampRequest.class));
        verify(bootcampServicePort, times(1)).saveBootcamp(bootcamp);
    }

    @Test
    void getBootcamp() throws Exception {
        Bootcamp bootcamp = TestDataDomain.getBootcampWithNoCapacities(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID);
        BootcampResponse bootcampResponse = TestDataController.getBootcampResponse(1L, 2, 2);

        when(bootcampServicePort.getBootcamp(anyString())).thenReturn(bootcamp);
        when(bootcampResponseMapper.toBootcampResponse(bootcamp)).thenReturn(bootcampResponse);

        MockHttpServletRequestBuilder request = get("/bootcamp/search/" + TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$.description").value(TestDataController.fieldText(1L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$.capacities.size()").value(2))
                .andExpect(jsonPath("$.capacities[0].id").value(1L))
                .andExpect(jsonPath("$.capacities.[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.CAPACITY)))
                .andExpect(jsonPath("$.capacities.[1].technologies.size()").value(2))
                .andExpect(jsonPath("$.capacities.[1].technologies[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
        ;

        verify(bootcampServicePort, times(1)).getBootcamp(anyString());
        verify(bootcampResponseMapper, times(1)).toBootcampResponse(bootcamp);
        /*
        Bootcamp bootcamp = new Bootcamp(1L, "Bootcamp", "The bootcamp");

        List<TechnologyInCapacityResponse> techs1 = Arrays.asList(new TechnologyInCapacityResponse(1L, "tec 1"), new TechnologyInCapacityResponse(2L, "tec 2"));
        CapacityInBootcampResponse capacityInBootcampResponse1 = new CapacityInBootcampResponse(1L, "cap 1", techs1);
        List<TechnologyInCapacityResponse> techs2 = Arrays.asList(new TechnologyInCapacityResponse(3L, "tec 3"), new TechnologyInCapacityResponse(4L, "tec 4"));
        CapacityInBootcampResponse capacityInBootcampResponse2 = new CapacityInBootcampResponse(2L, "cap 2", techs2);
        List<CapacityInBootcampResponse> capsInBootcamp = Arrays.asList(capacityInBootcampResponse1, capacityInBootcampResponse2);
        BootcampResponse bootcampResponse = new BootcampResponse(1L, "Bootcamp", "The bootcamp", capsInBootcamp);

        when(bootcampServicePort.getBootcamp(anyString())).thenReturn(bootcamp);
        when(bootcampResponseMapper.toBootcampResponse(bootcamp)).thenReturn(bootcampResponse);

        MockHttpServletRequestBuilder request = get("/bootcamp/search/Bootcamp");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Bootcamp"))
                .andExpect(jsonPath("$.description").value("The bootcamp"))
                .andExpect(jsonPath("$.capacities.size()").value(2))
                .andExpect(jsonPath("$.capacities[0].id").value(1L))
                .andExpect(jsonPath("$.capacities.[0].name").value("cap 1"))
                .andExpect(jsonPath("$.capacities.[1].technologies.size()").value(2))
                .andExpect(jsonPath("$.capacities.[1].technologies[1].name").value("tec 4"))
        ;

        verify(bootcampServicePort, times(1)).getBootcamp(anyString());
        verify(bootcampResponseMapper, times(1)).toBootcampResponse(bootcamp);

         */
    }

    @Test
    void getAllBootcamps() throws Exception {
        List<Bootcamp> bootcamps = TestDataDomain.getListOfValidBootcamps(2);
        List<BootcampResponse> responses = TestDataController.getListOfBootcampResponse(2, 2, 2);

        when(bootcampServicePort.getAllBootcamps(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(bootcamps);
        when(bootcampResponseMapper.toBootcampResponseList(bootcamps)).thenReturn(responses);

        MockHttpServletRequestBuilder request = get("/bootcamp/?page=0&size=2&isAscending=true&isSortByCapacitiesAmount=true");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$[0].description").value(TestDataController.fieldText(1L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$[1].description").value(TestDataController.fieldText(2L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$[0].capacities.size()").value(2))
                .andExpect(jsonPath("$[1].capacities[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.CAPACITY)))
                .andExpect(jsonPath("$[1].capacities[0].technologies.size()").value(2))
                .andExpect(jsonPath("$[0].capacities[1].technologies[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
        ;

        verify(bootcampServicePort, times(1)).getAllBootcamps(anyInt(), anyInt(), anyBoolean(), anyBoolean());
        verify(bootcampResponseMapper, times(1)).toBootcampResponseList(bootcamps);
    }
}