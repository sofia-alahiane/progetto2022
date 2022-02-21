package it.univpm.alahianebruni.weatherapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.univpm.alahianebruni.openweather.city.CityModel;

@Service
public class CityService {
	
	public List<CityModel> getCities() {
		List<CityModel> cities = new ArrayList<CityModel>();
		cities.add(new CityModel("Napoli"));
		cities.add(new CityModel("Milano"));
		cities.add(new CityModel("Venezia"));
		cities.add(new CityModel("Ancona"));
		cities.add(new CityModel("Maltignano"));
		cities.add(new CityModel("Palermo"));
		return cities;
	}

	public List<CityModel> findByName(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

}
