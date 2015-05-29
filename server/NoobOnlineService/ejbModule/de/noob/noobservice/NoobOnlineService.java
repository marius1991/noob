package de.noob.noobservice;


import de.noob.dto.CategoryListResponse;
import de.noob.dto.LocationListResponse;
import de.noob.dto.LocationTO;
import de.noob.dto.LoginResponse;
import de.noob.dto.ReturncodeResponse;
import de.noob.dto.UserTO;
import de.noob.entities.Comment;
import de.noob.entities.Location;
import de.noob.entities.User;



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
	public ReturncodeResponse register(String username, String email, String password, String passwordConfirmation) throws InvalidDataException;
	
	/**
	 * Login an User.
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws InvalidLoginException
	 */
	public LoginResponse login(String email, String password) throws InvalidLoginException;
	
	/**
	 * Logout an User
	 * 
	 * @return
	 * @throws NoSessionException
	 */
	public ReturncodeResponse logout() throws NoSessionException;
	
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
	 * @return 
	 * @throws NoSessionException
	 */
	public ReturncodeResponse createLocation(String name, String category, String description, String street, String number, String plz, String city, String coordinates, User owner)throws NoSessionException;
	
	/**
	 * Returns a list of all categories
	 * 
	 * @return
	 * @throws NoSessionException
	 */
	public CategoryListResponse listCategories() throws NoSessionException;
	
	/**
	 * Returns a List of locations in a specific city, matching a specific category.
	 * 
	 * @param category
	 * @param city
	 * @return
	 * @throws NoSessionException
	 */
	public LocationListResponse listLocationWithCategory(String category, String city) throws NoSessionException;
	
	/**
	 * Returns a list of all locations in a specific city.
	 * 
	 * @param city
	 * @return
	 * @throws NoSessionException
	 */
	public LocationListResponse listAllLocations(String city) throws NoSessionException;
	
	/**
	 * Returns a list of locations with a specific name in a city.
	 * 
	 * @param name
	 * @param city
	 * @return
	 * @throws NoSessionException
	 */
	public LocationListResponse listLocationsWithName(String name, String city) throws NoSessionException;
	
	/**
	 * Create a new comment on a location.
	 * 
	 * @param user
	 * @param location
	 * @param text
	 * @return 
	 * @throws NoSessionException
	 */
	public ReturncodeResponse commentOnLocation(User user, Location location, String text) throws NoSessionException;
	
	/**
	 * Create a new comment on a comment.
	 * @param user
	 * @param comment
	 * @param text
	 * @return 
	 * @throws NoSessionException
	 */
	public ReturncodeResponse commentOnComment(User user, Comment comment, String text) throws NoSessionException;
	
	/**
	 * Delete an User.
	 * @param user
	 * @return 
	 * @throws NoSessionException
	 */
	public ReturncodeResponse deleteUser(User user) throws NoSessionException;
	
	/**
	 * Returns a location, so that you can update the details of it. After that you have to send the Location back to Server with "setLocationDetails".
	 * 
	 * @param location
	 * @return
	 * @throws NoSessionException
	 */
	public LocationTO getLocationDetails(Location location) throws NoSessionException;
	
	/**
	 * Sends a location back to the Server so that updates of the locationdetails can persisted.
	 * 
	 * @param location
	 * @return 
	 * @throws NoSessionException
	 */
	public ReturncodeResponse setLocationDetails(Location location) throws NoSessionException;
	
	/**
	 * ??obsolet??
	 * Returns an user, so that you can update the details of it. After that you have to send the User back to Server with "setUserDetails".
	 * 
	 * @param user
	 * @return
	 * @throws NoSessionException
	 */
	public UserTO getUserDetails(User user) throws NoSessionException;
	
	/**
	 * Sends an user back to the server, so that user-details can be persisted.
	 * 
	 * @param user
	 * @return 
	 * @throws NoSessionException
	 */
	public ReturncodeResponse setUserDetails(User user) throws NoSessionException;
	
	/**
	 * Give a Rating from 1 to 10 to a Location.
	 * 
	 * @param user
	 * @param location
	 * @param value (1 to 10!)
	 * @return 
	 * @throws NoSessionException
	 */
	public ReturncodeResponse giveRating(User user, Location location, int value) throws NoSessionException;
	
}
