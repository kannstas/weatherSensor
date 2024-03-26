package ru.nastya.REST_API_application_for_working_with_weather_sensor.dto;

import jakarta.validation.constraints.*;

public class MeasurementDTO {
    @NotNull(message = "поле не должно быть пустым")
    @Min(value = -100, message = "значение температуры не должно быть меньше 100")
    @Max(value = 100, message = " значение температуры не должно быть больше 100")
    private Double value;
    @NotNull(message = "поле не должно быть пустым")
    private Boolean raining;
    @NotNull(message = "поле не должно быть пустым")
    private SensorDTO sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}