package com.cadebe.cities_api.service;

import com.cadebe.cities_api.api.v1.model.CityDTO;
import com.cadebe.cities_api.exception.ResourceNotFoundException;
import com.cadebe.cities_api.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
@DisplayName("CityService (IT)")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CityServiceIT {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CityService cityService;

    @Test
    @DisplayName("Test get all cities")
    void getAllCities() {
        List<CityDTO> cities = cityService.findAll();

        assertThat(cities.size())
                .withFailMessage("All of the Bootstrap cities could not be found")
                .isEqualTo(8);
    }

    @Test
    @DisplayName("Test get city by id")
    void getCityById() {
        UUID id = getFirstCityIdValue();
        CityDTO foundCity = cityService.findById(id);

        assertNotNull(foundCity);
    }

    @Test
    @DisplayName("Test get city by id (not found)")
    void getCityByIdNotFound() {
        UUID id = UUID.fromString("a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a");

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cityService.findById(id);
        });
    }

    @Test
    @Transactional
    @DisplayName("Test create new city")
    void createNewCity() {
        String name = "new1";
        List<CityDTO> citiesList1 = cityService.findAll();

        CityDTO cityDTO = CityDTO.builder()
                .name(name)
                .countryCode("ZZ")
                .latitude(0.0)
                .longitude(0.0)
                .build();

        CityDTO savedCityDTO = cityService.saveNewCity(cityDTO);

        List<CityDTO> citiesList2 = cityService.findAll();

        assertThat(savedCityDTO.getName())
                .withFailMessage("New city's name has does not match")
                .isEqualTo(name);

        assertThat(citiesList1.size())
                .withFailMessage("New city has not been added")
                .isEqualTo(citiesList2.size() - 1);
    }

    @Test
    @Transactional
    @DisplayName("Test update existing city by id")
    void updateExistingCity() {
        String updatedName = "sweets";
        UUID id = getFirstCityIdValue();

        // Get a city
        CityDTO originalCity = cityService.findById(id);
        assertNotNull(originalCity);

        String originalName = originalCity.getName();

        CityDTO cityDTO = CityDTO.builder()
                .name(updatedName)
                .build();

        // update it
        cityService.update(id, cityDTO);

        // check the update
        CityDTO updatedCity = cityService.findById(id);

        assertNotNull(updatedCity);

        assertEquals(updatedName, updatedCity.getName(), () -> "Updated city's new name does not match");

        assertThat(originalName)
                .withFailMessage("Updated city's new name has not been updated")
                .isNotEqualTo(updatedCity.getName());
    }

    @Test
    @Transactional
    @DisplayName("Test delete all cities")
    void deleteAllCities() {
        List<CityDTO> cityList = cityService.findAll();

        cityService.deleteAll();

        List<CityDTO> emptyCityList = cityService.findAll();

        assertThat(emptyCityList.size())
                .withFailMessage("Size of deleted list is not zero")
                .isZero();

        assertThat(cityList.size())
                .withFailMessage("Size of the two lists is not equal")
                .isNotEqualTo(emptyCityList.size());
    }

    @Test
    @Transactional
    @DisplayName("Test delete city by id")
    void deleteCityById() {
        List<CityDTO> cityList1 = cityService.findAll();

        UUID id = getFirstCityIdValue();
        cityService.findById(id);

        cityService.deleteById(id);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cityService.findById(id);
        });

        List<CityDTO> cityList2 = cityService.findAll();

        assertThat(cityList1.size() - 1)
                .withFailMessage("City has not been deleted")
                .isEqualTo(cityList2.size());
    }

    private UUID getFirstCityIdValue() {
        return cityService.findAll().get(0).getId();
    }
}
