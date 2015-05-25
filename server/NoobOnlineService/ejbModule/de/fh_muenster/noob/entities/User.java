package de.fh_muenster.noob.entities;

import java.util.ArrayList;


public class User{
	
	private int id;
	
	private String name;
	
	private String password;
	
	private String email;
	
	private ArrayList<Location> locations;
	
	private ArrayList<Rating> ratings;
	
	private ArrayList<Comment> comments;
	
	public User(String username, String email, String password){
		
		this.name=username;
		this.password=password;
		this.email=email;
		
	}

	public int getId() {
		return id;
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

	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public boolean createLocation(String name, String adresse, String category, String description){
		return false;
	
	}
	
	public boolean deleteUser(){
		return false;
		
	}

}
