package com.cadebe.cities_api.controller;

import com.cadebe.cities_api.model.City;
import com.cadebe.cities_api.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("CitiesController Unit Tests")
class CitiesControllerTest {

    @Mock
    private CityService cityService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    CitiesController controller;

    private List<City> cities;
    private City city;

    private String idString = "d2992100-d58e-11e9-b075-ef972a09af52";
    private UUID id = UUID.fromString(idString);
    private String cityName = "cityName";
    private String countryCode = "XX";

    @BeforeEach
    void setUp() {
        city = City.builder()
                .id(id)
                .name(cityName)
                .countryCode(countryCode)
                .latitude(0.0)
                .latitude(0.0)
                .population(1000)
                .build();

        City city2 = City.builder()
                .name("cityName2")
                .countryCode("ZZ")
                .latitude(0.0)
                .latitude(0.0)
                .population(2000)
                .build();

        cities = new ArrayList<>();
        cities.add(city);
        cities.add(city2);
    }

    @Test
    @DisplayName("Test find all cities")
    void findAll() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        given(cityService.findAll()).willReturn(cities);

        ResponseEntity<Object> responseEntity = controller.findAll();

        then(cityService).should().findAll();
        then(cityService).shouldHaveNoMoreInteractions();

        assertThat(responseEntity)
                .withFailMessage("Find all cities response entity produces a null").
                isNotNull();
        assertThat(responseEntity.getStatusCodeValue())
                .withFailMessage("Find all cities response status code is not 200 OK")
                .isEqualTo(200);
        assertThat(responseEntity.getBody())
                .withFailMessage("Find all cities response entity had null fields")
                .hasNoNullFieldsOrProperties();
        assertThat(responseEntity.getBody())
                .withFailMessage("Find city by id response entity does not match expected object")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Test find city by id")
    void findCityById() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        given(cityService.findById(any(UUID.class))).willReturn(Optional.of(city));

        ResponseEntity<Object> responseEntity = controller.findById(idString);

        then(cityService).should().findById(id);
        then(cityService).shouldHaveNoMoreInteractions();

        assertThat(responseEntity)
                .withFailMessage("Find city by id response entity produces a null").
                isNotNull();
        assertThat(responseEntity.getStatusCodeValue())
                .withFailMessage("Find city by id response status code is not 200 OK")
                .isEqualTo(200);
        assertThat(responseEntity.getBody())
                .withFailMessage("Find city by id response entity has null fields")
                .hasNoNullFieldsOrProperties();
        assertThat(responseEntity.getBody())
                .withFailMessage("Find city by id response entity does not match expected object")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Test find all cities by name")
    void findAllCitiesByName() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        given(cityService.findByName(anyString())).willReturn(Optional.of(cities));

        ResponseEntity<Object> responseEntity = controller.findByName(cityName);

        then(cityService).should().findByName(cityName);
        then(cityService).shouldHaveNoMoreInteractions();

        assertThat(responseEntity)
                .withFailMessage("Find all cities by name response entity produces a null").
                isNotNull();
        assertThat(responseEntity.getStatusCodeValue())
                .withFailMessage("Find all cities by name response status code is not 200 OK")
                .isEqualTo(200);
        assertThat(responseEntity.getBody())
                .withFailMessage("Find all cities by name response entity had null fields")
                .hasNoNullFieldsOrProperties();
        assertThat(responseEntity.getBody())
                .withFailMessage("Find city by name response entity does not match expected object")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Test find city by name (invalid)")
    void findCityByIdNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Object> responseEntity = controller.findById("a1a1a1a1-a2a2-a3a3-a4a4-a5a5a5a5a5a5");

        then(cityService).should().findById(UUID.fromString("a1a1a1a1-a2a2-a3a3-a4a4-a5a5a5a5a5a5"));
        then(cityService).shouldHaveNoMoreInteractions();

        assertThat(responseEntity)
                .withFailMessage("Find city by id response entity produces a null").
                isNotNull();
        assertThat(responseEntity.getStatusCodeValue())
                .withFailMessage("Find city by id response status code is not 200 OK")
                .isEqualTo(404);
    }

    @Test
    @DisplayName("Test find all cities by country code")
    void findAllCitiesByCountryCode() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        given(cityService.findByCountryCode(anyString())).willReturn(Optional.of(cities));

        ResponseEntity<Object> responseEntity = controller.findByCountryCode(countryCode);

        then(cityService).should().findByCountryCode(countryCode);
        then(cityService).shouldHaveNoMoreInteractions();

        assertThat(responseEntity)
                .withFailMessage("Find all cities by country code response entity produces a null").
                isNotNull();
        assertThat(responseEntity.getStatusCodeValue())
                .withFailMessage("Find all cities by country code response status code is not 200 OK")
                .isEqualTo(200);
        assertThat(responseEntity.getBody())
                .withFailMessage("Find all cities by country code response entity had null fields")
                .hasNoNullFieldsOrProperties();
        assertThat(responseEntity.getBody())
                .withFailMessage("Find city by country code response entity does not match expected object")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Test find all cities by population size larger than given number")
    void findAllCitiesLargerThanGivenNumber() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        given(cityService.findAllCitiesWithPopulationGreaterThanX(anyLong())).willReturn(Optional.of(cities));

        ResponseEntity<Object> responseEntity = controller.findAllCitiesWithPopulationGreaterThanX(1200);

        then(cityService).should().findAllCitiesWithPopulationGreaterThanX(1200);
        then(cityService).shouldHaveNoMoreInteractions();

        assertThat(responseEntity)
                .withFailMessage("Find all cities by size response entity produces a null").
                isNotNull();
        assertThat(responseEntity.getStatusCodeValue())
                .withFailMessage("Find all cities by size response status code is not 200 OK")
                .isEqualTo(200);
        assertThat(responseEntity.getBody())
                .withFailMessage("Find all cities by size response entity had null fields")
                .hasNoNullFieldsOrProperties();
        assertThat(responseEntity.getBody())
                .withFailMessage("Find city by size response entity does not match expected object")
                .hasNoNullFieldsOrProperties();
    }
}