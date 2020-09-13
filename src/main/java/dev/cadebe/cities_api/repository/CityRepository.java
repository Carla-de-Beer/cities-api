package dev.cadebe.cities_api.repository;

import dev.cadebe.cities_api.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

    List<City> findByNameContainingIgnoreCase(@Param("name") String name);

    List<City> findByCountryCodeContainingIgnoreCase(@Param("countryCode") String countryCode);

    @Query(value = "SELECT * FROM cities WHERE population > ?1", nativeQuery = true)
    List<City> findAllCitiesWithPopulationGreaterThanX(long size);
}
