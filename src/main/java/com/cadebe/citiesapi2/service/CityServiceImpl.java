package com.cadebe.citiesapi2.service;

import com.cadebe.citiesapi2.api.v1.mapper.CityMapper;
import com.cadebe.citiesapi2.api.v1.model.CityDTO;
import com.cadebe.citiesapi2.domain.City;
import com.cadebe.citiesapi2.exception.ResourceNotFoundException;
import com.cadebe.citiesapi2.repository.CityRepository;
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
        return cityRepository.findByName(name)
                .stream()
                .map(cityMapper::cityToCityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> findByCountryCode(String countryCode) {
        return cityRepository.findByCountryCode(countryCode)
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
        City savedCustomer = cityRepository.save(city);
        return cityMapper.cityToCityDTO(savedCustomer);
    }
}
