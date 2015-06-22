package de.noob.entities;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

/**
 * @author Tim, Philipp
 */
@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = -682681573951550447L;

	@Id
	@GeneratedValue
	private int id;
	
	@Column(length  = 1000)
	private String text;
	
	@ManyToOne
	private Location location;
	
	@ManyToOne
	private User owner;
	
	private String date;	
	
	public Comment() {
	}
	
	public Comment( User user, String text, Location location){
		this.setOwner(user);
		this.setText(text);
		this.setLocation(location);
		Format format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		this.date = format.format(new Date());
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}	
}
