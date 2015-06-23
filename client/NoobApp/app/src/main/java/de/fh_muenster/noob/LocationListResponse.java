package de.fh_muenster.noob;

import java.util.List;

/**
 * Objekte dieser Klasse werden vom Server an die App gesendet, wenn Locations abgerufen werden
 * @author marius, philipp
 */
public class LocationListResponse extends ReturnCodeResponse {
	
	private static final long serialVersionUID = 7195725464159927947L;
	
	private List<LocationTO> locations = null;
	
	
	public List<LocationTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationTO> locations) {
		this.locations = locations;
	}

}
