package com.cadebe.cities_api.service;

import com.cadebe.cities_api.api.v1.mapper.CityMapper;
import com.cadebe.cities_api.api.v1.model.CityDTO;
import com.cadebe.cities_api.domain.City;
import com.cadebe.cities_api.exception.ResourceNotFoundException;
import com.cadebe.cities_api.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityDTO> findAll() {
        return cityRepository.findAll()
                .stream()
                .map(cityMapper::cityToCityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CityDTO findById(UUID id) {
        return cityRepository.findById(id)
                .map(cityMapper::cityToCityDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<CityDTO> findByName(String name) {
        return cityRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(cityMapper::cityToCityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> findByCountryCode(String countryCode) {
        return cityRepository.findByCountryCodeContainingIgnoreCase(countryCode)
                .stream()
                .map(cityMapper::cityToCityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> findAllCitiesWithPopulationGreaterThanX(long size) {
        return cityRepository.findAllCitiesWithPopulationGreaterThanX(size)
                .stream()
                .map(cityMapper::cityToCityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CityDTO saveNewCity(CityDTO city) {
        return saveAndReturnDTO(cityMapper.cityDTOToCity(city));
    }

    @Override
    public CityDTO update(UUID uuid, CityDTO cityDTO) {
        City city = cityMapper.cityDTOToCity(cityDTO);
        city.setId(uuid);

        return saveAndReturnDTO(city);
    }

    @Override
    public CityDTO patch(UUID uuid, CityDTO city) {
        return null;
    }

    @Override
    public void deleteAll() {
        cityRepository.deleteAll();
    }

    @Override
    public void deleteById(UUID id) {
        try {
            cityRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    private CityDTO saveAndReturnDTO(City city) {
        City savedCity = cityRepository.save(city);
        return cityMapper.cityToCityDTO(savedCity);
    }
}
