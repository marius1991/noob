package de.noob.dto;

import java.util.List;

/**
 * Diese Klasse erweitert ReturnCodeResponse, sodass auch eine Liste mit LocationTOs zurückgegeben werden kann.
 * @author Philipp Ringele
 *
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
