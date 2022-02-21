package it.univpm.alahianebruni.weatherapi.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univpm.alahianebruni.openweather.city.CityModel;
import it.univpm.alahianebruni.openweather.climate.Climate;
import it.univpm.alahianebruni.openweather.climate.ClimateApiCall;
import it.univpm.alahianebruni.openweather.climate.ClimateData;
import it.univpm.alahianebruni.openweather.climate.ClimateHistoryRepository;
import it.univpm.alahianebruni.openweather.climate.ClimateStatistics;
import it.univpm.alahianebruni.openweather.climate.ClimateThread;
import it.univpm.alahianebruni.openweather.climate.DateFormats;



@Service
public class WeatherService {

	@Autowired()
	private ClimateApiCall climateApiCall;

	@Autowired()
	private ClimateHistoryRepository climateHistoryRepository;

	private ClimateThread climateThread;

	public WeatherService(ClimateApiCall climateApiCall) {
		this.climateApiCall = climateApiCall;
	}
	
	public List<ClimateData> getClimateFor(String cities) throws ExecutionException, InterruptedException {
		List<CityModel> cityList = getCityModel(cities);
		return (null == cityList || cityList.isEmpty()) ? null : this.GetClimate(cityList);
	}

	private List<ClimateData> GetClimate(List<CityModel> cities) throws InterruptedException, ExecutionException{
		List<ClimateData> fcl = new ArrayList<>();

		List<CompletableFuture<Climate>> cfl = new ArrayList<>();
		
		for(CityModel city : cities) {
			CompletableFuture<Climate> f = climateApiCall.getClimateForCity(city);
			cfl.add(f);
		}
		
		for(CompletableFuture<Climate> f : cfl) {
			CompletableFuture.anyOf(f).join();
			Climate fcg = f.get();

			ClimateData fc = new ClimateData();
			fc.setCityName(fcg.getName());
			fc.setDescription(fcg.getWeather().get(0).getDescription());
			fc.setClimateDate(this.GetNowString());
			fc.setTemperature(fcg.getMain().getTemp());
			fc.setFeelsLikeTemperature(fcg.getMain().getFeels_like());
			fc.setTemperatureMin(fcg.getMain().getTemp_min());
			fc.setTemperatureMax(fcg.getMain().getTemp_max());

			fcl.add(fc);
		}
	
		return fcl;
	}

	/**
	 * Genera le previsioni fittizie (servono per i test in fase di sviluppo)
	 * 
	 * @param cities
	 * @param seedingDate
	 * @return List<ClimateData>
	 */
	private List<ClimateData> GetSeededClimate(List<CityModel> cities, Date seedingDate) {
		List<ClimateData> fcl = new ArrayList<ClimateData>();
		ClimateData fc;
		for(CityModel city : cities) {
			fc= new ClimateData();
			// TODO
			fcl.add(fc);
		}
		return fcl;
	}
	
	
	public void startAutoSearchClimateFor(String cities) {
		this.stopAutoSearchClimateFor();
		this.climateThread = new ClimateThread(this, cities);
		Thread t = new Thread(this.climateThread);
		t.start();
		System.out.println("Begin autolookup forecast...");
	}

	 /**
	  * Ferma il processo automatico di recupero dei dati dal sito www.openweathermap.org
	  */
	public void stopAutoSearchClimateFor() {
		try {
			if (null != this.climateThread) this.climateThread.doStop();
			System.out.println("End autolookup forecast...");	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Salva sia i dati reali che fittizi nella memoria
	 */
	public void saveClimateDataList(List<ClimateData> ClimateDataList) {
		this.climateHistoryRepository.insertClimateHistory(ClimateDataList);
		System.out.println("Forecasts list successfully saved!");
	}

	public List<ClimateStatistics> getStatisticsFor(
			String cities,
			String startDate,
			String endDate
			) throws Exception {
		List<CityModel> cityList = getCityModel(cities);
		Date start = new SimpleDateFormat(DateFormats.CLIMATE_DATETIME_FORMAT).parse(startDate);
		Date end = new SimpleDateFormat(DateFormats.CLIMATE_DATETIME_FORMAT).parse(endDate);
		return this.createStatistics(cityList, start, end);
	}
	
	private List<ClimateStatistics> createStatistics(List<CityModel> cities, Date startDate, Date endDate) throws ParseException {
		List<ClimateStatistics> climateStatistics = new ArrayList<>();

		// filtriamo i dati dello storico
		List<ClimateData> climateForStatistic = new ArrayList<>();
		List<ClimateData> climateHistory = this.climateHistoryRepository.getClimateHistory();
		for (ClimateData climate : climateHistory) {
			// consideriamo solo le citta' passate
			String climateCityName = climate.getCityName();
			boolean createStatisticForCity = false;
			for (CityModel city : cities) {
				if (city.getName().equalsIgnoreCase(climateCityName)) {
					createStatisticForCity = true;
					break;
				}
			}

			// consideriamo solo le date passate
			Date climateDate = new SimpleDateFormat(DateFormats.CLIMATE_DATETIME_FORMAT).parse(climate.getClimateDate());
			if (climateDate.before(startDate) || climateDate.after(endDate)) {
				continue;
			}

			if (createStatisticForCity) {
				climateForStatistic.add(climate);
			}
		}

		// creiamo le statistiche considerando solo i dati filtrati prima
		if (!climateForStatistic.isEmpty()) {
			for (CityModel city : cities) {
				// dati climate della citta'
				List<ClimateData> climateDataForCurrentCity = new ArrayList<>();
				for (ClimateData cityClimate : climateForStatistic) {
					String climateCityName = cityClimate.getCityName();
					if (city.getName().equalsIgnoreCase(climateCityName)) {
						climateDataForCurrentCity.add(cityClimate);
					}
				}

			
				if (climateDataForCurrentCity.isEmpty()) {
					continue;
					}
				/** 
				 calcolo statistiche (temperatura media, massima e minima, la varianza)
				 */
				 
				Double temperatureSum = 0.0;
				Double temperatureMin = climateDataForCurrentCity.get(0).getTemperatureMin();
				Double temperatureMax = climateDataForCurrentCity.get(0).getTemperatureMax();
				for (ClimateData climateData : climateDataForCurrentCity) {
					if (temperatureMin > climateData.getTemperatureMin()) {
						temperatureMin= climateData.getTemperatureMin();
					}
					if (temperatureMax < climateData.getTemperatureMax()) {
						temperatureMax= climateData.getTemperatureMax();
					}
					temperatureSum += climateData.getTemperature();
				}
				Double avgTemp = !climateDataForCurrentCity.isEmpty() ? temperatureSum / climateDataForCurrentCity.size() : null;
				Double varTemp = temperatureMax - temperatureMin;

				// inseriamo le statistiche per citta' 
				ClimateStatistics cityStatistics = new ClimateStatistics();
				cityStatistics.setCityName(city.getName());
				cityStatistics.setStart(this.GetDateString(startDate));
				cityStatistics.setEnd(this.GetDateString(endDate));
				cityStatistics.setMaxTemperature(temperatureMax);
				cityStatistics.setMinTemperature(temperatureMin);
				cityStatistics.setAvgTemperature(avgTemp);
				cityStatistics.setVarTemperature(varTemp);
				climateStatistics.add(cityStatistics);
			}
		}
		
		return climateStatistics;
	}

	private List<CityModel> getCityModel(String cities) {
		if (null == cities || cities.length() == 0) {
			return null;
		}
		List<String> cityStringList = Arrays.asList(cities.split(","));
		List<CityModel> cityList = new ArrayList<>();
		for (String city : cityStringList) {
			cityList.add(new CityModel(city));
		}
		return cityList;
	}

	private String GetNowString() {
		DateFormat formatter = new SimpleDateFormat(DateFormats.CLIMATE_DATETIME_FORMAT);
		return formatter.format(new Date());
	}

	private String GetDateString(Date date) {
		DateFormat formatter = new SimpleDateFormat(DateFormats.CLIMATE_DATETIME_FORMAT);
		return formatter.format(date);
	}
}
