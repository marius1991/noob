package de.noob.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 6341269371900561214L;

	@GeneratedValue
	private int id;
	
	private String name;
	
	private String password;
	
	@Id
	private String email;
	
	@OneToMany (mappedBy ="owner")
	private List<Location> locations;
	
	@OneToMany (mappedBy ="owner", cascade = CascadeType.REMOVE)
	private List<Rating> ratings;
	
	@OneToMany (mappedBy ="owner", cascade = CascadeType.REMOVE)
	private List<Comment> comments;
	
	public User() {
	}
	
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
	
	public List<Location> getLocations() {
		return locations;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public boolean createLocation(String name, String adresse, String category, String description){
		return false;
	}
	
	

}
