package de.fh_muenster.noob;


/**
 * @author philipp
 *
 */
public interface NoobOnlineService {

    /**
     * @param username
     * @param email
     * @param password
     * @param passwordConfirmation
     * @return
     */
    public ReturncodeResponse register(String username, String email,
                                       String password, String passwordConfirmation);

    /**
     * Mit dieser Methode kann ein User eingeloggt werden. Dazu Email und Passwort angeben.
     * Im Erfolgsfall gibt es ein UserLoginResponse Objekt zurück, welches eine SessionID enthält.
     * @return UserLoginResponse
     */
    public UserLoginResponse login(String email, String password);

    /**
     * @param sessionId
     * @return
     */
    public ReturncodeResponse logout(int sessionId);

    /**
     * @return
     */
    public CategoryListResponse listCategories();

    /**
     * @param category
     * @param city
     * @return
     */
    public LocationListResponse listLocationsWithCategory(String category,
                                                          String city);

    /**
     * @param name
     * @param city
     * @return
     */
    public LocationListResponse listLocationsWithName(String name, String city);

    /**
     * @param city
     * @return
     */
    public LocationListResponse listAllLocations(String city);

    /**
     * @param sessionId
     * @param locationId
     * @param value
     * @return
     */
    public ReturncodeResponse giveRating(int sessionId, int locationId,
                                         int value);

    /**
     * @param sessionId
     * @param locationId
     * @param text
     * @return
     */
    public ReturncodeResponse commentOnLocation(int sessionId, int locationId,
                                                String text);

    /**
     * @param sessionId
     * @param commentId
     * @param text
     * @return
     */
    public ReturncodeResponse commentOnComment(int sessionId, int commentId,
                                               String text);

    /**
     * @param sessionId
     * @param name
     * @param category
     * @param description
     * @param street
     * @param number
     * @param plz
     * @param city
     * @return
     */
    public ReturncodeResponse createLocation(int sessionId, String name,
                                             String category, String description, String street, String number,
                                             int plz, String city);

    /**
     * @param sessionId
     * @param newLocationDetails
     * @return
     */
    public ReturncodeResponse setLocationDetails(int sessionId,
                                                 LocationTO newLocationDetails);

    /**
     * @param sessionId
     * @param newUser
     * @return
     */
    public ReturncodeResponse setUserDetails(int sessionId, UserTO newUser);

    /**
     * @param sessionId
     * @return
     */
    public UserTO getUserDetails(int sessionId);

    /**
     * @param sessionId
     * @return
     */
    public ReturncodeResponse deleteUser(int sessionId);

}