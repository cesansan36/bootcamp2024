package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampResponseMapper;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import com.pragma.bootcamp.testdata.TestDataController;
import com.pragma.bootcamp.testdata.TestDataDomain;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BootcampControllerAdapterTest {

    private BootcampControllerAdapter bootcampControllerAdapter;

    private IBootcampServicePort bootcampServicePort;
    private IBootcampRequestMapper bootcampRequestMapper;
    private IBootcampResponseMapper bootcampResponseMapper;

    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        bootcampServicePort = mock(IBootcampServicePort.class);
        bootcampRequestMapper = mock(IBootcampRequestMapper.class);
        bootcampResponseMapper = mock(IBootcampResponseMapper.class);
        bootcampControllerAdapter = new BootcampControllerAdapter(bootcampServicePort, bootcampRequestMapper, bootcampResponseMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(bootcampControllerAdapter).build();
    }

    @Test
    void addBootcamp() throws Exception {
        String token = "1234";
        Object inputObject = new Object() {
            public final String name = "Bootcamp 1";
            public final String description = "The bootcamp 1";
            public final List<String> capabilitiesNames = Arrays.asList("Capability 1", "Capability 2", "Capability 3");
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);

        when(bootcampRequestMapper.addRequestToBootcamp(any(AddBootcampRequest.class))).thenReturn(bootcamp);

        MockHttpServletRequestBuilder request = post("/bootcamp/add").contentType(MediaType.APPLICATION_JSON).content(inputJson).header("Authorization", "Bearer " + token);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(bootcampRequestMapper, times(1)).addRequestToBootcamp(any(AddBootcampRequest.class));
        verify(bootcampServicePort, times(1)).saveBootcamp(any(Bootcamp.class));
    }

    @Test
    void getBootcamp() throws Exception {
        Bootcamp bootcamp = TestDataDomain.getBootcamp(1L, TestDataDomain.DataCase.VALID, TestDataDomain.DataCase.VALID, 4, 4);
        BootcampResponse bootcampResponse = TestDataController.getBootcampResponse(1L, 4, 4);

        when(bootcampServicePort.getBootcamp(anyString())).thenReturn(bootcamp);
        when(bootcampResponseMapper.toBootcampResponse(any(Bootcamp.class))).thenReturn(bootcampResponse);

        MockHttpServletRequestBuilder request = get("/bootcamp/search/bootcamp_name");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$.description").value(TestDataController.fieldText(1L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$.capabilities.size()").value(4))
                .andExpect(jsonPath("$.capabilities[0].id").value(1L))
                .andExpect(jsonPath("$.capabilities.[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.CAPABILITY)))
                .andExpect(jsonPath("$.capabilities.[1].id").value(2L))
                .andExpect(jsonPath("$.capabilities.[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.CAPABILITY)))
        ;

        verify(bootcampServicePort, times(1)).getBootcamp(anyString());
        verify(bootcampResponseMapper, times(1)).toBootcampResponse(any(Bootcamp.class));

    }

    @ParameterizedTest
    @CsvSource({
            "0, 2, true, true",
            "-1, 0, false, false",
    })
    void getAllBootcamps(Integer page, Integer size, boolean isAscending, boolean isSortByCapabilitiesAmount) throws Exception {
        List<Bootcamp> bootcamps = TestDataDomain.getListOfValidBootcamps(2, 4, 4);
        List<BootcampResponse> bootcampResponses = TestDataController.getListOfBootcampResponse(2, 4, 4);

        when(bootcampServicePort.getAllBootcamps(anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(bootcamps);
        when(bootcampResponseMapper.toBootcampResponseList(anyList())).thenReturn(bootcampResponses);

        MockHttpServletRequestBuilder request = get("/bootcamp/?page=" + page + "&size=" + size + "&isAscending=" + isAscending + "&isSortByCapabilitiesAmount=" + isSortByCapabilitiesAmount);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$[0].description").value(TestDataController.fieldText(1L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$[1].description").value(TestDataController.fieldText(2L, TestDataController.Fields.DESCRIPTION, TestDataController.Element.BOOTCAMP)))
                .andExpect(jsonPath("$[0].capabilities.size()").value(4))
                .andExpect(jsonPath("$[1].capabilities[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.CAPABILITY)))
        ;

        verify(bootcampServicePort, times(1)).getAllBootcamps(anyInt(), anyInt(), anyBoolean(), anyBoolean());
        verify(bootcampResponseMapper, times(1)).toBootcampResponseList(anyList());

    }
}
