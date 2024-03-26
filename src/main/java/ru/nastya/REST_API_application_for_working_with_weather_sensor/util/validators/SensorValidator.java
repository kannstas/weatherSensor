package ru.nastya.REST_API_application_for_working_with_weather_sensor.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.dto.SensorDTO;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.models.Sensor;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.repositories.SensorRepository;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;

        Optional<Sensor> foundSensor = sensorRepository.findByName(sensor.getName());

        if(foundSensor.isPresent()) {
            errors.rejectValue("name"," ", "В базе данных уже присутствует датчик с таким именем");
        }

    }

}