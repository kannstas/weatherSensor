package ru.nastya.REST_API_application_for_working_with_weather_sensor.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Поле name не должно быть пустым")
    @Size(min = 3, max = 30, message = "Длина name должна быть от 3 до 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}