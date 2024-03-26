package ru.nastya.REST_API_application_for_working_with_weather_sensor.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.dto.MeasurementDTO;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.models.Measurement;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.repositories.MeasurementRepository;
import ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors.MeasurementNotFoundError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private MeasurementRepository measurementRepository;
    private SensorService sensorService;

    private ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    public List<MeasurementDTO> findAll() {
        List<MeasurementDTO> list = measurementRepository.findAll().stream()
                .map(this::convertMeasurementToMeasurementDTO).toList();

        return list;
    }

    public int findAllByRainingIsTrue() {
        Optional<List<Measurement>> rainingDays = measurementRepository.findAllByRainingIsTrue();
        if (rainingDays.isEmpty()) {
          throw new MeasurementNotFoundError("В базе данных нет записей о дожливых днях");
        }
        return rainingDays.get().size();
    }

    @Transactional
    public void save(MeasurementDTO measurementDTO) {
        Measurement measurement = convertMeasurementDTOToMeasurement(measurementDTO);
        enrichMeasurement(measurement);

        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {

        LocalDateTime localDateTime = LocalDateTime.now();

        measurement.setDate(localDateTime);
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
    }

    private MeasurementDTO convertMeasurementToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertMeasurementDTOToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }


}