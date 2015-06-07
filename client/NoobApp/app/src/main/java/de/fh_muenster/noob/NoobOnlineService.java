package de.fh_muenster.noob;

import de.fh_muenster.noob.*;



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
     */
    public void register(String username, String email, String password, String passwordConfirmation);

    /**
     * Login an User.
     *
     * @param email
     * @param password
     * @return
     */
    public UserLoginResponse login(String email, String password);

    /**
     * Logout an User
     *
     * @return
     */
    public ReturncodeResponse logout();

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
     */
    public ReturncodeResponse createLocation(String name, String category, String description, String street, String number, String plz, String city, String coordinates, UserTO owner);

    /**
     * Returns a list of all categories
     *
     * @return
     */
    public CategoryListResponse listCategories();

    /**
     * Returns a List of locations in a specific city, matching a specific category.
     *
     * @param category
     * @param city
     * @return
     */
    public LocationListResponse listLocationWithCategory(String category, String city);

    /**
     * Returns a list of all locations in a specific city.
     *
     * @param city
     * @return
     */
    public LocationListResponse listAllLocations(String city);

    /**
     * Returns a list of locations with a specific name in a city.
     *
     * @param name
     * @param city
     * @return
     */
    public LocationListResponse listLocationsWithName(String name, String city);

    /**
     * Create a new comment on a location.
     *
     * @param user
     * @param location
     * @param text
     * @return
     */
    public ReturncodeResponse commentOnLocation(UserTO user, LocationTO location, String text);

    /**
     * Create a new comment on a comment.
     * @param user
     * @param comment
     * @param text
     * @return
     */
    public ReturncodeResponse commentOnComment(UserTO user, CommentTO comment, String text);

    /**
     * Give a Rating from 1 to 10 to a Location.
     *
     * @param user
     * @param location
     * @param value (1 to 10!)
     * @return
     */
    public ReturncodeResponse giveRating(UserTO user, LocationTO location, int value);

    /**
     * Returns a location, so that you can update the details of it. After that you have to send the Location back to Server with "setLocationDetails".
     *
     * @param location
     * @return
     */
    public LocationTO getLocationDetails(LocationTO location);

    /**
     * Sends a location back to the Server so that updates of the locationdetails can persisted.
     *
     * @param location
     * @return
     */
    public ReturncodeResponse setLocationDetails(LocationTO location);

    /**
     * ??obsolet??
     * Returns an user, so that you can update the details of it. After that you have to send the User back to Server with "setUserDetails".
     *
     * @param user
     * @return
     */
    public UserTO getUserDetails(UserTO user);

    /**
     * Sends an user back to the server, so that user-details can be persisted.
     *
     * @param user
     * @return n
     */
    public ReturncodeResponse setUserDetails(UserTO user);

    /**
     * Delete an User.
     * @param user
     * @return
     */
    public ReturncodeResponse deleteUser(UserTO user);

}