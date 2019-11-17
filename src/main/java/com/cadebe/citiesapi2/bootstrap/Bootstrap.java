package com.cadebe.citiesapi2.bootstrap;

import com.cadebe.citiesapi2.domain.City;
import com.cadebe.citiesapi2.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final CityRepository cityRepository;

    public Bootstrap(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void run(String... args) {
        loadCities();
    }

    private void loadCities() {
        City bratislava = City.builder()
                .name("Bratislava")
                .population(432508)
                .latitude(48.148598)
                .longitude(17.107748)
                .countryCode("SK")
                .build();

        City budapest = City.builder()
                .name("Budapest")
                .population(1763913)
                .latitude(47.497913)
                .longitude(19.040236)
                .countryCode("HU")
                .build();

        City prague = City.builder()
                .name("Prague")
                .population(1298804)
                .latitude(1)
                .longitude(1)
                .countryCode("SK")
                .build();

        City warsaw = City.builder()
                .name("Warsaw")
                .population(1775933)
                .latitude(1)
                .longitude(1)
                .countryCode("SK")
                .build();

        City losAngeles = City.builder()
                .name("Los Angeles")
                .population(4057841)
                .latitude(34.052235)
                .longitude(-118.243683)
                .countryCode("US")
                .build();

        City newYork = City.builder()
                .name("New York")
                .population(8601186)
                .latitude(40.712776)
                .longitude(-74.005974)
                .countryCode("US")
                .build();

        City edinburgh = City.builder()
                .name("Edinburgh")
                .population(530741)
                .latitude(55.953251)
                .longitude(-3.188267)
                .countryCode("GB")
                .build();

        City berlin = City.builder()
                .name("Berlin")
                .population(3556792)
                .latitude(52.520008)
                .longitude(13.404954)
                .countryCode("DE")
                .build();

        cityRepository.save(bratislava);
        cityRepository.save(budapest);
        cityRepository.save(prague);
        cityRepository.save(warsaw);
        cityRepository.save(losAngeles);
        cityRepository.save(newYork);
        cityRepository.save(edinburgh);
        cityRepository.save(berlin);

        log.info("Cities loaded: " + cityRepository.findAll());
    }
}
