package dev.cadebe.cities_api.controller.v1;

import dev.cadebe.cities_api.api.v1.model.CityDTO;
import dev.cadebe.cities_api.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = {"City Controller"})
@SwaggerDefinition(tags = {
        @Tag(name = "City Controller", description = "City Controller for the Cities API")
})
@RestController
@RequestMapping({CityController.BASE_URL, CityController.BASE_URL + "/"})
@RequiredArgsConstructor
public class CityController {

    public static final String BASE_URL = "/api/v1/cities";

    private final CityService cityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> getAllCities() {
        return cityService.findAll();
    }

    @GetMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CityDTO getCityById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return cityService.findById(uuid);
    }

    @GetMapping({"name/{name}"})
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> getCitiesByName(@PathVariable String name) {
        return cityService.findByName(name);
    }

    @GetMapping({"country/{countryCode}"})
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> getAllCitiesByCountryCode(@PathVariable String countryCode) {
        return cityService.findByCountryCode(countryCode.toUpperCase());
    }

    @GetMapping({"populationSize/{size}"})
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> getAllCitiesWithPopulationGreaterThanX(@PathVariable long size) {
        return cityService.findAllCitiesWithPopulationGreaterThanX(size);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO createNewCity(@RequestBody CityDTO city) {
        return cityService.saveNewCity(city);
    }

    @PutMapping({"{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CityDTO updateExistingCity(@PathVariable String id, @RequestBody CityDTO city) {
        UUID uuid = UUID.fromString(id);
        return cityService.update(uuid, city);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        cityService.deleteAll();
    }

    @DeleteMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        cityService.deleteById(uuid);
    }
}
