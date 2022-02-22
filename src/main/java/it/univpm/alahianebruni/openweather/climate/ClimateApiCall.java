package it.univpm.alahianebruni.openweather.climate;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.univpm.alahianebruni.openweather.city.CityModel;

@Async
@Service
public class ClimateApiCall {

	private final RestTemplate restTemplate;

	@Value("${openweatherApiKey}")
	private String apiKey;

	public ClimateApiCall(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Async
	public CompletableFuture<Climate> getClimateForCity(CityModel city) {
		String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&lang=it&units=metric&appid=%s",
				city.getName(), this.apiKey);
		try {
		Climate results = restTemplate.getForObject(url, Climate.class);
		return CompletableFuture.completedFuture(results);
	} catch (Exception ex) {
		System.err.println(ex);
		return null;
	}
	}

}
