package de.noob.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.noob.entities.Comment;
import de.noob.entities.Location;
import de.noob.entities.NoobSession;
import de.noob.entities.User;

/**
 * Enthält sämtliche Logik für Datenbankzugriffe.
 * @author philipp, Tim
 *
 */
@Stateless
public class NoobDAO implements NoobDAOLocal {
	
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
		return (User) em.createQuery("SELCET u FROM USER u WHERE u.name = '" + name + "' ").getSingleResult();
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
		return em.find(User.class, email);
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
		
		return em.createQuery("SELECT l FROM LOCATION l WHERE l.city = :stadt AND l.name= :name").setParameter("stadt",city).setParameter("name",name).getResultList();
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
		
		return em.createQuery("SELECT l FROM LOCATION l WHERE l.city = :stadt AND l.category= :category").setParameter("stadt",city).setParameter("category",category).getResultList();
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
		
		return em.createQuery("SELECT l FROM LOCATION l WHERE l.city = :stadt").setParameter("stadt",city).getResultList();
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
		
		return em.find(NoobSession.class, sessionId);
	}

	
	@Override
	public void persist(Object o) {
		em.persist(o);
	}

	@Override
	public void remove(Object o) {
		em.remove(o);
	}


	@Override
	public List<String> listCities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
