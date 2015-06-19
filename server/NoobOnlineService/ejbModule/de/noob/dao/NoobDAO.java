package de.noob.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import de.noob.entities.Comment;
import de.noob.entities.Location;
import de.noob.entities.NoobSession;
import de.noob.entities.User;

/**
 * Enthält sämtliche Logik für Datenbankzugriffe.
 * @author Philipp Ringele, Tim Hembrock
 *
 */
@Stateless
public class NoobDAO implements NoobDAOLocal {
	
	private static final Logger logger = Logger.getLogger(NoobDAO.class);
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * User über die id finden.
	 * Da Id nicht der Primärschlüssel ist, muss hier ein Query benutzt werden.
	 * 
	 * @param id Eindeutige ID des Users
	 * 
	 * @return User Objekt
	 *
	 */
	@Override
	public User findUserById(int id) {
		logger.info("DB-Query: SELECT u FROM USER u WHERE u.id = '" + id + "' ");
		return (User) em.createQuery("SELCET u FROM USER u WHERE u.id = '" + id + "' ").getSingleResult();	
	}

	
	/**
	 * User über den Namen finden.
	 * Da Name nicht der Primärschlüssel ist, muss hier ein Query benutzt werden.
	 * 
	 * @param name Name des Users
	 * 
	 * @return User Objekt
	 */
	@Override
	public User findUserByName(String name) {
		logger.info("DB-Query: " + "SELECT u FROM USER u WHERE u.name = '" + name + "' ");
		try {
			return (User) em.createQuery("SELECT u FROM User u WHERE u.name LIKE '" + name + "' ").getSingleResult();
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}


	/**
	 * User über die Emailadresse finden.
	 * 
	 * Da Email der Primärschlüssel von User ist kann man hier direkt über
	 * den EntityManager die Methode find()  benutzen.
	 * 
	 * @param email E-Mailadresse des Users
	 * 
	 * @return User Object
	 */
	@Override
	public User findUserByEmail(String email) {
		logger.info("DB-Query: em.find(User.class, email);");
		User user;
		try {
			user = em.find(User.class, email);
			return user;
		}
		catch (Exception e) {
			return null;
		}
	}

	
	/**
	 * Durch den Aufruf werden alle Locations mit passenden Namen und passender
	 * Stadt als Liste zurückgegeben.
	 * 
	 * @param name Name der Location
	 * @param city Stadt in der die Location zu finden ist
	 * 
	 * @return Liste von Locations
	 */
	@Override
	public List<Location> findLocationsByName(String name, String city) {
		logger.info("DB-Query: " + "SELECT l FROM Location l WHERE l.city = :stadt AND lower(l.name) = lower(:name)");
		try {
			return em.createQuery("SELECT l FROM Location l WHERE l.city = :stadt AND lower(l.name) = lower(:name)", Location.class).setParameter("stadt",city).setParameter("name",name).getResultList();
		}
		catch(Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	/**
	 * Location über die eindeutige Id finden.
	 * 
	 * @param id ID der Location
	 * 
	 * @return Location Objekt
	 */
	@Override
	public Location findLocationById(int id) {
		logger.info("DB-Query: em.find(Location.class, id);");
		return em.find(Location.class, id);
	}
	
	/**
	 * Durch den Aufruf wird eine Liste mit Locations zurückgegebe mit passender Kategorie
	 * und passender Stadt
	 * 
	 * @param category Kategory
	 * @param city Stadt in der die Location zu finden ist
	 * 
	 * @return Liste von Locations
	 */
	@Override
	public List<Location> findLocationsByCategory(String category, String city) {
		logger.info("DB-Query: " + "SELECT l FROM Location l WHERE l.city = " + city + " AND l.category = " + category);
		try {
			return em.createQuery("SELECT l FROM Location l WHERE l.city = :stadt AND l.category = :category", Location.class).setParameter("stadt",city).setParameter("category",category).getResultList();
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	/**
	 * Durch den Aufruf wird eine Liste mit Locations zurückgegebe mit passender Stadt
	 * 
	 * @param city Stadt in der die Location zu finden ist
	 * 
	 * @return Liste von Locations
	 */
	@Override
	public List<Location> findLocationsByCity(String city) {	
		logger.info("DB-Query: " + "SELECT l FROM LOCATION l WHERE l.city = :stadt");
		try {
			return em.createQuery("SELECT l FROM LOCATION l WHERE l.city = :stadt", Location.class).setParameter("stadt",city).getResultList();
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	/**
	 * Kommentar über die ID finden.
	 * 
	 * Da die ID der Primärschlüssel von Kommentar ist kann man hier direkt über
	 * den EntityManager die Methode find()  benutzen.
	 * 
	 * @param commentId ID des Kommentars
	 * 
	 * @return Kommentar Object
	 */
	@Override
	public Comment findCommentById(int commentId) {
		logger.info("DB-Query: em.find(Comment.class, commentId);");
		return em.find(Comment.class, commentId);
	}

	
	/**
	 * Session über die ID finden.
	 * 
	 * Da die ID der Primärschlüssel von NoobSession ist kann man hier direkt über
	 * den EntityManager die Methode find()  benutzen.
	 * 
	 * @param sessionId ID der Session
	 * 
	 * @return NoobSession Object
	 */
	@Override
	public NoobSession findSessionById(int sessionId) {
		logger.info("DB-Query: em.find(NoobSession.class, sessionId);");
		return em.find(NoobSession.class, sessionId);
	}

	
	@Override
	public void persist(Object o) {
		logger.info("DB-Query: em.persist(o);");
		em.persist(o);
	}

	@Override
	public void remove(Object o) {
		logger.info("DB-Query: em.remove(o);");
		em.remove(o);
	}
	
	@Override
	public List<String> listCities() {
		try {
			logger.info("DB-Query: SELECT DISTINCT l.city FROM Location l ORDER BY l.city ASC");
			return em.createQuery("SELECT DISTINCT l.city FROM Location l ORDER BY l.city ASC", String.class).getResultList();			
		}
		catch(Exception e){
			logger.info(e.getMessage());
			return null;
		}
	}
	
	
	

}
