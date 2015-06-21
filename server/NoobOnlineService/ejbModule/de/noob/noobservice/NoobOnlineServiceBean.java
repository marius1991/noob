package de.noob.noobservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.jboss.logging.Logger;
import org.jboss.ws.api.annotation.WebContext;

import de.noob.dao.NoobDAOLocal;
import de.noob.dto.CategoryListResponse;
import de.noob.dto.CityListResponse;
import de.noob.dto.LocationListResponse;
import de.noob.dto.LocationTO;
import de.noob.dto.ReturnCodeResponse;
import de.noob.dto.UserLoginResponse;
import de.noob.dto.UserTO;
import de.noob.entities.Image;
import de.noob.entities.Location;
import de.noob.entities.NoobSession;
import de.noob.entities.User;
import de.noob.util.DtoAssembler;

/**
 * Diese Bean implemetiert das Interface zwischen Client und Server, alle enthaltenen Methoden können 
 * über den WebService mittels SOAP aufgerufen werden.
 * @author Philipp Ringele
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
	
	/**
	 * Mt dieser EJB werden Mails an Benutzer gesendet.
	 */
	@EJB
	private MailerBean mailer;
	
	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#register(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ReturnCodeResponse register(String username, String email, String password, String passwordConfirmation) {
		logger.info("register() aufgerufen.");		
		ReturnCodeResponse re = new ReturnCodeResponse();
		//Schaue nach ob username und email noch frei sind.
		if (dao.findUserByName(username) == null && dao.findUserByEmail(email) == null) {
			if(password.equals(passwordConfirmation)) {
				User user = new User(username, email, password);
				dao.persist(user);
				re.setReturnCode(0);
				re.setMessage(username + " erfolgreich registriert.");
				mailer.sendWelcomeMail(user);
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

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public UserLoginResponse login(String email, String password) {
		logger.info("login() aufgerufen!");
		logger.info("Email: " + email);
		logger.info("Passwort: " + password);
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
				re.setMessage("Email oder Passwort falsch!");
				logger.info("Email oder Passwort falsch!");
			}	
		}
		else {
			re.setReturnCode(2);
			re.setMessage("User nicht vorhanden!");
			logger.info("User nicht vorhanden!");
		}
		return re;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#logout(int)
	 */
	@Override
	public ReturnCodeResponse logout(int sessionId) {
		logger.info("logout() aufgerufen.");
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);		
		if(session != null) {
			dao.remove(session);
			re.setReturnCode(0);
			re.setMessage("Erfolgreich ausgeloggt.");
			logger.info("Erfolgreich ausgeloggt.");
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Keine Session vorhanden!");
		}
		
		return re;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#listCategories()
	 */
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

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#listCities()
	 */
	@Override
	public CityListResponse listCities() {
		logger.info("listCities() aufgerufen.");
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
			re.setMessage("Keine Städte vorhanden!");
			logger.info("Keine Städte vorhanden!");
		}

		return re;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#listLocationsWithCategory(java.lang.String, java.lang.String)
	 */
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
				
						//Zu Testzwecken!
						if( locations.get(0).getRatings() != null ){
							if(locations.get(0).getRatings().isEmpty()) {
								logger.info("Ratings sind leer!");
							}
							else {
								logger.info("Ratings vorhanden.");
							}
						}
						//Zu Testzwecken!
						if(locations.get(0).getComments() != null) {
							if(locations.get(0).getComments().isEmpty()) {
								logger.info("Comments sind leer!");
							}
							else {
								logger.info("Comments vorhanden.");
							}
						}	
						//Zu Testzwecken!
						if(locations.get(0).getImages() != null) {
							if(locations.get(0).getImages().isEmpty()) {
								logger.info("Images sind leer!");
							}
							else {
								logger.info("Images vorhanden.");
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

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#listLocationsWithName(java.lang.String, java.lang.String)
	 */
	@Override
	public LocationListResponse listLocationsWithName(String name, String city) {
		logger.info("listLocationsWithName aufgerufen.");
		LocationListResponse re = new LocationListResponse();
		List<Location> locations = dao.findLocationsByName(name, city);
		if(locations != null) {
			if(!locations.isEmpty()) {
				re.setLocations(dtoAssembler.makeLocationsDTO(locations));
				re.setReturnCode(0);
				re.setMessage(locations.size() + " Location(s) gefunden.");
				logger.info(locations.size() + " Location(s) gefunden.");
			}
			else {
				re.setReturnCode(1);
				re.setMessage(locations.size() + " Location(s) gefunden.");
				logger.info(locations.size() + " Location(s) gefunden.");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("0 Locations gefunden.");
			logger.info("NULL Locations gefunden.");
		}
		return re;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#giveRating(int, int, int)
	 */
	@Override
	public ReturnCodeResponse giveRating(int sessionId, int locationId, int value) {
		logger.info("giveRating() aufgerufen.");
		ReturnCodeResponse re = new ReturnCodeResponse();
		Location location = dao.findLocationById(locationId);
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			User user = session.getUser();
			if(location != null) {
				location.addRating(user, value);
				dao.persist(location);	
				re.setReturnCode(0);
				re.setMessage("Bewertung mit Wert "+Integer.toString(value)+" gespeichert.");
				logger.info("Rating mit Wert "+Integer.toString(value)+" gespeichert.");
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Location existiert nicht!");
				logger.info("Location existiert nicht!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
			logger.info("Kein Benutzer angemeldet!");
		}
		return re;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#commentOnLocation(int, int, java.lang.String)
	 */
	@Override
	public ReturnCodeResponse commentOnLocation(int sessionId, int locationId, String text) {
		logger.info("commentOnLocation() aufgerufen.");
		ReturnCodeResponse re = new ReturnCodeResponse();
		Location location = dao.findLocationById(locationId);
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			User user = session.getUser();
			if(location != null) {
				if(text.length() <= 1000) {
					location.addComment(user, text);
					dao.persist(location);				
					re.setReturnCode(0);
					re.setMessage("Kommentar wurde gespeichert.");
					logger.info("Kommentartext: " + text);
					logger.info("Kommentar wurde gespeichert.");
				}
				else {
					re.setReturnCode(3);
					re.setMessage("Kommentar darf maximal 1000 Zeichen enthalten!");
					logger.info("Kommentar darf maximal 1000 Zeichen enthalten!");
				}
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Location existiert nicht!");
				logger.info("Location existiert nicht!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
			logger.info("Kein Benutzer angemeldet!");
		}
		return re;
	}
	
	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#createLocation(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, byte[])
	 */
	@Override
	public ReturnCodeResponse createLocation(int sessionId, String name, String category, String description, String street, String number, int plz, String city, byte[] imageData) {
		logger.info("createLocation() aufgerufen.");
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			if(dao.findLocationsByName(name, city).isEmpty()) {
				User user = session.getUser();
				Location location = new Location(name,
						category,
						description,
						street,
						number,
						plz,
						city,
						user);
				dao.persist(location);
				if(imageData != null) {
					Image image = new Image();
					image.setData(imageData);
					image.setOwner(user);
					image.setLocation(location);
					dao.persist(image);
				}
				re.setReturnCode(0);
				re.setMessage("Location gespeichert.");
				logger.info("Location gespeichert.");
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Eine Location mit Namen " + name + " existiert schon in " + city + "!");
				logger.info("Eine Location mit Namen " + name + " existiert schon in " + city + "!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
			logger.info("Kein Benutzer angemeldet!");
		}
		return re;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#addImageToLocation(int, int, byte[])
	 */
	@Override
	public ReturnCodeResponse addImageToLocation(int sessionId, int locationId, byte[] image) {
		logger.info("addImageToLocation() aufgerufen");
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			Location location = dao.findLocationById(locationId);
			if(location != null) {
				if(location.getImages().size() < 10) {
					location.addImage(image, session.getUser());
					dao.persist(location);
					re.setReturnCode(0);
					re.setMessage("Bild erfolgreich gespeichert.");
					logger.info("Bild erfolgreich gespeichert.");
				}
				else {
					re.setReturnCode(3);
					re.setMessage("Maximale Anzahl an Bildern erreicht!");
					logger.info("Maximale Anzahl an Bildern erreicht!");
				}

			}
			else {
				re.setReturnCode(2);
				re.setMessage("Location existiert nicht!");
				logger.info("Location existiert nicht!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
			logger.info("Kein Benutzer angemeldet!");
		}		
		return re;
	}


	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#setLocationDetails(int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String)
	 */
	@Override
	public ReturnCodeResponse setLocationDetails(int sessionId, int locationId, String name, String category, String description, String street, String number, int plz, String city) {
		logger.info("setLocationDetails() aufgerufen.");
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		Location location = dao.findLocationById(locationId);
		if(session != null) {
			User user = session.getUser();
			if(location != null) {
				if(location.getOwner().getEmail().equals(user.getEmail())) {
					location.setName(name);
					location.setCategory(category);
					location.setDescription(description);
					location.setStreet(street);
					location.setNumber(number);
					location.setPlz(plz);
					location.setCity(city);
					//location.setImage(image);
					dao.persist(location);
					re.setReturnCode(0);
					re.setMessage("Änderungen gespeichert.");
					logger.info("Änderungen gespeichert.");
				}
				else {
					re.setReturnCode(3);
					re.setMessage("Location gehört nicht dem User!");
					logger.info("Location gehört nicht dem User!");
				}
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Location existiert nicht!");
				logger.info("Location existiert nicht!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
			logger.info("Kein Benutzer angemeldet!");
		}		
		return re;
	}
	
	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#getLocationDetails(int)
	 */
	@Override
	public LocationTO getLocationDetails(int locationId) {
		logger.info("getLocationDetails() aufgerufen.");
		LocationTO re = new LocationTO();
		Location location = dao.findLocationById(locationId);
		if(location != null) {
			re = dtoAssembler.makeDTO(location);
			re.setReturnCode(0);
			re.setMessage("Location erfolgreich abgerufen.");
			logger.info("Location erfolgreich abgerufen.");
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Location existiert nicht!");
			logger.info("Location existiert nicht!");
		}
		return re;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#setUserDetails(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ReturnCodeResponse setUserDetails(int sessionId, String name, String password, String passwordConfirmation) {
		logger.info("setUserDetails() aufgerufen.");
		logger.info("Passwort: " + password + " Confirmation: " + passwordConfirmation);
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			if(password.equals(passwordConfirmation)) {
				User user = session.getUser();
				user.setName(name);
				user.setPassword(password);
				dao.persist(user);
				re.setReturnCode(0);
				re.setMessage("Daten erfolgreich gespeichert.");
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Passwörter stimmen nicht überein!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
		}
		return re;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#getUserDetails(int)
	 */
	@Override
	public UserTO getUserDetails(int sessionId) {
		logger.info("getUserDetails() aufgerufen.");
		logger.info("SessionId: " + sessionId);
		UserTO userTO = new UserTO();
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null) {
			userTO = dtoAssembler.makeDTO(session.getUser());
			userTO.setReturnCode(0);
			userTO.setMessage("User abgerufen.");
			logger.info("User abgerufen.");
		}
		else {
			userTO.setReturnCode(1);
			userTO.setMessage("Kein Benutzer angemeldet!");
			logger.info("Kein Benutzer angemeldet");
		}
		return userTO;
	}

	/* (non-Javadoc)
	 * @see de.noob.noobservice.NoobOnlineService#deleteUser(int, java.lang.String)
	 */
	@Override
	public ReturnCodeResponse deleteUser(int sessionId, String password) {
		logger.info("deleteUser() aufgerufen.");
		ReturnCodeResponse re = new ReturnCodeResponse();
		NoobSession session = dao.findSessionById(sessionId);
		if(session != null){
			User user = session.getUser();
			if(user.getPassword().equals(password)) {
				mailer.sendLeaveMail(user);
				dao.remove(session);
				dao.remove(user);
				re.setReturnCode(0);
				re.setMessage("Benutzer erfolgreich gelöscht.");
				logger.info("Benutzer erfolgreich gelöscht.");
			}
			else {
				re.setReturnCode(2);
				re.setMessage("Passwort ist nicht korrekt!");
				logger.info("Passwort ist nicht korrekt!");
			}
		}
		else {
			re.setReturnCode(1);
			re.setMessage("Kein Benutzer angemeldet!");
			logger.info("Kein Benutzer angemeldet!");
		}
		return re;
	}
}