package ru.nastya.REST_API_application_for_working_with_weather_sensor.util.errors;

public class MeasurementErrorResponse {
    private String message;
    public MeasurementErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}