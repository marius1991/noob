package de.noob.dto;

import java.io.Serializable;
import java.util.List;

public class CommentTO implements Serializable {
	
	private static final long serialVersionUID = -6300158809866948939L;

	private int id;
	
	private String text;
	
	private List<CommentTO> comments;
	
	private LocationTO location;
	
	private UserTO owner;

	public CommentTO(int id, String text, List<CommentTO> comments,
			LocationTO location, UserTO owner) {
		this.id = id;
		this.text = text;
		this.comments = comments;
		this.location = location;
		this.owner = owner;
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

	public List<CommentTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentTO> comments) {
		this.comments = comments;
	}

	public LocationTO getLocation() {
		return location;
	}

	public void setLocation(LocationTO location) {
		this.location = location;
	}

	public UserTO getOwner() {
		return owner;
	}

	public void setOwner(UserTO owner) {
		this.owner = owner;
	}
	
	
}
