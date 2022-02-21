package it.univpm.alahianebruni.weatherapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.alahianebruni.openweather.city.CityModel;
import it.univpm.alahianebruni.weatherapi.services.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

	@Autowired
	private CityService cityService;

	@GetMapping("")
	public List<CityModel> getCities() throws Exception {
		return this.cityService.getCities();

	}

}
