package de.noob.noobservice;

import de.noob.dto.CategoryListResponse;
import de.noob.dto.CityListResponse;
import de.noob.dto.LocationListResponse;
import de.noob.dto.LocationTO;
import de.noob.dto.ReturnCodeResponse;
import de.noob.dto.UserLoginResponse;
import de.noob.dto.UserTO;

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
	 * @param commentId
	 * @param text
	 * @return
	 */
	public ReturnCodeResponse commentOnComment(int sessionId, int commentId,
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
			int plz, String city);

	/**
	 * @param sessionId
	 * @param newLocationDetails
	 * @return
	 */
	public ReturnCodeResponse setLocationDetails(int sessionId,
			LocationTO newLocationDetails);
	
	/**
	 * @param sessionId
	 * @param newUser
	 * @return
	 */
	public ReturnCodeResponse setUserDetails(int sessionId, int id, String name, String email, String password);

	/**
	 * @param sessionId
	 * @return
	 */
	public UserTO getUserDetails(int sessionId);

	/**
	 * @param sessionId
	 * @return
	 */
	public ReturnCodeResponse deleteUser(int sessionId);

	public ReturnCodeResponse createLocation(int sessionId, String name,
			String category, String description, String street, String number,
			int plz, String city, byte[] image);

}
