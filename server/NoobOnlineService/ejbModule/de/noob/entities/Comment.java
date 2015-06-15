package de.noob.entities;

import java.io.Serializable;

import javax.persistence.*;

/*
 * @author Tim, Philipp
 */
@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = -682681573951550447L;

	@Id
	@GeneratedValue
	private int id;
	
	private String text;
	
	//@OneToMany (mappedBy = "superComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//private List<Comment> comments;
	
	@ManyToOne
	private Location location;
	
	@ManyToOne
	private User owner;
	
	//@ManyToOne
	//private Comment superComment;
	
	
	
	public Comment() {
	}
	
	public Comment( User user, String text, Location location){
		this.setOwner(user);
		this.setText(text);
		this.setLocation(location);
		//this.setSuperComment(comment);
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

//	public List<Comment> getComments() {
//		return comments;
//	}

//	public void setComments(List<Comment> comments) {
//		this.comments = comments;
//	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

//	public Comment getSuperComment() {
//		return superComment;
//	}
//
//	public void setSuperComment(Comment superComment) {
//		this.superComment = superComment;
//	}
//
//	public void addComment(User user, String text) {
//		this.comments.add(new Comment(user, text, null, this));	
//	}
	
}
