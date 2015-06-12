package de.fh_muenster.noob;

import java.io.Serializable;
import java.util.List;

public class CommentTO implements Serializable {
	
	private static final long serialVersionUID = -6300158809866948939L;

	private int id;
	
	private String text;
	
	private List<CommentTO> comments;
	
	private LocationTO location;
	
	private String ownerId;

	public CommentTO(int id, String text, List<CommentTO> comments,
			LocationTO location, String ownerId) {
		this.id = id;
		this.text = text;
		this.comments = comments;
		this.location = location;
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
}
