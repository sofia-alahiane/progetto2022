package it.univpm.alahianebruni.openweather.climate;

public class ClimateStatistics {
	private String cityName;
	private String start;
	private String end;
	
	private double avgTemperature;
	private double varTemperature;
	private double maxTemperature;
	private double minTemperature;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Double getAvgTemperature() {
		return avgTemperature;
	}

	public void setAvgTemperature(Double avgTemperature) {
		this.avgTemperature = avgTemperature;
	}

	public double getVarTemperature() {
		return varTemperature;
	}

	public void setVarTemperature(double varTemperature) {
		this.varTemperature = varTemperature;
	}

	public double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}
}
