package de.fh_muenster.noobservice;

import java.util.List;

public interface NoobOnlineService {
	
	public boolean login(String email, String password) throws InvalidLoginException;
	
	public boolean logout() throws NoSessionException;
	
	public void createLocation()throws NoSessionException;
	
	public List<String> listCategories()throws NoSessionException;
	
	
}
