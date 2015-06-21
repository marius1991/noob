package de.noob.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import de.noob.dto.CommentTO;
import de.noob.dto.LocationTO;
import de.noob.dto.RatingTO;
import de.noob.dto.UserTO;
import de.noob.entities.Comment;
import de.noob.entities.Image;
import de.noob.entities.Location;
import de.noob.entities.Rating;
import de.noob.entities.User;

/**
 * Dto Assembler stellt Methoden bereit mit denen man aus Entities DTOs erzeugen kann.
 * @author Philipp Ringele
 *
 */
@Stateless
public class DtoAssembler {
	
	/**
	 * Mit dieser Methode kann man ein User-Objekt in ein UserTO umwandeln.
	 * @param user
	 * @return Eine UserTO-Instanz
	 */
	public UserTO makeDTO(User user) {
		UserTO dto = new UserTO(user.getName(), 
				user.getPassword(), 
				user.getEmail(),
				this.makeLocationsDTO(user.getLocations()), 
				this.makeRatingsDTO(user.getRatings()), 
				this.makeCommentsDTO(user.getComments()));
		return dto;
	}
	
	/**
	 * Mit dieser Methode kann man ein Location-Objekt in ein LocationTO umwandeln.
	 * @param location
	 * @return Eine LocationTO-Instanz.
	 */
	public LocationTO makeDTO(Location location) {
		List<byte[]> images = new ArrayList<byte[]>();
		for(Image image: location.getImages()) {
			images.add(image.getData());
		}
		
		LocationTO dto = new LocationTO(location.getId(), 
				location.getName(), 
				location.getCategory(),
				location.getDescription(), 
				location.getStreet(), 
				location.getNumber(), 
				location.getPlz(), 
				location.getCity(),  
				location.getAverageRating(), 
				makeRatingsDTO(location.getRatings()), 
				makeCommentsDTO(location.getComments()), 
				location.getOwner().getEmail(),
				location.getOwner().getName(),
				images);
		return dto;
	}
	/**
	 * Mit dieser Methode kann man ein Location-Objekt in ein LocationTO umwandeln, allerdings enthaelt dieses
	 * LocationTO nicht die Bilder, die im Location-Objekt gespeichert sind. 
	 * @param location
	 * @return Eine LocationTO-Instanz ohne images.
	 */
	public LocationTO makeDTOWithoutImages(Location location) {		
		LocationTO dto = new LocationTO(location.getId(), 
				location.getName(), 
				location.getCategory(),
				location.getDescription(), 
				location.getStreet(), 
				location.getNumber(), 
				location.getPlz(), 
				location.getCity(),  
				location.getAverageRating(), 
				makeRatingsDTO(location.getRatings()), 
				makeCommentsDTO(location.getComments()), 
				location.getOwner().getEmail(),
				location.getOwner().getName(),
				null);
		return dto;
	}
	


	/**
	 * Mit dieser Methode kann man ein Rating-Objekt in ein RatingTO umwandeln.
	 * @param rating
	 * @return Eine RatingTO-Instanz.
	 */
	public RatingTO makeDTO(Rating rating) {
		RatingTO dto = new RatingTO(rating.getId(),
				rating.getValue(),
				rating.getLocation().getId(),
				rating.getOwner().getEmail());
		return dto;
	}
	
	/**
	 * Mit dieser Methode kann man ein Comment-Objekt in ein CommentTO umwandeln.
	 * @param comment
	 * @return Eine CommentTO-Instanz
	 */
	public CommentTO makeDTO(Comment comment) {
		CommentTO dto = new CommentTO(comment.getId(), 
				comment.getText(), 
				comment.getLocation().getId(), 
				comment.getOwner().getEmail(),
				comment.getOwner().getName(),
				comment.getDate());
		return dto;
	}

	/**
	 * Wandelt eine Liste mit User-Objekten in eine Liste mit UserTOs um.
	 * @param users
	 * @return Eine Liste mit UserTOs.
	 */
	public List<UserTO> makeUsersDTO(List<User> users) {
		List<UserTO> toList = new ArrayList<UserTO>();
		for(User user: users) {
			toList.add(makeDTO(user));
		}
		return toList;		  
	}

	/**
	 * Wandelt eine Liste mit Location-Objekten in eine Liste mit LocationTOs um.
	 * @param locations
	 * @return Eine Liste mit LocationTOs.
	 */
	public List<LocationTO> makeLocationsDTO(List<Location> locations) {
		List<LocationTO> toList = new ArrayList<LocationTO>();
		for(Location location: locations) {
			toList.add(makeDTOWithoutImages(location));
		}
		return toList;		  
	}

	/**
	 * Wandelt eine Liste mit Rating-Objekten in eine Liste mit RatingTOs um.
	 * @param ratings
	 * @return Eine Liste mit RatingTOs.
	 */
	public List<RatingTO> makeRatingsDTO(List<Rating> ratings) {
		List<RatingTO> toList = new ArrayList<RatingTO>();
		for(Rating rating: ratings) {
			toList.add(makeDTO(rating));
		}	
		return toList;	
	}

	/**
	 * Wandelt eine Liste mit Comment-Objekten in eine Liste mit CommentTOs um.
	 * @param comments
	 * @return Eine Liste mit CommentTOs.
	 */
	public List<CommentTO> makeCommentsDTO(List<Comment> comments) {
		List<CommentTO> toList = new ArrayList<CommentTO>();
		for(Comment comment: comments) {
			toList.add(makeDTO(comment));
		}	
		return toList;
	}
}
