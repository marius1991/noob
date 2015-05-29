package de.noob.dto;

import java.util.ArrayList;

import de.noob.entities.Comment;
import de.noob.entities.Rating;
import de.noob.entities.User;


public class LocationTO extends ReturncodeResponse {

	private static final long serialVersionUID = -5498069855610055362L;
	
	public LocationTO(int id, String name, String category, String description,
			String street, String number, int plz, String city,
			String coordinates, int averageRating, ArrayList<Rating> ratings,
			ArrayList<Comment> comments, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.street = street;
		this.number = number;
		this.plz = plz;
		this.city = city;
		this.coordinates = coordinates;
		this.averageRating = averageRating;
		this.ratings = ratings;
		this.comments = comments;
		this.owner = owner;
	}

	private int id;
	
	private String name;
	
	private String category;
	
	private String description;
	
	private String street;
	
	private String number;
	
	private int plz;
	
	private String city;
	
	private String coordinates;
	
	private int averageRating;
	
	private ArrayList<Rating> ratings;
	
	private ArrayList<Comment> comments;
	
	private User owner;
	
	public LocationTO() {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public int getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	

}
