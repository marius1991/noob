package de.noob.dto;

import java.util.List;

public class CityListResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = -2079105107018093442L;

	private List<String> cities;

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	
}
