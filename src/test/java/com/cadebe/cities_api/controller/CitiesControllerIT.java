package com.cadebe.cities_api.controller;

import com.cadebe.cities_api.repository.CityDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("controller")
@DisplayName("Test find all cities")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CitiesControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    CityDao repository;

    private String url = "/api/v1/cities/";

    @Test
    @DisplayName("Test find all cities")
    void testFindAll() {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode())
                .withFailMessage("Find all cities response status code is not 200 OK")
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Test find all cities (not found)")
    void testFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "a1a1a1a1-a2a2-a3a3-a4a4-a5a5a5a5a5a5", String.class);

        assertThat(response.getStatusCode())
                .withFailMessage("Find city by id (invalid) response status code is not 404 NOT FOUND")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Test find cities by name")
    void testFindByName() {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "name/" + "Paris", String.class);

        assertThat(response.getStatusCode())
                .withFailMessage("Find all cities by name response status code is not 200 OK")
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Test find cities by name (not found)")
    void testFindByNameNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/name/" + "Paariss", String.class);

        assertThat(response.getStatusCode())
                .withFailMessage("Find all cities by name (invalid) response status code is not 404 NOT FOUND")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Test find cities by country code")
    void testFindByCountryCode() {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "country/" + "FR", String.class);

        assertThat(response.getStatusCode())
                .withFailMessage("Find all cities by country code response status code is not 200 OK")
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Test find cities by country code (not found)")
    void testFindByCountryCodeNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "country/" + "huu", String.class);

        assertThat(response.getStatusCode())
                .withFailMessage("Find all cities by country code (invalid) response status code is not 404 NOT FOUND")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}