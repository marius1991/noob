package de.noob.dto;

import java.io.Serializable;

/**
 * Diese Klasse repräsentiert ein de.noob.entities.Comment nur mit Getter und Setter Methoden, sodass der
 * Serialisierungsaufwand verringert ist wenn ein Comment zum Client gesendet wird.
 * @author Philipp Ringele
 *
 */
public class CommentTO implements Serializable {
	
	private static final long serialVersionUID = -6300158809866948939L;

	private int id;
	
	private String text;
	
	private int locationId;
	
	private String ownerId;
	
	private String ownerName;
	
	private String date;

	public CommentTO(int id,
			String text,
			int locationId,
			String ownerId,
			String ownerName,
			String date) {		
		this.id = id;
		this.text = text;
		this.locationId = locationId;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.date = date;
	}
	
	public CommentTO() {
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
