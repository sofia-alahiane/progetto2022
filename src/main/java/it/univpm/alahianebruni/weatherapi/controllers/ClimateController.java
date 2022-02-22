package it.univpm.alahianebruni.weatherapi.controllers;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.alahianebruni.openweather.climate.ClimateData;
import it.univpm.alahianebruni.openweather.climate.ClimateStatistics;
import it.univpm.alahianebruni.openweather.climate.DateFormats;
import it.univpm.alahianebruni.weatherapi.services.WeatherService;
@RestController
@RequestMapping("/climate")
public class ClimateController {
	
	@Autowired
	private WeatherService weatherService;
	
	@GetMapping("")
	public List<ClimateData> getClimateFor(
			@RequestParam(name="cities") String cities
			) throws Exception {
		return this.weatherService.getClimateFor(cities);
	}

	@PostMapping("/search/start")
	public String startAutoSearchClimate(
			@RequestParam(name="cities") String cities
			) {
		this.weatherService.startAutoSearchClimateFor(cities);
		return "Start searching...";
	}
	

	/** 
	 * Ferma il thred della chiamata startAutoSearchClimate.
	 * 
	 * @return String
	 */
	@PostMapping("/search/stop")
	public String stopAutoSearchClimate() {
		this.weatherService.stopAutoSearchClimateFor();
		return "Stop searching...";
	}

	@GetMapping("/statistics")
	public List<ClimateStatistics> getStatisticsFor(
			@RequestParam(name="cities") String cities,
			@RequestParam(name="start", required = false) String startDate,
			@RequestParam(name="end", required = false) String endDate
			) throws Exception {
		if (null == startDate) startDate = this.GetNowString("start");
		if (null == endDate) endDate = this.GetNowString("end");
		
		return this.weatherService.getStatisticsFor(cities, startDate, endDate);
	}

	
	/** 
	 * Restituisce una stringa con la data
	 * 
	 * @param timeSpan
	 * @return String
	 */
	private String GetNowString(String timeSpan) {
		DateFormat formatter = new SimpleDateFormat(DateFormats.CLIMATE_DATE_FORMAT);
        return formatter.format(new Date()) + (timeSpan.equals("start") ? " 00:00:00":" 23:59:59");
	}
}
