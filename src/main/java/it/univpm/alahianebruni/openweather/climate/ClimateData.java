package it.univpm.alahianebruni.openweather.climate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)

public class ClimateData {
	
	
	private String cityName;
	private String climateDate;
	private String description;
	private double temperature;
	private double feelsLikeTemperature;
	private double temperatureMin;
	private double temperatureMax;
  	
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getFeelsLikeTemperature() {
		return feelsLikeTemperature;
	}
	public void setFeelsLikeTemperature(double feelsLikeTemperature) {
		this.feelsLikeTemperature = feelsLikeTemperature;
	}
	public double getTemperatureMin() {
		return temperatureMin;
	}
	public void setTemperatureMin(double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}
	public double getTemperatureMax() {
		return temperatureMax;
	}
	public void setTemperatureMax(double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}
	public String getClimateDate() {
		return climateDate;
	}
	public void setClimateDate(String climateDate) {
		this.climateDate = climateDate;
	}
	
}
