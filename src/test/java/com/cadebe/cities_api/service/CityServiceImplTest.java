package com.cadebe.cities_api.service;

import com.cadebe.cities_api.api.v1.mapper.CityMapper;
import com.cadebe.cities_api.api.v1.model.CityDTO;
import com.cadebe.cities_api.domain.City;
import com.cadebe.cities_api.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@Tag("service")
@DisplayName("Test CityServiceImpl")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CityServiceImplTest {

    private static final String NAME_1 = "city1";
    private static final String ID_1 = "6c86560e-8afd-4e50-8919-f8e9963726e6";
    private static final String COUNTRY_CODE_1 = "ZZ";
    private static final Long POPULATION_1 = 1000L;

    private static final String NAME_2 = "city2";
    private static final String ID_2 = "1c4272a1-8afd-4e50-f919-62e9163712c5";
    private static final String COUNTRY_CODE_2 = "ZZ";
    private static final Long POPULATION_2 = 500L;

    @Mock
    CityRepository cityRepository;

    private CityServiceImpl cityService;

    private City city1;
    private City city2;

    @BeforeEach
    void setUp() {
        CityMapper cityMapper = new CityMapper();
        cityService = new CityServiceImpl(cityRepository, cityMapper);

        city1 = City.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(POPULATION_1)
                .build();

        city2 = City.builder()
                .id(UUID.fromString(ID_2))
                .name(NAME_2)
                .countryCode(COUNTRY_CODE_2)
                .population(POPULATION_2)
                .build();
    }

    @Test
    @DisplayName("Test find all cities")
    void findAll() {
        List<City> list = Arrays.asList(new City(), new City());

        when(cityRepository.findAll()).thenReturn(list);

        List<CityDTO> result = cityService.findAll();

        then(cityRepository).should().findAll();

        assertThat(result.size())
                .withFailMessage("Could not find list of cities")
                .isEqualTo(2);

        verify(cityRepository, times(1)).findAll();
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Test find city by id")
    void findById() {
        when(cityRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.ofNullable(city1));

        CityDTO foundCity = cityService.findById(UUID.fromString(ID_1));

        then(cityRepository).should().findById(any(UUID.class));

        assertThat(foundCity.getName())
                .withFailMessage("Could not find city with given id")
                .isEqualTo(NAME_1);

        verify(cityRepository, times(1)).findById(any(UUID.class));
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Test find city by name")
    void findByName() {
        when(cityRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(Collections.singletonList(city1));

        List<CityDTO> list = cityService.findByName(NAME_1);

        then(cityRepository).should().findByNameContainingIgnoreCase(anyString());

        assertThat(list.size())
                .withFailMessage("Could not find correct city list for the with given name")
                .isEqualTo(1);

        assertThat(list.get(0).getName())
                .withFailMessage("Could not find correct city for given name")
                .isEqualTo(NAME_1);

        assertThat(list.get(0).getCountryCode())
                .withFailMessage("Could not find correct city for given name")
                .isEqualTo(COUNTRY_CODE_1);

        verify(cityRepository, times(1)).findByNameContainingIgnoreCase(anyString());
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Test find city by country code")
    void findByCountryCode() {
        when(cityRepository.findByCountryCodeContainingIgnoreCase(anyString())).thenReturn(Arrays.asList(city1, city2));

        List<CityDTO> list = cityService.findByCountryCode(COUNTRY_CODE_1);

        then(cityRepository).should().findByCountryCodeContainingIgnoreCase(anyString());

        assertThat(list.size())
                .withFailMessage("Could not find correct city list for given country code")
                .isEqualTo(2);

        assertThat(list.get(0).getName())
                .withFailMessage("Could not find correct city for given name")
                .isEqualTo(NAME_1);

        assertThat(list.get(0).getCountryCode())
                .withFailMessage("Could not find correct city for given name")
                .isEqualTo(COUNTRY_CODE_1);

        verify(cityRepository, times(1)).findByCountryCodeContainingIgnoreCase(anyString());
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Test find city with population larger than given size")
    void findAllCitiesWithPopulationGreaterThanX() {
        when(cityRepository.findAllCitiesWithPopulationGreaterThanX(anyLong())).thenReturn(Collections.singletonList(city1));

        List<CityDTO> list = cityService.findAllCitiesWithPopulationGreaterThanX(750L);

        then(cityRepository).should().findAllCitiesWithPopulationGreaterThanX(anyLong());

        assertThat(list.size())
                .withFailMessage("Could not find correct city list for given population size")
                .isEqualTo(1);

        assertThat(list.get(0).getName())
                .withFailMessage("Could not find correct city for given population size")
                .isEqualTo(NAME_1);

        assertThat(list.get(0).getCountryCode())
                .withFailMessage("Could not find correct city for given population size")
                .isEqualTo(COUNTRY_CODE_1);

        verify(cityRepository, times(1)).findAllCitiesWithPopulationGreaterThanX(anyLong());
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Test save new city")
    void save() {
        CityDTO cityDTO = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(POPULATION_1)
                .build();

        when(cityRepository.save(any(City.class))).thenReturn(city1);

        CityDTO savedCityDTO = cityService.saveNewCity(cityDTO);

        // 'should' defaults to times = 1
        then(cityRepository).should().save(any(City.class));

        assertThat(savedCityDTO.getName())
                .withFailMessage("Could not correctly save city")
                .contains(NAME_1);

        verify(cityRepository, times(1)).save(any(City.class));
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Test update existing city")
    void update() {
        CityDTO cityDTO = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(1234L)
                .build();

        given(cityRepository.save(any(City.class))).willReturn(city1);

        CityDTO savedCityDTO = cityService.update(UUID.fromString(ID_1), cityDTO);

        // 'should' defaults to times = 1
        then(cityRepository).should().save(any(City.class));

        assertThat(savedCityDTO.getName())
                .withFailMessage("Could not correctly update city")
                .contains(NAME_1);

        verify(cityRepository, times(1)).save(any(City.class));
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Test delete all cities")
    void deleteAll() {
        cityService.deleteAll();

        then(cityRepository).should().deleteAll();

        verify(cityRepository, times(1)).deleteAll();
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Test delete city by id")
    void deleteById() {
        cityService.deleteById(UUID.fromString(ID_1));

        then(cityRepository).should().deleteById(any(UUID.class));

        verify(cityRepository, times(1)).deleteById(any(UUID.class));
        verifyNoMoreInteractions(cityRepository);
    }
}