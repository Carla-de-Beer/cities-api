package com.cadebe.cities_api.api.v1.mapper;

import com.cadebe.cities_api.api.v1.model.CityDTO;
import com.cadebe.cities_api.domain.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    City cityDTOToCity(CityDTO source);

    CityDTO cityToCityDTO(City source);
}
