package com.cadebe.cities_api.service;

import com.cadebe.cities_api.api.v1.model.CityDTO;

import java.util.List;
import java.util.UUID;

public interface CityService {

    List<CityDTO> findAll();

    CityDTO findById(UUID id);

    List<CityDTO> findByName(String name);

    List<CityDTO> findByCountryCode(String countryCode);

    List<CityDTO> findAllCitiesWithPopulationGreaterThanX(long size);

    CityDTO saveNewCity(CityDTO city);

    CityDTO update(UUID uuid, CityDTO city);

    CityDTO patch(UUID uuid, CityDTO city);

    void deleteAll();

    void deleteById(UUID id);
}
