package ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors;

public class SensorNotFoundError extends RuntimeException{
    public SensorNotFoundError(String message) {
        super(message);
    }
}