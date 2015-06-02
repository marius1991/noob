package de.noob.dto;

import java.io.Serializable;

public class RatingTO implements Serializable {
	
	private static final long serialVersionUID = 795414601015492292L;

	private int id;
	
	private int value;
	
	private LocationTO location;
	
	private UserTO owner;

	public RatingTO(int id, int value, LocationTO location, UserTO owner) {
		this.id = id;
		this.value = value;
		this.location = location;
		this.owner = owner;
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

	public LocationTO getLocation() {
		return location;
	}

	public void setLocation(LocationTO location) {
		this.location = location;
	}

	public UserTO getOwner() {
		return owner;
	}

	public void setOwner(UserTO owner) {
		this.owner = owner;
	}
	
	
	
}
