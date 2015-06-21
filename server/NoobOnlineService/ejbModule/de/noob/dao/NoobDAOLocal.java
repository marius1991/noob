package de.noob.dao;

import java.util.List;

import javax.ejb.Local;

import de.noob.entities.Comment;
import de.noob.entities.Location;
import de.noob.entities.NoobSession;
import de.noob.entities.User;

/**
 * 
 * @author Philipp Ringele
 *
 */
@Local
public interface NoobDAOLocal {
	
	public User findUserByName(String name);
	
	public User findUserByEmail(String email);
	
	public List<String> listCities();

	public List<Location> findLocationsByName(String name, String city);

	public Location findLocationById(int id);
	
	public List<Location> findLocationsByCategory(String category, String city);
	
	public List<Location> findLocationsByCity(String city);
	
	public NoobSession findSessionById(int sessionId);
	
	public Comment findCommentById(int commentId);

	public void persist(Object o);
	
	public void remove(Object o);
	
}
