package com.cadebe.cities_api.service;

import com.cadebe.cities_api.model.City;
import com.cadebe.cities_api.repository.CityDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@Tag("controller")
@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock(lenient = true)
    CityDao cityDao;

    @InjectMocks
    CityService service;

    private City city;
    private List<City> cities;

    @BeforeEach
    void setUp() {
        city = City.builder()
                .name("name")
                .latitude(0.0)
                .latitude(0.0)
                .population(100)
                .build();

        cities = new ArrayList<>();
        cities.add(city);
    }

    @Test
    @DisplayName("Test find all cities")
    void testFindAll() {
        // given
        given(cityDao.findAll()).willReturn(cities);

        // when
        List<City> foundCities = service.findAll();

        // then
        then(cityDao).should().findAll();
        then(cityDao).shouldHaveNoMoreInteractions();

        assertThat(foundCities)
                .withFailMessage("Find all cities produces a null")
                .isNotNull();
        assertThat(foundCities)
                .withFailMessage("City list size is incorrect for find all cities")
                .hasSize(1);
    }

    @Test
    @DisplayName("Test find city by id")
    void testFindById() {
        // given
        given(cityDao.findById(any(UUID.class))).willReturn(Optional.of(city));

        // when
        Optional<City> foundCity = service.findById(UUID.randomUUID());

        // then
        then(cityDao).should().findById(any(UUID.class));
        then(cityDao).shouldHaveNoMoreInteractions();

        assertThat(foundCity.orElse(null))
                .withFailMessage("Find city by id produces a null")
                .isNotNull();
    }

    @Test
    @DisplayName("Test find city by name")
    void testFindByName() {
        // given
        given(cityDao.findByName(anyString())).willReturn(Optional.of(cities));

        // when
        Optional<List<City>> foundCities = service.findByName("name");

        // then
        then(cityDao).should().findByName(anyString());
        then(cityDao).shouldHaveNoMoreInteractions();

        assertThat(foundCities.orElse(null))
                .withFailMessage("Find cities by name produces a null")
                .isNotNull();
        assertThat(foundCities.get())
                .withFailMessage("City list size is incorrect for find city by name")
                .hasSize(1);
    }

    @Test
    @DisplayName("Test find city by country code")
    void testFindByCountryCode() {
        // given
        given(cityDao.findByCountryCode(anyString())).willReturn(Optional.of(cities));

        // when
        Optional<List<City>> foundCities = service.findByCountryCode("code");

        // then
        then(cityDao).should().findByCountryCode(anyString());
        then(cityDao).shouldHaveNoMoreInteractions();

        assertThat(foundCities.orElse(null))
                .withFailMessage("Find cities by country code produces a null")
                .isNotNull();
        assertThat(foundCities.get())
                .withFailMessage("City list size is incorrect for find city by country code")
                .hasSize(1);
    }

    @Test
    @DisplayName("Test find city by population size larger than given number")
    void testFindAllCitiesWithPopulationGreaterThanX() {
        // given
        given(cityDao.findAllCitiesWithPopulationGreaterThanX(anyLong())).willReturn(Optional.of(cities));

        // when
        Optional<List<City>> foundCities = service.findAllCitiesWithPopulationGreaterThanX(1L);

        // then
        then(cityDao).should().findAllCitiesWithPopulationGreaterThanX(anyLong());
        then(cityDao).shouldHaveNoMoreInteractions();

        assertThat(foundCities.orElse(null))
                .withFailMessage("Find cities by population larger than certain value produces a null")
                .isNotNull();
        assertThat(foundCities.get())
                .withFailMessage("City list size is incorrect for find city by population larger than certain value")
                .hasSize(1);
    }

    @Test
    @DisplayName("Test save city")
    void testSave() {
        // given
        given(cityDao.save(any(City.class))).willReturn(city);

        // when
        City savedCity = service.save(city);

        // then
        then(cityDao).should().save(any(City.class));
        then(cityDao).shouldHaveNoMoreInteractions();

        assertThat(savedCity)
                .withFailMessage("Save new city produces a null")
                .isNotNull();
    }

    @Test
    @DisplayName("Test update city")
    void testUpdate() {
        // given
        given(cityDao.update(any(City.class))).willReturn(city);

        // when
        City savedCity = service.update(city);

        // then
        then(cityDao).should().update(any(City.class));
        then(cityDao).shouldHaveNoMoreInteractions();

        assertThat(savedCity)
                .withFailMessage("Update existing city produces a null")
                .isNotNull();
    }

    @Test
    @DisplayName("Test delete city by id")
    void testDeleteById() {
        // when
        service.deleteById(UUID.randomUUID());

        // then
        then(cityDao).should().deleteById(any(UUID.class));
        then(cityDao).shouldHaveNoMoreInteractions();
    }
}