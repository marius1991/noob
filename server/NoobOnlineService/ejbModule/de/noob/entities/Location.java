package de.noob.entities;

import java.util.ArrayList;

import javax.persistence.*;

@Entity
public class Location{
	
	@Id
	@GeneratedValue
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
	
	@OneToMany (mappedBy="ratings", cascade = CascadeType.REMOVE)
	private ArrayList<Rating> ratings;
	
	@OneToMany (mappedBy ="comments", cascade = CascadeType.REMOVE)
	private ArrayList<Comment> comments;
	
	@ManyToOne
	private User owner;
	
	
	
	public Location(){
		
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
