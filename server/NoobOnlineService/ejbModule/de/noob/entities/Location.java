package de.noob.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.jboss.logging.Logger;


/**
 * 
 * @author Tim
 *
 */
@Entity
public class Location implements Serializable {

	private static final long serialVersionUID = 2566351654426224522L;
	private static final Logger logger = Logger.getLogger(Location.class);
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
	
	private double averageRating;
	
	@OneToMany (mappedBy="location", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Rating> ratings;
	
	@OneToMany (mappedBy ="location", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Comment> comments;
	
	@ManyToOne
	private User owner;
	
	private byte[] image;
	
	public Location() {
	}
	
	public Location(String name, String category, String description,
			String street, String number, int plz, String city, User owner) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.street = street;
		this.number = number;
		this.plz = plz;
		this.city = city;
		this.owner = owner;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}

	public void addRating(User user, int value) {
		
		
		//Prüfen ob die Liste ratings leer ist, wenn ja Rating hinzufügen
		if (this.ratings.isEmpty()) {
			this.ratings.add(new Rating(user,value,this));
		}
		
		//falls die Liste nicht leer ist
		else {
			//Prüfen ob der User bereits ein Rating abgegeben hat. Dafür jedes Element 
			//innerhalb der Liste die ID abfragen und mit dem aktuellen User vergleichen.
			//Wenn der User bereits ein Rating abgegeben hat, wird der alte Wert überschrieben.
			boolean newRating = true;
			for(int i=0;i<this.ratings.size();i++) {
				if (this.ratings.get(i).getOwner().getEmail().equals(user.getEmail())) {
					this.ratings.get(i).setValue(value);
					newRating=false;
				}
			}
			//Falls der aktuelle User noch kein Rating abgegeben hat, neues Rating hinzufügen.
			if (newRating == true) {
				logger.info("User hat noch kein Rating abgegebeb");
				this.ratings.add(new Rating(user,value,this));
			}
			
		}
		//Durchschnittsbewertung berechen
		this.calcAverageRating();
	}

	public void addComment(User user, String text) {
		this.comments.add(new Comment(user, text, this, null));
	}
	
	private void calcAverageRating() {
		
		double sum=0;
		
		for(int i=0; i<this.ratings.size();i++) {
			sum = sum + ratings.get(i).getValue();
		}
		this.setAverageRating( ( sum / this.ratings.size() ) );
	}
}
