package ru.nastya.REST_API_application_for_working_with_weather_sensor.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.dto.MeasurementDTO;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.models.Measurement;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.models.Sensor;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.repositories.SensorRepository;

import java.util.Optional;


@Component
public class MeasurementValidator implements Validator {

    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        Optional<Sensor> foundSensor = sensorRepository.findByName(measurementDTO.getSensor().getName());// TODO изменить
        if (foundSensor.isEmpty()) {
            errors.rejectValue("sensor", " ", "В базе данных не существует датчика с таким именем. Перед добавлением измерений необходимо зарегистрировать датчик");
        }
    }
}