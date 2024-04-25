package com.pragma.bootcamp.adapters.driving.http.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.request.AddBootcampVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.rest.dto.response.BootcampVersionResponse;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampVersionRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.rest.mapper.IBootcampVersionResponseMapper;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import com.pragma.bootcamp.domain.primaryport.IBootcampVersionServicePort;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
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

    private IBootcampVersionServicePort bootcampVersionServicePort;
    private IBootcampVersionRequestMapper bootcampVersionRequestMapper;
    private IBootcampVersionResponseMapper bootcampVersionResponseMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        bootcampVersionServicePort = mock(IBootcampVersionServicePort.class);
        bootcampVersionRequestMapper = mock(IBootcampVersionRequestMapper.class);
        bootcampVersionResponseMapper = mock(IBootcampVersionResponseMapper.class);
        bootcampVersionControllerAdapter = new BootcampVersionControllerAdapter(bootcampVersionServicePort, bootcampVersionRequestMapper, bootcampVersionResponseMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(bootcampVersionControllerAdapter).build();
    }

    @Test
    void addBootcampVersion() throws Exception {
        String token = "1234";
        int separationBetweenDates = 5;
        Object inputObject = new Object() {
            public final String name = TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP_VERSION);
            public final int maxParticipants = 10;
            public final String startDate = LocalDate.now().toString();
            public final String endDate = LocalDate.now().plusDays(separationBetweenDates).toString();
            public final String bootcampName = TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP);
        };
        ObjectMapper objectMapper = new ObjectMapper();
        String inputJson = objectMapper.writeValueAsString(inputObject);

        BootcampVersion bootcampVersion = TestDataDomain.getBootcampVersion(1L, TestDataDomain.DataCase.VALID,  separationBetweenDates, 4, 4);

        when(bootcampVersionRequestMapper.requestToModel(any(AddBootcampVersionRequest.class))).thenReturn(bootcampVersion);

        MockHttpServletRequestBuilder request = post("/bootcamp_version/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson).header("Authorization", "Bearer " + token);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(bootcampVersionRequestMapper, times(1)).requestToModel(any(AddBootcampVersionRequest.class));
        verify(bootcampVersionServicePort, times(1)).saveBootcampVersion(any(BootcampVersion.class));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, true, NAME",
            "-1, -1, false, MAX_PARTICIPANTS",
            "1, 2, false, START_DATE",
            "1, 2, false, BOOTCAMP_NAME"
    })
    void getAllBootcampVersion(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField) throws Exception {
        int separationBetweenDates = 5;
        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(2, separationBetweenDates, 4, 4);
        List<BootcampVersionResponse> bootcampVersionResponses = TestDataController.getListOfBootcampVersionResponse(3, separationBetweenDates);

        when(bootcampVersionServicePort.getAllBootcampVersion(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class))).thenReturn(bootcampVersions);
        when(bootcampVersionResponseMapper.toResponseList(anyList())).thenReturn(bootcampVersionResponses);

        MockHttpServletRequestBuilder request = get("/bootcamp_version/?page=" + page + "&size=" + size + "&isAscending=" + isAscending + "&sortingField=" + sortingField);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP_VERSION)))
                .andExpect(jsonPath("$[0].maxParticipants").value(10))
                .andExpect(jsonPath("$[0].startDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].endDate").value(LocalDate.now().plusDays(separationBetweenDates).toString()))
                .andExpect(jsonPath("$[0].bootcampName").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
        ;
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, true, NAME, someName",
            "-1, -1, false, MAX_PARTICIPANTS, bootcampName",
            "1, 2, false, START_DATE, anotherName",
    })
    void getVersionsByBootcamp(Integer page, Integer size, boolean isAscending, Constants.SortingField sortingField, String bootcampName) throws Exception {
        int separationBetweenDates = 5;

        List<BootcampVersion> bootcampVersions = TestDataDomain.getListOfValidBootcampVersions(2, separationBetweenDates, 4, 4);
        List<BootcampVersionResponse> bootcampVersionResponses = TestDataController.getListOfBootcampVersionResponse(3, separationBetweenDates);

        when(bootcampVersionServicePort.getVersionsOfBootcamp(anyInt(), anyInt(), anyBoolean(), any(Constants.SortingField.class), any(String.class))).thenReturn(bootcampVersions);
        when(bootcampVersionResponseMapper.toResponseList(anyList())).thenReturn(bootcampVersionResponses);

        MockHttpServletRequestBuilder request = get("/bootcamp_version/search_bootcamp/" + bootcampName + "/?page=" + page + "&size=" + size + "&isAscending=" + isAscending + "&sortingField=" + sortingField);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP_VERSION)))
                .andExpect(jsonPath("$[0].maxParticipants").value(10))
                .andExpect(jsonPath("$[0].startDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].endDate").value(LocalDate.now().plusDays(separationBetweenDates).toString()))
                .andExpect(jsonPath("$[0].bootcampName").value(TestDataController.fieldText(1L, TestDataController.Fields.NAME, TestDataController.Element.BOOTCAMP)))
        ;
    }
}
