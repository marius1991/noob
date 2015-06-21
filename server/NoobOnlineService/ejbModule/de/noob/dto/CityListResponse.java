package de.noob.dto;

import java.util.List;

/**
 * Diese Klasse erweitert ReturnCodeResponse, sodass auch eine Liste mit City-Strings zurückgegeben werden kann.
 * @author Philipp Ringele
 *
 */
public class CityListResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = -2079105107018093442L;

	private List<String> cities = null;

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	
}
