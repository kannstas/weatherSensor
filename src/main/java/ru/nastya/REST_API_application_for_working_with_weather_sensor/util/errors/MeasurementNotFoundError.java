package ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors;

public class MeasurementNotFoundError extends RuntimeException {
    public MeasurementNotFoundError(String message) {
        super(message);
    }
}