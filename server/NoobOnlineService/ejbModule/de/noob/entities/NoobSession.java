package de.noob.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Stellt eine User-Session in der Datenbank dar,
 * da nur eingeloggte User die App benutzen dürfen.
 * @author Tim Hembrock, Philipp Ringele
 *
 */
@Entity
public class NoobSession implements Serializable  {
	
	private static final long serialVersionUID = -5959083437227421525L;

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
	
	public NoobSession() {
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
