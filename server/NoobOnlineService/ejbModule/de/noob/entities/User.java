package de.noob.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Stellt einen User in der Datenbank dar.
 * @author Tim Hembrock, Philipp Ringele
 *
 */
@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 6341269371900561214L;
	
	/**
	 * Die ID eines Users ist seine E-Mailadresse.
	 * Mit dieser muss sich der User auch einloggen.
	 */
	@Id
	private String email;
	
	private String name;
	
	private String password;
	
	@OneToMany (mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Location> locations;
	
	@OneToMany (mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Rating> ratings;
	
	@OneToMany (mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Image> images;
	
	public User() {
	}
	
	public User(String username, String email, String password){
		
		this.name=username;
		this.password=password;
		this.email=email;
		
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

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	
}
