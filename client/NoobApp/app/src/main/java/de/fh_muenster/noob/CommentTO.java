package de.fh_muenster.noob;

import java.io.Serializable;

/**
 * Objekte dieser Klasse werden vom Server gesendet, wenn eine Location Kommentare besitzt
 * @author marius, philipp
 */
public class CommentTO implements Serializable {
	
	private static final long serialVersionUID = -6300158809866948939L;

	private int id;
	
	private String text;
	
	private int locationId;
	
	private String ownerId;

	private String date;

	private String ownerName;

	public CommentTO () {}

	public CommentTO(int id, String text, int locationId, String ownerId) {
		this.id = id;
		this.text = text;
		this.locationId = locationId;
		this.ownerId = ownerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
