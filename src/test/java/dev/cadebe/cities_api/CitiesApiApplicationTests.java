package dev.cadebe.cities_api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("integration")
@DisplayName("Test CitiesApiApplication (IT)")
class CitiesApiApplicationTests {

    @Test
    @DisplayName("Test contextLoads")
    void contextLoads() {
    }
}
