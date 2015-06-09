package de.noob.dao;

import javax.ejb.Local;

import de.noob.entities.Location;
import de.noob.entities.User;

@Local
public interface NoobDAOLocal {
	
	public User findUserByName(String name);
	
	public User findUserById(int id);
	
	public User findUserByEmail(String email);
	
	public Location findLocationByName(String name);
	
	public Location findLocationById(int id);
	
	public Location findLocationByCategory(String category);
	
	public void createSession(User user);
	
	public void closeSession(int id);
	
}
