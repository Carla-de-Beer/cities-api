package com.cadebe.citiesapi2.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(description = "City data")
@Entity
@Table(name = "cities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class City {

    @ApiModelProperty(notes = "UUID associated with the city")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    UUID id;

    @ApiModelProperty(notes = "City name", required = true)
    @NotNull
    String name;

    @ApiModelProperty(notes = "Country", required = true)
    @NotNull
    String countryCode;

    @ApiModelProperty(notes = "Population size")
    long population;

    @ApiModelProperty(notes = "Longitude", required = true)
    @NotNull
    double longitude;

    @ApiModelProperty(notes = "Latitude", required = true)
    @NotNull
    double latitude;
}
