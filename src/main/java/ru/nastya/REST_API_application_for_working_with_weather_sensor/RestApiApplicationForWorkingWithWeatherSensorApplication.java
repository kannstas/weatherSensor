package ru.nastya.REST_API_application_for_working_with_weather_sensor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestApiApplicationForWorkingWithWeatherSensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplicationForWorkingWithWeatherSensorApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
