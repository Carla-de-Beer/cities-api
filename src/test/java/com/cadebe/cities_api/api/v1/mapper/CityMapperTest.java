package com.cadebe.cities_api.api.v1.mapper;

import com.cadebe.cities_api.api.v1.model.CityDTO;
import com.cadebe.cities_api.domain.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("mapper")
@DisplayName("Test CityMapper")
class CityMapperTest {

    private static final String CITY_NAME = "New Town";
    private static final String COUNTRY_CODE = "ZZ";

    private final CityMapper cityMapper = new CityMapper();

    @Test
    @DisplayName("Test map CityDTO to City")
    void cityDTOToCity() {
        CityDTO cityDTO = CityDTO.builder()
                .name(CITY_NAME)
                .countryCode(COUNTRY_CODE)
                .build();

        City city = cityMapper.cityDTOToCity(cityDTO);

        assertThat(city.getName())
                .withFailMessage("Could not map from City to CityDTO (city name incorrect)")
                .isEqualTo(CITY_NAME);

        assertThat(city.getCountryCode())
                .withFailMessage("Could not map from City to CityDTO (country code incorrect)")
                .isEqualTo(COUNTRY_CODE);
    }

    @Test
    @DisplayName("Test map City to CityDTO")
    void cityToCityDTO() {
        City city = City.builder()
                .name(CITY_NAME)
                .countryCode(COUNTRY_CODE)
                .build();

        CityDTO cityDTO = cityMapper.cityToCityDTO(city);

        assertThat(cityDTO.getName())
                .withFailMessage("Could not map from CityDTO to City (city name incorrect)")
                .isEqualTo(CITY_NAME);

        assertThat(cityDTO.getCountryCode())
                .withFailMessage("Could not map from CityDTO to City (country code incorrect)")
                .isEqualTo(COUNTRY_CODE);
    }
}