package de.noob.dto;

import java.util.List;

public class LocationListResponse extends ReturnCodeResponse {
	
	private static final long serialVersionUID = 7195725464159927947L;
	
	private List<LocationTO> locations;
	
	
	public List<LocationTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationTO> locations) {
		this.locations = locations;
	}

}
