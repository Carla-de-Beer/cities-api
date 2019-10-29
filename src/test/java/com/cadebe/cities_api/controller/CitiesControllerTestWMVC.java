package com.cadebe.cities_api.controller;

import com.cadebe.cities_api.model.City;
import com.cadebe.cities_api.service.CityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CitiesController.class)
@DisplayName("CitiesController Web MVC Tests")
class CitiesControllerTestWMVC {

    @MockBean
    CityService cityService;

    @Autowired
    private MockMvc mockMvc;

    private List<City> cities;

    @BeforeEach
    void setUp() {
        cities = Arrays.asList(
                City.builder().name("city1").countryCode("AA").population(1000).build(),
                City.builder().name("city2").countryCode("BB").population(2000).build());
    }

    @Test
    @DisplayName("Test find all cities")
    void findAllCities() throws Exception {
        given(cityService.findAll()).willReturn(cities);

        MvcResult result = mockMvc.perform(get("/api/v1/cities/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("city1")))
                .andExpect(jsonPath("$[0].countryCode", is("AA")))
                .andExpect(jsonPath("$[0].population", is(1000)))
                .andExpect(jsonPath("$[1].name", is("city2")))
                .andExpect(jsonPath("$[1].countryCode", is("BB")))
                .andExpect(jsonPath("$[1].population", is(2000)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @AfterEach
    void tearDown() {
        reset(cityService);
    }
}
