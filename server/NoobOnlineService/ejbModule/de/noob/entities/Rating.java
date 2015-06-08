package de.noob.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * 
 * @author Tim
 *
 */
@Entity
public class Rating implements Serializable {

	private static final long serialVersionUID = 8544212870790206964L;

	@Id
	@GeneratedValue
	private int id;
	
	private int value;
	
	@ManyToOne
	private Location location;
	
	@ManyToOne
	private User owner;
	
	public Rating() {
	}
	

	public Rating(User user, int value) {
		this.setOwner(user);
		this.setValue(value);
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
