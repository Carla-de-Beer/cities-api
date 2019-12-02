package com.cadebe.cities_api.repository;

import com.cadebe.cities_api.domain.City;
import com.cadebe.cities_api.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DisplayName("Test CityRepository (IT)")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CityRepositoryIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    CityRepository cityRepository;

    @Test
    @DisplayName("Test find all cities")
    void findAll() {
        List<City> cityList = cityRepository.findAll();

        assertThat(cityList.size())
                .withFailMessage("Could not find list with correct number of cities")
                .isEqualTo(8);
    }

    @Test
    @DisplayName("Test find city by id")
    void findById() {
        UUID id = getFirstCityId();
        String cityName = cityRepository.findAll().get(0).getName();

        Optional<City> foundCity = cityRepository.findById(id);

        foundCity.ifPresent(city -> assertThat(foundCity.get().getName())
                .withFailMessage("Could not find correct city for given id")
                .isEqualTo(cityName));
    }

    @Test
    @DisplayName("Test get city by id (not found)")
    void findByIdNotFound() {
        UUID id = UUID.fromString("a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a");

        Assertions.assertThrows(ResourceNotFoundException.class, () -> cityRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Test
    @DisplayName("Test find city by name")
    void findByName() {
        String cityName = "Bratislava";
        List<City> cityList = cityRepository.findByNameContainingIgnoreCase(cityName);

        assertThat(cityList.get(0).getName())
                .withFailMessage("Could not find city with given name")
                .isEqualTo(cityName);
    }

    @Test
    @DisplayName("Test find city by country code")
    void findByCountryCode() {
        String countryCode = "US";
        List<City> cityList = cityRepository.findByCountryCodeContainingIgnoreCase(countryCode);

        assertThat(cityList.size())
                .withFailMessage("Could not find city list with given country code")
                .isEqualTo(2);
    }

    @Test
    @DisplayName("Test find city with population larger than given size")
    void findAllCitiesWithPopulationGreaterThanX() {
        List<City> cityList = cityRepository.findAllCitiesWithPopulationGreaterThanX(4000000L);

        assertThat(cityList.size())
                .withFailMessage("Could not find city list with given population size")
                .isEqualTo(2);
    }

    @Test
    @DisplayName("Test create new city")
    @Transactional
    void deleteById() {
        UUID id = getFirstCityId();

        List<City> categoryList1 = cityRepository.findAll();

        cityRepository.deleteById(id);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> cityRepository.findById(id).orElseThrow(ResourceNotFoundException::new));

        List<City> categoryList2 = cityRepository.findAll();

        assertThat(categoryList1.size() - 1)
                .withFailMessage("City by given id has not been deleted")
                .isEqualTo(categoryList2.size());
    }

    @Test
    @Transactional
    @DisplayName("Test update existing city")
    void deleteAll() {
        List<City> cityList1 = cityRepository.findAll();

        cityRepository.deleteAll();

        List<City> cityList2 = cityRepository.findAll();

        assertThat(cityList2.size())
                .withFailMessage("Could not delete all cities because list is not empty")
                .isZero();

        assertThat(cityList1.size())
                .withFailMessage("Could not delete all cities (list sizes are equal)")
                .isNotEqualTo(cityList2.size());
    }

    private UUID getFirstCityId() {
        return cityRepository.findAll().get(0).getId();
    }
}