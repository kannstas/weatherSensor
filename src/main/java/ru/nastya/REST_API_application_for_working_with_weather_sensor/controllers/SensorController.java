package ru.nastya.REST_API_application_for_working_with_weather_sensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.dto.SensorDTO;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.models.Sensor;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.services.SensorService;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors.SensorErrorResponse;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors.SensorRegistrationError;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.util.validators.SensorValidator;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private SensorService sensorService;

    private SensorValidator sensorValidator;
    private ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> serviceRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                          BindingResult bindingResult) {
        sensorValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {

            StringBuilder errorMsg = new StringBuilder();

            List <FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorRegistrationError(errorMsg.toString());
        }

        sensorService.save(convertSensorDTOToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException (SensorRegistrationError error) {
        SensorErrorResponse response = new SensorErrorResponse(
                error.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertSensorDTOToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertSensorToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}