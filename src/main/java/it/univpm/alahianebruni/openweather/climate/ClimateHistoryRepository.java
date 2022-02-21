package it.univpm.alahianebruni.openweather.climate;

import java.util.ArrayList;
import java.util.List;

public class ClimateHistoryRepository {
	private List<ClimateData> climateHistory;

	public ClimateHistoryRepository() {
		this.climateHistory = new ArrayList<>();
	}

	public List<ClimateData> getClimateHistory() {
		return climateHistory;
	}

	public void insertClimateHistory(List<ClimateData> climateHistory) {
		this.climateHistory.addAll(climateHistory);
	}

}
