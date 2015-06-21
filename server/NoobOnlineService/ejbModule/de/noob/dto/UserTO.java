package de.noob.dto;

import java.util.List;

/**
 * Diese Klasse repräsentiert einen de.noob.entities.User, nur mit Getter und Setter Methoden, sodass der
 * Serialisierungsaufwand verringert ist wenn ein User zum Client gesendet wird.
 * @author Philipp Ringele
 *
 */
public class UserTO extends ReturnCodeResponse {

	private static final long serialVersionUID = 6907767619451988547L;

	private String name;
	
	private String password;
	
	private String email;
	
	private List<LocationTO> locations;
	
	private List<RatingTO> ratings;
	
	private List<CommentTO> comments;
	
	//images eines Users sind nicht enthalten, da diese nur im Zusammenhang einer Location auf dem Client benötigt werden.
	
	public UserTO(String name, String password, String email,
			List<LocationTO> locations, List<RatingTO> ratings,
			List<CommentTO> comments) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.locations = locations;
		this.ratings = ratings;
		this.comments = comments;
	}
	
	public UserTO() {
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

	public List<LocationTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationTO> locations) {
		this.locations = locations;
	}

	public List<RatingTO> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingTO> ratings) {
		this.ratings = ratings;
	}

	public List<CommentTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentTO> comments) {
		this.comments = comments;
	}
	
	
	
}
