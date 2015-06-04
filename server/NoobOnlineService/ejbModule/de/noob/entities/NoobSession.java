package de.noob.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class NoobSession {
	
	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne
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
