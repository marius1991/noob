package de.noob.dto;

import java.util.ArrayList;

import de.noob.entities.Comment;
import de.noob.entities.Location;
import de.noob.entities.Rating;


public class UserTO extends ReturncodeResponse {

	private static final long serialVersionUID = 6907767619451988547L;
	
	private int id;
	

	private String name;
	
	private String password;
	
	private String email;
	
	private ArrayList<Location> locations;
	
	private ArrayList<Rating> ratings;
	
	private ArrayList<Comment> comments;
	
	public UserTO(int id, String name, String password, String email,
			ArrayList<Location> locations, ArrayList<Rating> ratings,
			ArrayList<Comment> comments) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.locations = locations;
		this.ratings = ratings;
		this.comments = comments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(ArrayList<Rating> ratings) {
		this.ratings = ratings;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	
	
}
