package com.cadebe.citiesapi2.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

    @ApiModelProperty(notes = "City id")
    UUID id;

    @ApiModelProperty(notes = "City name")
    String name;

    @ApiModelProperty(notes = "City country")
    String countryCode;

    @ApiModelProperty(notes = "City population size")
    long population;

    @ApiModelProperty(notes = " City longitude")
    double longitude;

    @ApiModelProperty(notes = " City latitude")
    double latitude;
}
