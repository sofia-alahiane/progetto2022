package it.univpm.alahianebruni.openweather.climate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)

public class ClimateWeather {
	private String description;
	
	public ClimateWeather() {

	}
	
	public ClimateWeather(String description) {
		super();
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
