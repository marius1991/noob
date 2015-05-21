package de.fh_muenster.noob.location;

import java.util.ArrayList;

import de.fh_muenster.noob.user.User;

public interface Location {
	
	public int getId();

	public void setId(int id);

	public String getName();

	public void setName(String name);

	public String getCategory();

	public void setCategory(String category);

	public String getDescription();

	public void setDescription(String description) ;

	public String getStreet();
	
	public void setStreet(String street);
	
	public String getNumber();

	public void setNumber(String number);

	public int getPlz();

	public void setPlz(int plz);
	
	public String getCity();

	public void setCity(String city);

	public String getCoordinates();

	public void setCoordinates(String coordinates);

	public int getAverageRating();

	public void setAverageRating(int averageRating);

	public ArrayList<Rating> getRatings();

	public void setRatings(ArrayList<Rating> ratings);

	public ArrayList<Comment> getComments();

	public void setComments(ArrayList<Comment> comments);

	public User getOwner();

	public void setOwner(User owner);

}
