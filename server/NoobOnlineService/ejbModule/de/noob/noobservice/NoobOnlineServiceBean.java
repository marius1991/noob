package de.noob.noobservice;

import java.util.ArrayList;
import java.util.List;

import de.noob.dao.NoobDAOLocal;
import de.noob.dto.CategoryListResponse;
import de.noob.dto.CityListResponse;
import de.noob.dto.LocationListResponse;
import de.noob.dto.LocationTO;
import de.noob.dto.UserLoginResponse;
import de.noob.dto.ReturnCodeResponse;
import de.noob.dto.UserTO;
import de.noob.entities.Comment;
import de.noob.entities.Location;
import de.noob.entities.NoobSession;
import de.noob.entities.User;
import de.noob.util.DtoAssembler;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.jboss.logging.Logger;
import org.jboss.ws.api.annotation.WebContext;

/**
 * @author philipp
 *
 */
@WebService
@WebContext(contextRoot="/noob")
@Stateless
public class NoobOnlineServiceBean implements NoobOnlineService {
	
	private static final Logger logger = Logger.getLogger(NoobOnlineServiceBean.class);
	
	/**
	 * Über diese EJB werden alle Abfragen auf die Datenbank abgesetzt.
	 */
	@EJB(beanName = "NoobDAO", beanInterface = NoobDAOLocal.class)
	private NoobDAOLocal dao;

	/**
	 * Mit dieser EJB werden Transfer Objects erzeugt.
	 */
	@EJB
	private DtoAssembler dtoAssembler;

	@Override
	public ReturnCodeResponse register(String username, String email, String password, String passwordConfirmation) {
		logger.info("register() aufgerufen.");
		
		ReturnCodeResponse re = new ReturnCodeResponse();
		if (dao.findUserByName(username) == null && dao.findUserByEmail(email) == null) {
			if(password.equals(passwordConfirmation)) {
				User user = new User(username, email, password);
				dao.persist(user);
				re.setReturnCode(0);
				re.setMessage(username + " erfolgreich registriert.");
				
				logger.info(username + " erfolgreich registriert.");
			}
			else {
				re.setReturnCode(1);
				re.setMessage("Passwörter stimmen nicht überein!");
				
				logger.info("Passwörter stimmen nicht überein!");
			}
		}
		else {
			re.setReturnCode(2);
			re.setMessage("Name oder Email schon vergeben!");
			
			logger.info("Name oder Email schon vergeben!");
		}
		return re;
	}

	/**
	 * Mit dieser Methode kann ein User eingeloggt werden. Dazu Email und Passwort angeben.
	 * Im Erfolgsfall gibt es ein UserLoginResponse Objekt zurück, welches eine SessionID enthält.
	 * @return UserLoginResponse
	 */
	@Override
	public UserLoginResponse login(String email, String password) {
		logger.info("login() aufgerufen!");
		UserLoginResponse re = new UserLoginResponse();
		NoobSession session;
		
		User user = dao.findUserByEmail(email);
		if (user != null) {
			if (user.getPassword().equals(password)) {				
				session = new NoobSession(user);
				dao.persist(session);
				
				re.setReturnCode(0);
				re.setMessage("Login erfolgreich.");
				re.setSessionId(session.getId());
				
				logger.info(user.getName() +" erfolgreich eingeloggt.");
			}
			else {
				re.setReturnCode(1);
				re.setMessage("Email oder Passwort falsch.");
				logger.info("Email oder Passwort falsch.");
			}	
		}
		else {
			re.setReturnCode(2);
			re.setMessage("User nicht vorhanden.");
			logger.info("User nicht vorhanden.");
		}
		return re;
	}

	/**
	 * 
	 * @return ReturncodeResponse
	 */
	@Override
	public ReturnCodeResponse logout(int sessionId) {
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);		
		if(session != null) {
			dao.remove(session);
			re.setReturnCode(0);
			re.setMessage("Erfolgreich ausgeloggt.");
			logger.info("Ergolgreich ausgeloggt");
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Keine Session vorhanden!");
		}
		
		return re;
	}

	@Override
	public CategoryListResponse listCategories() {
		logger.info("listCategories() aufgerufen.");
		CategoryListResponse re = new CategoryListResponse();
		List<String> categories = new ArrayList<String>();
		
		categories.add("Bar");
		categories.add("Kneipe");
		categories.add("Supermarkt");
		categories.add("Arzt");
		categories.add("Tankstelle");
		categories.add("Friseur");
		categories.add("Diskothek");
		categories.add("Hochschule/Universität");
		categories.add("Schule");
		categories.add("Bank");
		categories.add("Park");
		categories.add("Kirche");
		categories.add("Sehenswürdigkeit");
		
		re.setCategories(categories);
		re.setReturnCode(0);
		re.setMessage("Kategorien erfolgreich abgerufen.");
		logger.info("Kategorien erfolgreich abgerufen.");
		return re;
	}

	@Override
	public CityListResponse listCities() {
		CityListResponse re = new CityListResponse();
		List<String> cities = dao.listCities();
		if(!cities.isEmpty()) {
			re.setCities(cities);
			re.setReturnCode(0);
			re.setMessage(cities.size() + " Städte erfolgreich abgerufen.");
			logger.info(cities.size() + " Städte erfolgreich abgerufen.");
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Keine Städte vorhanden.");
			logger.info("Keine Städte vorhanden");
		}

		return re;
	}

	@Override
	public LocationListResponse listLocationsWithCategory(String category, String city) {
		logger.info("listLocationsWithCategory() aufgerufen.");
		LocationListResponse re = new LocationListResponse();
		List<Location> locations = dao.findLocationsByCategory(category, city);
		if(locations != null) {
			if(!locations.isEmpty()) {
				re.setLocations(dtoAssembler.makeLocationsDTO(locations));
				re.setReturnCode(0);
				re.setMessage(locations.size() + " Location(s) gefunden.");
				logger.info(locations.size() + " Location(s) gefunden.");
				
						if( locations.get(0).getRatings() != null ){
							if(!locations.get(0).getRatings().isEmpty()) {
							logger.info(locations.get(0).getRatings().get(0).getValue());
							}
							else {
								logger.info("Ratings sind leer!!!!!!");
							}
						}
					
				
			}
			else {
				re.setReturnCode(1);
				re.setMessage("Keine Locations für Kategorie: '" + category + "' gefunden.");
				logger.info("Keine Locations für Kategorie: '" + category + "' gefunden.");	
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Keine Locations für Kategorie: '" + category + "' gefunden.");
			logger.info("Keine Locations für Kategorie: '" + category + "' gefunden.");	
		}
		return re;
	}

	@Override
	public LocationListResponse listLocationsWithName(String name, String city) {
		LocationListResponse re = new LocationListResponse();
		List<Location> locations = dao.findLocationsByName(name, city);
		re.setLocations(dtoAssembler.makeLocationsDTO(locations));
		re.setReturnCode(0);
		re.setMessage(locations.size() + " Location(s) gefunden.");
		return re;
	}

	@Override
	public LocationListResponse listAllLocations(String city) {
		LocationListResponse re = new LocationListResponse();
		List<Location> locations = dao.findLocationsByCity(city);
		re.setLocations(dtoAssembler.makeLocationsDTO(locations));
		re.setReturnCode(0);
		re.setMessage(locations.size() + " Location(s) gefunden.");
		return re;
	}

	@Override
	public ReturnCodeResponse giveRating(int sessionId, int locationId, int value) {
		logger.info("giveRating() aufgerufen.");
		logger.info("SessionId: " + sessionId);
		ReturnCodeResponse re = new ReturnCodeResponse();
		Location location = dao.findLocationById(locationId);
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			User user = session.getUser();
			logger.info("User: " + user.getEmail());
			if(location != null) {
				location.addRating(user, value);
				dao.persist(location);	
				logger.info("Rating mit Wert "+Integer.toString(value)+" gespeichert.");
				re.setReturnCode(0);
				re.setMessage("Bewertung wurde gespeichert.");
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Location existiert nicht.");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet.");
		}
		return re;
	}

	@Override
	public ReturnCodeResponse commentOnLocation(int sessionId, int locationId, String text) {
		ReturnCodeResponse re = new ReturnCodeResponse();
		Location location = dao.findLocationById(locationId);
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			User user = session.getUser();
			if(location != null) {
				location.addComment(user, text);
				dao.persist(location);				
				re.setReturnCode(0);
				re.setMessage("Kommentar wurde gespeichert.");
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Location existiert nicht.");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet.");
		}
		return re;
	}

	@Override
	public ReturnCodeResponse commentOnComment(int sessionId, int commentId, String text) {
		ReturnCodeResponse re = new ReturnCodeResponse();
		Comment comment = dao.findCommentById(commentId);
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			User user = session.getUser();
			if(comment != null) {
				comment.addComment(user, text);
				dao.persist(comment);				
				re.setReturnCode(0);
				re.setMessage("Kommentar wurde gespeichert.");
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Kommentar existiert nicht.");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet.");
		}
		return re;
	}

	@SuppressWarnings("unused")
	@Override
	public ReturnCodeResponse createLocation(int sessionId, String name, String category, String description, String street, String number, int plz, String city) {
		logger.info("createLocation() aufgerufen.");
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		logger.info(session.getUser().getName() + "s Session gefunden.");
		if(session != null) {
			User user = session.getUser();
			Location location = new Location(name,
					category,
					description,
					street,
					number,
					plz,
					city,
					user);
			try {
			dao.persist(location);
			}
			catch(Exception e) {
				logger.error(e.getMessage());
			}
			re.setReturnCode(0);
			re.setMessage("Location gespeichert");
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet.");
		}
		return re;
	}

	@Override
	public ReturnCodeResponse setLocationDetails(int sessionId, LocationTO newLocationDetails) {
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		Location location = dao.findLocationById(newLocationDetails.getId());
		if(session != null) {
			User user = session.getUser();
			if(location != null) {
				if(location.getOwner().getId() == user.getId()) {
					location.setName(newLocationDetails.getName());
					location.setCategory(newLocationDetails.getCategory());
					location.setDescription(newLocationDetails.getDescription());
					location.setStreet(newLocationDetails.getStreet());
					location.setNumber(newLocationDetails.getNumber());
					location.setPlz(newLocationDetails.getPlz());
					location.setCity(newLocationDetails.getCity());
					dao.persist(location);
					re.setReturnCode(0);
					re.setMessage("Änderungen gespeichert.");
				}
				else {
					re.setReturnCode(3);
					re.setMessage("Location gehört nicht dem User!");
				}
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Location existiert nicht!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
		}		
		return re;
	}

	@Override
	public ReturnCodeResponse setUserDetails(int sessionId, UserTO newUser) {
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			User user = session.getUser();
			if (user.getId() == newUser.getId()) {
				user.setName(newUser.getName());
				user.setEmail(newUser.getEmail());
				user.setPassword(newUser.getPassword());
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Es ist nicht erlaubt die Daten eines anderen Users zu ändern!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
		}
		return re;
	}

	@Override
	public UserTO getUserDetails(int sessionId) {
		UserTO userTO = new UserTO();
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			userTO = dtoAssembler.makeDTO(session.getUser());
			userTO.setReturnCode(0);
			userTO.setMessage("User abgerufen.");
		}
		else {
			userTO.setReturnCode(1);
			userTO.setMessage("Kein Benutzer angemeldet!");
		}
		return userTO;
	}

	@Override
	public ReturnCodeResponse deleteUser(int sessionId) {
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null){
			User user = session.getUser();
			dao.remove(session);
			dao.remove(user);
			re.setReturnCode(0);
			re.setMessage("Benutzer erfolgreich gelöscht.");
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
		}
		return re;
	}	
}
