package it.univpm.alahianebruni.openweather.climate;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Climate {

	private String name;
	private ClimateMain main;
	private List<ClimateWeather> weather;
	

	public Climate(String name, ClimateMain main, List<ClimateWeather> weather) {
		this.name = name;
		this.main = main;
		this.weather = weather;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClimateWeather> getWeather() {
		return weather;
	}

	public void setWeather(List<ClimateWeather> weather) {
		this.weather = weather;
	}

	public ClimateMain getMain() {
		return main;
	}

	public void setMain(ClimateMain main) {
		this.main = main;
	}

}
