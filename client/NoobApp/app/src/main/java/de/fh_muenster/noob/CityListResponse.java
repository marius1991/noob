package de.fh_muenster.noob;

import java.util.List;

/**
 * Objekte dieser Klasse werden vom Server an die App gesendet, wenn Städte abgerufen werden
 * @author marius, philipp
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
