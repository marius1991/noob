package de.fh_muenster.noob.entities;

import java.util.Date;


public class NoobSession {

	private int id;
	private User user;
	private Date creationTime;
		
	public NoobSession(User user) {
		this.user = user;
		this.creationTime = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationTime() {
		return creationTime;
	}	
}
