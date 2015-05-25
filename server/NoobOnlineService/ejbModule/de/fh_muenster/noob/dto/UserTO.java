package de.fh_muenster.noob.dto;

import java.io.Serializable;
import java.util.List;

import de.fh_muenster.noob.entities.Comment;
import de.fh_muenster.noob.entities.Location;
import de.fh_muenster.noob.entities.Rating;


/**
 * @author philipp
 *
 */
public class UserTO implements Serializable {

	private static final long serialVersionUID = 8083056892127800832L;

	private int id;
	private String name;
	private String password;
	private String email;
	private List<Location> locations;
	private List<Rating> ratings;
	private List<Comment> comments;
	
	public UserTO() {
	}
	
	

	public UserTO(int id, String name, String password, String email,
			List<Location> locations, List<Rating> ratings,
			List<Comment> comments) {
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



	public List<Location> getLocations() {
		return locations;
	}



	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}



	public List<Rating> getRatings() {
		return ratings;
	}



	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}



	public List<Comment> getComments() {
		return comments;
	}



	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
