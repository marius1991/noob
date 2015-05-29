package de.noob.dto;

import java.util.List;

public class LocationListResponse extends ReturncodeResponse {
	
	private static final long serialVersionUID = 7195725464159927947L;
	
	private List<LocationTO> locations;
	
	public LocationListResponse() {
		// TODO Auto-generated constructor stub
	}

	public List<LocationTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationTO> locations) {
		this.locations = locations;
	}

}
