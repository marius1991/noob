package de.noob.dto;

import java.io.Serializable;


public class CommentTO implements Serializable {
	
	private static final long serialVersionUID = -6300158809866948939L;

	private int id;
	
	private String text;
	
	//private List<CommentTO> comments;
	
	private int locationId;
	
	private String ownerId;
	
	//private int superCommentId;

	public CommentTO(int id,
			String text,
			//List<CommentTO> comments,
			int locationId,
			String ownerId
			//int superCommentId
			) {
		this.id = id;
		this.text = text;
		//this.comments = comments;
		this.locationId = locationId;
		this.ownerId = ownerId;
		//this.superCommentId = superCommentId;
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

//	public List<CommentTO> getComments() {
//		return comments;
//	}
//
//	public void setComments(List<CommentTO> comments) {
//		this.comments = comments;
//	}

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

//	public int getSuperCommentId() {
//		return superCommentId;
//	}
//
//	public void setSuperCommentId(int superCommentId) {
//		this.superCommentId = superCommentId;
//	}
}
