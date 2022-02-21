package it.univpm.alahianebruni.weatherapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.univpm.alahianebruni.openweather.city.CityModel;
import it.univpm.alahianebruni.openweather.climate.ClimateData;
import it.univpm.alahianebruni.openweather.climate.ClimateStatistics;
import it.univpm.alahianebruni.weatherapi.controllers.CityController;
import it.univpm.alahianebruni.weatherapi.controllers.ClimateController;

/**Questa classe testa alcuni metodi del controller
 * 
 * @author sofialahiane
 *
 */

@SpringBootTest
public class ClimateServiceAppTests {

	@Autowired
    private CityController cityController;

	@Autowired
    private ClimateController climateController;

	@Test
	void cityModelTest (){
		CityModel test = new CityModel("Venezia");
		assertEquals("Venezia", test.getName());
	}

	@Test
	void citiesLoadTest() throws Exception {
        List<CityModel> cities = this.cityController.getCities();
        assertNotNull(cities);
	}

	@Test
	void getClimateForTest() throws Exception{
        List<ClimateData> climate = this.climateController.getClimateFor("Milano,Venezia");
        assertNotNull(climate);
	}

	@Test
	void startAutoSearchClimateTest() {
        String result = this.climateController.startAutoSearchClimate("Milano,Venezia");
        assertEquals("Climate autolookup started...", result);
	}

	@Test
	void stopAutoSearchClimateTest() {
        String result = this.climateController.stopAutoSearchClimate();
        assertEquals("Climate autolookup stopped...", result);
	}
	
	@Test
	void getStatisticsForTest() throws Exception {
		List<ClimateStatistics> climateStat = this.climateController.getStatisticsFor("Ancona", null, null);
		assertNotNull(climateStat);
	}
}
	
