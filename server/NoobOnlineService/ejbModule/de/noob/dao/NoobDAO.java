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
 * @author philipp
 *
 */
@Stateless
public class NoobDAO implements NoobDAOLocal {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public User findUserById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public User findUserByName(String name) {
		return em.find(User.class, name);
	}

	@Override
	public User findUserByEmail(String email) {
		return em.find(User.class, email);
	}

	@Override
	public List<Location> findLocationsByName(String name, String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location findLocationById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findLocationsByCategory(String category, String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findLocationsByCity(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment findCommentById(int commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createSession(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public NoobSession findSessionById(int sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeSession(int id) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void persist(Object o) {
		em.persist(o);
	}

	@Override
	public void remove(Object o) {
		em.remove(o);
	}
	
	
	

}
