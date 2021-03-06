package dev.cadebe.cities_api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@Tag("integration")
@DisplayName("CityController (IT)")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CityControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void findAllCities() {

    }
}
