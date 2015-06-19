package de.fh_muenster.noob;

import java.io.Serializable;

public class RatingTO implements Serializable {
	
	private static final long serialVersionUID = 795414601015492292L;

	private int id;
	
	private int value;
	
	private int locationId;
	
	private String ownerId;

	public RatingTO() {}

	public RatingTO(int id, int value, int locationId, String ownerId) {
		this.id = id;
		this.value = value;
		this.locationId = locationId;
		this.ownerId = ownerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
}
