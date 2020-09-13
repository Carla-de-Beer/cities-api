package dev.cadebe.cities_api.controller;

import dev.cadebe.cities_api.api.v1.model.CityDTO;
import dev.cadebe.cities_api.controller.v1.CityController;
import dev.cadebe.cities_api.exception.ResourceNotFoundException;
import dev.cadebe.cities_api.exception.RestResponseEntityExceptionHandler;
import dev.cadebe.cities_api.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("controller")
@DisplayName("CityController")
@ExtendWith(MockitoExtension.class)
class CityControllerTest extends AbstractRestControllerTest {

    private static final String NAME_1 = "city1";
    private static final String ID_1 = "6c86560e-8afd-4e50-8919-f8e9963726e6";
    private static final String COUNTRY_CODE_1 = "ZZ";
    private static final Long POPULATION_1 = 1000L;

    private static final String NAME_2 = "city2";
    private static final String ID_2 = "1c4272a1-8afd-4e50-f919-62e9163712c5";
    private static final String COUNTRY_CODE_2 = "ZZ";
    private static final Long POPULATION_2 = 500L;

    @Mock
    CityService cityService;

    @InjectMocks
    CityController cityController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cityController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Test find all cities")
    void getAllCities() throws Exception {
        CityDTO cityDTO1 = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(POPULATION_1)
                .build();

        CityDTO cityDTO2 = CityDTO.builder()
                .id(UUID.fromString(ID_2))
                .name(NAME_2)
                .countryCode(COUNTRY_CODE_2)
                .population(POPULATION_2)
                .build();

        List<CityDTO> cities = Arrays.asList(cityDTO1, cityDTO2);

        when(cityService.findAll()).thenReturn(cities);

        mockMvc.perform(get(getCitiesURL())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(cityService).should().findAll();

        verify(cityService, times(1)).findAll();
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test find city by id")
    void getCityById() throws Exception {
        CityDTO cityDTO = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(POPULATION_1)
                .build();

        when(cityService.findById(any(UUID.class))).thenReturn(cityDTO);

        mockMvc.perform(get(getCitiesURL() + ID_1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)));

        then(cityService).should().findById(any(UUID.class));

        verify(cityService, times(1)).findById(any(UUID.class));
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test get city by id (not found)")
    void getCityByIdNotFound() throws Exception {
        when(cityService.findById(any(UUID.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CityController.BASE_URL + "/a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        then(cityService).should().findById(any(UUID.class));

        verify(cityService, times(1)).findById(any(UUID.class));
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test find city by name")
    void getCitiesByName() throws Exception {
        CityDTO cityDTO = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(POPULATION_1)
                .build();

        when(cityService.findByName(anyString())).thenReturn(Collections.singletonList(cityDTO));

        mockMvc.perform(get(getCitiesURL() + "name/NewTown")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(cityService).should().findByName(anyString());

        verify(cityService, times(1)).findByName(anyString());
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test find city by country code")
    void getAllCitiesByCountryCode() throws Exception {
        CityDTO cityDTO1 = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(POPULATION_1)
                .build();

        List<CityDTO> cities = Collections.singletonList(cityDTO1);

        when(cityService.findByCountryCode(anyString())).thenReturn(cities);

        mockMvc.perform(get(getCitiesURL() + "country/ZZ")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(cityService).should().findByCountryCode(anyString());

        verify(cityService, times(1)).findByCountryCode(anyString());
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test find city with population larger than given size")
    void getAllCitiesWithPopulationGreaterThanX() throws Exception {
        CityDTO cityDTO1 = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(POPULATION_1)
                .build();

        CityDTO cityDTO2 = CityDTO.builder()
                .id(UUID.fromString(ID_2))
                .name(NAME_2)
                .countryCode(COUNTRY_CODE_2)
                .population(POPULATION_2)
                .build();

        List<CityDTO> cities = Arrays.asList(cityDTO1, cityDTO2);

        when(cityService.findAllCitiesWithPopulationGreaterThanX(anyLong())).thenReturn(cities);

        mockMvc.perform(get(getCitiesURL() + "populationSize/500")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(cityService).should().findAllCitiesWithPopulationGreaterThanX(anyLong());

        verify(cityService, times(1)).findAllCitiesWithPopulationGreaterThanX(anyLong());
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test create new city")
    void createNewCity() throws Exception {
        CityDTO cityDTO1 = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(POPULATION_1)
                .build();

        given(cityService.saveNewCity(cityDTO1)).willReturn(cityDTO1);

        mockMvc.perform(post(getCitiesURL())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cityDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(cityDTO1.getName())));

        then(cityService).should().saveNewCity(any(CityDTO.class));

        verify(cityService, times(1)).saveNewCity(any(CityDTO.class));
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test update existing city")
    void updateExistingCity() throws Exception {
        CityDTO cityDTO1 = CityDTO.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .countryCode(COUNTRY_CODE_1)
                .population(1234L)
                .build();

        given(cityService.update(any(UUID.class), any(CityDTO.class))).willReturn(cityDTO1);

        mockMvc.perform(put(getCitiesURL() + ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cityDTO1)))
                .andExpect(status().isNoContent());

        then(cityService).should().update(any(UUID.class), any(CityDTO.class));

        verify(cityService, times(1)).update(any(UUID.class), any(CityDTO.class));
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test delete all cities")
    void deleteAll() throws Exception {
        mockMvc.perform(delete(getCitiesURL()))
                .andExpect(status().isOk());

        then(cityService).should().deleteAll();

        verify(cityService, times(1)).deleteAll();
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("Test delete city by id")
    void deleteById() throws Exception {
        mockMvc.perform(delete(getCitiesURL() + ID_1))
                .andExpect(status().isOk());

        then(cityService).should().deleteById(any(UUID.class));

        verify(cityService, times(1)).deleteById(any(UUID.class));
        verifyNoMoreInteractions(cityService);
    }

    private String getCitiesURL() {
        return CityController.BASE_URL + "/";
    }
}