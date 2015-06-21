package de.noob.noobservice;

import de.noob.dto.CategoryListResponse;
import de.noob.dto.CityListResponse;
import de.noob.dto.LocationListResponse;
import de.noob.dto.LocationTO;
import de.noob.dto.ReturnCodeResponse;
import de.noob.dto.UserLoginResponse;
import de.noob.dto.UserTO;

/**
 * @author Philipp Ringele
 *
 */
public interface NoobOnlineService {

	/**
	 * Mit dieser Methode kann ein Benutzer registriert werden.
	 * @param username
	 * @param email
	 * @param password
	 * @param passwordConfirmation
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt. 
	 */
	public ReturnCodeResponse register(String username, String email,
			String password, String passwordConfirmation);


	/**
	 * Mit dieser Methode kann ein User eingeloggt werden. Auf dem Server wird ein Session-Objekt fuer den User angelegt.
	 * @param email
	 * @param password
	 * @return UserLoginResponse-Instanz, welche SessionID, ReturnCode und Message beinhaltet.
	 */
	public UserLoginResponse login(String email, String password);

	/**
	 * Mit dieser Methode kann ein User ausgeloggt werden, sodass sein Session-Objekt auf dem Server geloescht wird.
	 * @param sessionId
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt. 
	 */
	public ReturnCodeResponse logout(int sessionId);

	/**
	 * Mit dieser Methode koennen alle zulaessigen Kategorien vom Server abgerufen werden.
	 * @return Eine CategoryListResponse-Instanz, welche eine java.util.List<String> mit allen Kategorien beinhaltet,
	 * sowie ReturnCode und Message.
	 */
	public CategoryListResponse listCategories();
	
	/**
	 * Mit dieser Methode koennen alle bisher vorhandenen Staedte vom Server abgerufen werden.
	 * @return Eine CityListResponse-Instanz, welche eine java.util.List<String> mit allen gefundenen Staedten
	 * beinhaltet, sowie ReturnCode und Message.
	 */
	public CityListResponse listCities();

	/**
	 * Mit dieser Methode werden alle Locations abgerufen, die zu einer bestimmten Kategorie gehoeren und 
	 * in einer bestimmten Stadt liegen.
	 * @param category
	 * @param city
	 * @return Eine LocationListResponse-Instanz, die eine java.util.List<Location> mit allen gefundenen Locations 
	 * enthaelt.
	 */
	public LocationListResponse listLocationsWithCategory(String category,
			String city);

	/**
	 * Mit dieser Methode werden alle Locations abgerufen, die einen bestimmten Name haben und in einer
	 * bestimmten Stadt liegen.
	 * @param name
	 * @param city
	 * @return  Eine LocationListResponse-Instanz, die eine java.util.List<Location> mit allen gefundenen Locations 
	 * enthaelt.
	 */
	public LocationListResponse listLocationsWithName(String name, String city);

	/**
	 * Mit dieser Methode kann ein Rating zu einer bestimmten Location hinzugefuegt werden.
	 * Da ein User nur ein Rating pro Location abgeben darf, wird im Wiederholungsfall das alte Rating
	 * ueberschrieben.
	 * @param sessionId
	 * @param locationId
	 * @param value Die Hoehe des Ratings von 1 bis 10. 
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt. 
	 */
	public ReturnCodeResponse giveRating(int sessionId, int locationId,
			int value);

	/**
	 * Mit dieser Methode kann ein Kommentar zu einer Location hinzugefuegt werden.
	 * @param sessionId
	 * @param locationId
	 * @param text
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt. 
	 */
	public ReturnCodeResponse commentOnLocation(int sessionId, int locationId,
			String text);
	
	/**
	 * Mit dieser Methode kann eine neue Location angelegt werden, alle Parameter ausser image sind Pflichtfelder,
	 * wenn kein image hochgeladen werden soll kann dieser Parameter als null gesetzt werden.
	 * @param sessionId
	 * @param name
	 * @param category
	 * @param description
	 * @param street
	 * @param number
	 * @param plz
	 * @param city
	 * @param image
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt. 
	 */
	public ReturnCodeResponse createLocation(int sessionId, String name,
	String category, String description, String street, String number,
	int plz, String city, byte[] image);
	
	/**
	 * Mit dieser Methode koennen die Werte aller Location-Attribute einer existierenden Location abgerufen werden.
	 * @param locationId
	 * @return Eine LocationTO-Instanz, die die Attribute einer Location enthaelt.
	 */
	public LocationTO getLocationDetails(int locationId);
	
	/**
	 * Mit dieser Methode lassen sich die Werte aller Attribute, (außer list<Image>images) einer existierenden Location veraendern. Alle Parameter sind
	 * Pflicht, da sonst die entsprechenden Attribute mit null überschrieben werden.
	 * @param sessionId
	 * @param locationId
	 * @param name
	 * @param category
	 * @param description
	 * @param street
	 * @param number
	 * @param plz
	 * @param city
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt.
	 */
	public ReturnCodeResponse setLocationDetails(int sessionId, int locationId,
	String name, String category, String description, String street,
	String number, int plz, String city);

	/**
	 * Mit dieser Methode koennen die Werte aller User-Attribute eines existierenden Users abgerufen werden.
	 * @param sessionId
	 * @return Eine UserTO-Instanz, die alle Attribute eines Users enthaelt.
	 */
	public UserTO getUserDetails(int sessionId);

	/**
	 * Mit dieser Methode lassen sich die Werte aller Attribute eines existierenden Users veraendern. Alle Parameter sind
	 * Pflicht, da sonst die entsprechenden Attribute mit null überschrieben werden.
	 * @param sessionId
	 * @param name
	 * @param password
	 * @param passwordConfirmation
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt.
	 */
	public ReturnCodeResponse setUserDetails(int sessionId, String name,
	String password, String passwordConfirmation);

	/**
	 * Mit dieser Methode laesst sich ein gerade eingeloggte User loeschen.
	 * @param sessionId
	 * @param password
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt.
	 */
	public ReturnCodeResponse deleteUser(int sessionId, String password);
	
	/**
	 * Mit dieser Methode kann man einer Location ein Bild hinzufuegen. Eine Location kann maximal
	 * 10 Bilder besitzen.
	 * @param sessionId
	 * @param locationId
	 * @param image
	 * @return Eine ReturnCodeResponse-Instanz, welche einen ReturnCode und eine Message enthaelt.
	 */
	public ReturnCodeResponse addImageToLocation(int sessionId, int locationId, byte[] image);

}
