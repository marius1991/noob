package de.fh_muenster.noob;

import java.io.Serializable;


public class CommentTO implements Serializable {
	
	private static final long serialVersionUID = -6300158809866948939L;

	private int id;
	
	private String text;
	
	//private List<CommentTO> comments;
	
	private int locationId;
	
	private String ownerId;

	private String date;

	private String ownerName;
	
	//private int superCommentId;

	public CommentTO () {}

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

//	public int getSuperCommentId() {
//		return superCommentId;
//	}
//
//	public void setSuperCommentId(int superCommentId) {
//		this.superCommentId = superCommentId;
//	}
}
