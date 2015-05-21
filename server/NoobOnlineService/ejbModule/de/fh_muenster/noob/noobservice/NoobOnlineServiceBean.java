package de.fh_muenster.noob.noobservice;

import de.fh_muenster.noob.location.Comment;
import de.fh_muenster.noob.location.Location;
import de.fh_muenster.noob.user.User;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * @author philipp
 * Session Bean implementation class NoobOnlineServiceBean
 */
@Stateless
@Remote(NoobOnlineService.class)
public class NoobOnlineServiceBean implements NoobOnlineService {

    /**
     * Default constructor. 
     */
    public NoobOnlineServiceBean() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see NoobOnlineService#logout()
     */
    public boolean logout() {
        // TODO Auto-generated method stub
			return false;
    }

	/**
     * @see NoobOnlineService#getLocationDetails(Location)
     */
    public Location getLocationDetails(Location location) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see NoobOnlineService#listLocationsWithName(String, String)
     */
    public List<Location> listLocationsWithName(String name, String city) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see NoobOnlineService#setUserDetails(User)
     */
    public void setUserDetails(User user) {
        // TODO Auto-generated method stub
    }

	/**
     * @see NoobOnlineService#createLocation(String, String, String, String, String, String, String, String, User)
     */
    public void createLocation(String name, String category, String description, String street, String number, String plz, String city, String coordinates, User owner) {
        // TODO Auto-generated method stub
    }

	/**
     * @see NoobOnlineService#setLocationDetails(Location)
     */
    public void setLocationDetails(Location location) {
        // TODO Auto-generated method stub
    }

	/**
     * @see NoobOnlineService#register(String, String, String, String)
     */
    public boolean register(String username, String email, String password, String passwordConfirmation) {
        // TODO Auto-generated method stub
			return false;
    }

	/**
     * @see NoobOnlineService#listLocationWithCategory(String, String)
     */
    public List<Location> listLocationWithCategory(String category, String city) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see NoobOnlineService#deleteUser(User)
     */
    public void deleteUser(User user) {
        // TODO Auto-generated method stub
    }

	/**
     * @see NoobOnlineService#getUserDetails(User)
     */
    public User getUserDetails(User user) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see NoobOnlineService#giveRating(User, Location, int)
     */
    public void giveRating(User user, Location location, int value) {
        // TODO Auto-generated method stub
    }

	/**
     * @see NoobOnlineService#listCategories()
     */
    public List<String> listCategories() {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see NoobOnlineService#login(String, String)
     */
    public boolean login(String email, String password) {
        // TODO Auto-generated method stub
			return false;
    }

	/**
     * @see NoobOnlineService#listAllLocations(String)
     */
    public List<Location> listAllLocations(String city) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see NoobOnlineService#commentOnLocation(User, Location, String)
     */
    public void commentOnLocation(User user, Location location, String text) {
        // TODO Auto-generated method stub
    }

	/**
     * @see NoobOnlineService#commentOnComment(User, Comment, String)
     */
    public void commentOnComment(User user, Comment comment, String text) {
        // TODO Auto-generated method stub
    }

}
