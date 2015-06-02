package de.noob.entities;

import javax.persistence.*;



@Entity
public class Rating {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int value;
	
	@ManyToOne
	private Location location;
	
	@ManyToOne
	private User owner;
	
	
	

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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
