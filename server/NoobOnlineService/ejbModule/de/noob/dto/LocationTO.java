package de.noob.dto;

import java.util.List;

/**
 * Diese Klasse repräsentiert eine de.noob.entities.Location, nur mit Getter und Settter Methoden, sodass
 * der Serialisierungsaufwand verringert ist wenn eine Location zum Client gesendet wird.
 * @author Philipp Ringele
 *
 */
public class LocationTO extends ReturnCodeResponse {

	private static final long serialVersionUID = -5498069855610055362L;
	
	private int id;
	
	private String name;
	
	private String category;
	
	private String description;
	
	private String street;
	
	private String number;
	
	private int plz;
	
	private String city;
	
	private double averageRating;
	
	private String ownerId;
	
	private String ownerName;
	
	private List<RatingTO> ratings;
	
	private List<CommentTO> comments;
	//Images werden direkt als byte[] übergeben, was die Entgegennahme auf dem Client vereinfacht.
	private List<byte[]> images;

	
	public LocationTO(int id, String name, String category, String description,
			String street, String number, int plz, String city,
			double averageRating, List<RatingTO> ratings,
			List<CommentTO> comments, String ownerId, String ownerName, List<byte[]> images) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.street = street;
		this.number = number;
		this.plz = plz;
		this.city = city;
		this.averageRating = averageRating;
		this.ratings = ratings;
		this.comments = comments;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.images = images;
	}

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

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public List<byte[]> getImages() {
		return images;
	}

	public void setImages(List<byte[]> images) {
		this.images = images;
	}

}
