package it.univpm.alahianebruni.weatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import it.univpm.alahianebruni.openweather.climate.ClimateHistoryRepository;

@SpringBootApplication
@ComponentScan({ "it.univpm.alahianebruni" })
@EntityScan("it.univpm.alahianebruni")
@EnableAsync
public class ClimateServiceApp {
	/**
	 * Il main viene invocato automaticamente da Spring
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ClimateServiceApp.class, args);
	}

	@Bean
	public ClimateHistoryRepository climateHistory() {
		ClimateHistoryRepository climateHistory = new ClimateHistoryRepository();
		return climateHistory;
	}
}
