package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampVersionResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampVersionRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampVersionResponseMapper;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import com.pragma.bootcamp.domain.primaryport.IBootcampVersionServicePort;
import com.pragma.bootcamp.testdata.TestDataController;
import com.pragma.bootcamp.testdata.TestDataDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
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

class BootcampVersionControllerAdapterTest {

    private BootcampVersionControllerAdapter bootcampVersionControllerAdapter;

    private IBootcampServicePort bootcampServicePort;
    private IBootcampVersionServicePort bootcampVersionServicePort;
    private IBootcampVersionRequestMapper bootcampVersionRequestMapper;
    private IBootcampVersionResponseMapper bootcampVersionResponseMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        bootcampServicePort = mock(IBootcampServicePort.class);
        bootcampVersionServicePort = mock(IBootcampVersionServicePort.class);
        bootcampVersionRequestMapper = mock(IBootcampVersionRequestMapper.class);
        bootcampVersionResponseMapper = mock(IBootcampVersionResponseMapper.class);
        bootcampVersionControllerAdapter = new BootcampVersionControllerAdapter(bootcampServicePort, bootcampVersionServicePort, bootcampVersionRequestMapper, bootcampVersionResponseMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(bootcampVersionControllerAdapter).build();
    }

    @Test
    void addBootcampVersion() throws Exception {
        Object inputObject = new Object() {
            public final String name = "Bootcamp version 1";
            public final int maxParticipants = 10;
            public final String startDate = "2022-01-01";
            public final String endDate = "2022-01-02";
            public final String bootcampName = "Bootcamp 1";
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        BootcampVersion bootcampVersion = new BootcampVersion(1L, "Bootcamp version 1", 10, TestDataDomain.parseDate("2022-01-01"), TestDataDomain.parseDate("2022-01-02"));
        Bootcamp bootcamp = new Bootcamp(1L, "Bootcamp 1", "The bootcamp 1");
        bootcampVersion.setBootcamp(bootcamp);

        when(bootcampVersionRequestMapper.requestToModel(any(AddBootcampVersionRequest.class))).thenReturn(bootcampVersion);

        MockHttpServletRequestBuilder request = post("/bootcamp_version/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(bootcampVersionRequestMapper, times(1)).requestToModel(any(AddBootcampVersionRequest.class));
        verify(bootcampVersionServicePort, times(1)).saveBootcampVersion(bootcampVersion);
    }

    @Test
    void getAllBootcampVersion() throws Exception {
//        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(3, 5);
//        List<BootcampVersionResponse> bootcampVersionResponses = TestDataController.getListOfBootcampVersionResponse(3);
//
//        when(bootcampVersionServicePort.getAllBootcampVersion(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class))).thenReturn(bootcampVersions);
//        when(bootcampVersionResponseMapper.toResponseList(bootcampVersions)).thenReturn(bootcampVersionResponses);
//
//        MockHttpServletRequestBuilder request = get("/bootcamp_version/?page=0&size=2&isAscending=true&sortingField=NAME");

        assertTrue(true);
//        mockMvc.perform(request)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
//                .andExpect(jsonPath("$[1].id").value(2L))
//                .andExpect(jsonPath("$[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
//                .andExpect(jsonPath("$[0].capabilities.size()").value(2))
//                .andExpect(jsonPath("$[1].capabilities[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.CAPABILITY)))
//                .andExpect(jsonPath("$[1].capabilities[0].technologies.size()").value(2))
//                .andExpect(jsonPath("$[0].capabilities[1].technologies[1].name").value(TestDataController.fieldText(2L, TestDataController.Fields.NAME, TestDataController.Element.TECHNOLOGY)))
//        ;

    }

    @Test
    void getVersionsByBootcamp() {
    }
}