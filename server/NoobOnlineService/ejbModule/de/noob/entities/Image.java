package de.noob.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Diese Klasse stellt ein Image in der Datenbank dar. Die eigentlichen Bild-Daten werden als byte[] gespeichert.
 * @author Philipp Ringele
 *
 */
@Entity
public class Image implements Serializable {

	private static final long serialVersionUID = 4216589260463863640L;
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	private User owner;
	
	@ManyToOne
	private Location location;
	
	@Lob
	private byte[] data;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
