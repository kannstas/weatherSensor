package ru.nastya.REST_API_application_for_working_with_weather_sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.models.Measurement;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    Optional <List<Measurement>> findAllByRainingIsTrue ();
}
