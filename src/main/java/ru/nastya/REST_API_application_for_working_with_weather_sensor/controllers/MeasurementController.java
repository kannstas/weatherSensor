package ru.nastya.REST_API_application_for_working_with_weather_sensor.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.dto.MeasurementDTO;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.services.MeasurementService;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.util.validators.MeasurementValidator;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors.SensorErrorResponse;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors.SensorNotFoundError;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private MeasurementService measurementService;

    private MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public List<MeasurementDTO> findAll() {
        return measurementService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public int findCountRainyDays() {
       return measurementService.findAllByRainingIsTrue();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {

        measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new SensorNotFoundError(errorMsg.toString());
        }

        measurementService.save(measurementDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundError error) {
        SensorErrorResponse response = new SensorErrorResponse(
                error.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}