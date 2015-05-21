package de.fh_muenster.noob.noobservice;

import java.util.List;

import de.fh_muenster.noob.location.Comment;
import de.fh_muenster.noob.location.Location;
import de.fh_muenster.noob.user.User;


/**
 * @author philipp
 *
 */
public interface NoobOnlineService {
	
	/**
	 * Register a new User.
	 * 
	 * @param username
	 * @param email
	 * @param password
	 * @param passwordConfirmation
	 * @return 
	 * @throws InvalidDataException
	 */
	public boolean register(String username, String email, String password, String passwordConfirmation) throws InvalidDataException;
	
	/**
	 * Login an User.
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws InvalidLoginException
	 */
	public boolean login(String email, String password) throws InvalidLoginException;
	
	/**
	 * Logout an User
	 * 
	 * @return
	 * @throws NoSessionException
	 */
	public boolean logout() throws NoSessionException;
	
	/**
	 * Create a new Location.
	 * 
	 * @param name
	 * @param category
	 * @param description
	 * @param street
	 * @param number
	 * @param plz
	 * @param city
	 * @param coordinates
	 * @param owner
	 * @throws NoSessionException
	 */
	public void createLocation(String name, String category, String description, String street, String number, String plz, String city, String coordinates, User owner)throws NoSessionException;
	
	/**
	 * Returns a list of all categories
	 * 
	 * @return
	 * @throws NoSessionException
	 */
	public List<String> listCategories() throws NoSessionException;
	
	/**
	 * Returns a List of locations in a specific city, matching a specific category.
	 * 
	 * @param category
	 * @param city
	 * @return
	 * @throws NoSessionException
	 */
	public List<Location> listLocationWithCategory(String category, String city) throws NoSessionException;
	
	/**
	 * Returns a list of all locations in a specific city.
	 * 
	 * @param city
	 * @return
	 * @throws NoSessionException
	 */
	public List<Location> listAllLocations(String city) throws NoSessionException;
	
	/**
	 * Returns a list of locations with a specific name in a city.
	 * 
	 * @param name
	 * @param city
	 * @return
	 * @throws NoSessionException
	 */
	public List<Location> listLocationsWithName(String name, String city) throws NoSessionException;
	
	/**
	 * Create a new comment on a location.
	 * 
	 * @param user
	 * @param location
	 * @param text
	 * @throws NoSessionException
	 */
	public void commentOnLocation(User user, Location location, String text) throws NoSessionException;
	
	/**
	 * Create a new comment on a comment.
	 * @param user
	 * @param comment
	 * @param text
	 * @throws NoSessionException
	 */
	public void commentOnComment(User user, Comment comment, String text) throws NoSessionException;
	
	/**
	 * Delete an User.
	 * @param user
	 * @throws NoSessionException
	 */
	public void deleteUser(User user) throws NoSessionException;
	
	/**
	 * Returns a location, so that you can update the details of it. After that you have to send the Location back to Server with "setLocationDetails".
	 * 
	 * @param location
	 * @return
	 * @throws NoSessionException
	 */
	public Location getLocationDetails(Location location) throws NoSessionException;
	
	/**
	 * Sends a location back to the Server so that updates of the locationdetails can persisted.
	 * 
	 * @param location
	 * @throws NoSessionException
	 */
	public void setLocationDetails(Location location) throws NoSessionException;
	
	/**
	 * ??obsolet??
	 * Returns an user, so that you can update the details of it. After that you have to send the User back to Server with "setUserDetails".
	 * 
	 * @param user
	 * @return
	 * @throws NoSessionException
	 */
	public User getUserDetails(User user) throws NoSessionException;
	
	/**
	 * Sends an user back to the server, so that user-details can be persisted.
	 * 
	 * @param user
	 * @throws NoSessionException
	 */
	public void setUserDetails(User user) throws NoSessionException;
	
	/**
	 * Give a Rating from 1 to 10 to a Location.
	 * 
	 * @param user
	 * @param location
	 * @param value (1 to 10!)
	 * @throws NoSessionException
	 */
	public void giveRating(User user, Location location, int value) throws NoSessionException;
	
}
