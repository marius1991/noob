package de.fh_muenster.noob.location;

import java.util.ArrayList;

import de.fh_muenster.noob.user.User;

public class Comment {

	private int id;
	
	private String text;
	
	private ArrayList<Comment> comments;
	
	private Location location;
	
	private User owner;
	
	
	

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

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

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
	
	public void answer(Comment answerComment){
	
	}
	
	public void delete(){
		
	}
	
}
