package it.univpm.alahianebruni.openweather.climate;

import java.util.List;

import it.univpm.alahianebruni.weatherapi.services.WeatherService;

public class ClimateThread implements Runnable {
	private boolean doStop = false;

//    private int timeToSleep = 1 * 60 * 60 * 1000;   // 1h di fermo (1 ora x 60 minuti x 60 secondi x 1000 millisecondi)
	private int timeToSleep = 60 * 60 * 1000; // ogni ora

	private WeatherService weatherService;
	private String cities;

	public ClimateThread(WeatherService weatherService, String cities) {
		this.weatherService = weatherService;
		this.cities = cities;
	}

	public synchronized void doStop() {
		this.doStop = true;
		System.out.println("Thread stopped...");
	}

	private synchronized boolean keepRunning() {
		return this.doStop == false;
	}

	@Override
	public void run() {
		while (this.keepRunning()) {
			try {
				List<ClimateData> fbf = this.weatherService.getClimateFor(this.cities);
				this.weatherService.saveClimateDataList(fbf);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.doSleep();
			}
		}
	}

	private void doSleep() {
		try {
			Thread.sleep(this.timeToSleep);
		} catch (InterruptedException e) {
			this.doStop();
			e.printStackTrace();
		}
	}
}
