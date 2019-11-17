package com.cadebe.citiesapi2.api.v1.mapper;

import com.cadebe.citiesapi2.api.v1.model.CityDTO;
import com.cadebe.citiesapi2.domain.City;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    @Synchronized
    @Nullable
    public City cityDTOToCity(CityDTO source) {
        if (source == null) {
            return null;
        }
        return City.builder()
                .id(source.getId())
                .name(source.getName())
                .countryCode(source.getCountryCode())
                .population(source.getPopulation())
                .latitude(source.getLatitude())
                .longitude(source.getLongitude())
                .build();
    }

    @Synchronized
    @Nullable
    public CityDTO cityToCityDTO(City source) {
        if (source == null) {
            return null;
        }
        return CityDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .countryCode(source.getCountryCode())
                .population(source.getPopulation())
                .latitude(source.getLatitude())
                .longitude(source.getLongitude())
                .build();
    }
}
