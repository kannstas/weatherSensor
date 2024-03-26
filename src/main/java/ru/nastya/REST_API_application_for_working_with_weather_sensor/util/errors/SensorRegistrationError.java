package ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors;

public class SensorRegistrationError extends RuntimeException{
    public SensorRegistrationError(String message) {
        super(message);
    }
}