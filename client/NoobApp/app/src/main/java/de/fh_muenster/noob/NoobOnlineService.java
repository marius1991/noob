package de.fh_muenster.noob;



/**
 * Interface des Servers
 * @author philipp
 */
public interface NoobOnlineService {

    /**
     * @param username
     * @param email
     * @param password
     * @param passwordConfirmation
     * @return
     */
    public ReturnCodeResponse register(String username, String email,
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
    public ReturnCodeResponse logout(int sessionId);

    /**
     * @return
     */
    public CategoryListResponse listCategories();

    /**
     * @return
     */
    public CityListResponse listCities();

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
     * @param sessionId
     * @param locationId
     * @param value
     * @return
     */
    public ReturnCodeResponse giveRating(int sessionId, int locationId,
                                         int value);

    /**
     * @param sessionId
     * @param locationId
     * @param text
     * @return
     */
    public ReturnCodeResponse commentOnLocation(int sessionId, int locationId,
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
    public ReturnCodeResponse createLocation(int sessionId, String name,
                                             String category, String description, String street, String number,
                                             int plz, String city, byte[] image);


    /**
     *
     * @param sessionId
     * @param locationId
     * @param name
     * @param category
     * @param description
     * @param street
     * @param number
     * @param plz
     * @param city
     * @return
     */
    public ReturnCodeResponse setLocationDetails(int sessionId, int locationId, String name, String category, String description, String street, String number, int plz, String city);

    /**
     *
     * @param locationId
     * @return
     */
    public LocationTO getLocationDetails(int locationId);

    /**
     *
     * @param sessionId
     * @param name
     * @param password
     * @param passwordwdh
     * @return
     */
    public ReturnCodeResponse setUserDetails(int sessionId, String name, String password, String passwordwdh);

    /**
     * @param sessionId
     * @return
     */
    public UserTO getUserDetails(int sessionId);

    /**
     * @param sessionId
     * @return
     */
    public ReturnCodeResponse deleteUser(int sessionId, String password);

    /**
     * @param sessionId
     * @param locationId
     * @param image
     * @return
     */
    public ReturnCodeResponse addImageToLocation(int sessionId, int locationId, byte[] image);

    /**
     *
     * @param sessionId
     * @param locationId
     * @return
     */
    public ReturnCodeResponse deleteLocation(int sessionId, int locationId);

}