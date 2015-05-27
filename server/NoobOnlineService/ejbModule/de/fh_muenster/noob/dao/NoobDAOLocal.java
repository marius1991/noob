package de.fh_muenster.noob.dao;

import javax.ejb.Local;

import de.fh_muenster.noob.entities.Location;
import de.fh_muenster.noob.entities.User;

@Local
public interface NoobDAOLocal {
	
	public User findUserByName(String name);
	
	public User findUserById(int id);
	
	public Location findLocationByName(String name);
	
	public Location findLocationById(int id);
	
	public Location findLocationByCategory(String category);
	
	public void createSession(User user);
	
	public void closeSession(int id);
	
}
