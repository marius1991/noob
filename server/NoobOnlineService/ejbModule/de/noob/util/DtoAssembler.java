package de.noob.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import de.noob.dto.CommentTO;
import de.noob.dto.LocationTO;
import de.noob.dto.RatingTO;
import de.noob.dto.UserTO;
import de.noob.entities.Comment;
import de.noob.entities.Location;
import de.noob.entities.Rating;
import de.noob.entities.User;

/**
 * Dto Assembler stellt Methoden bereit mit denen man aus Entities DTOs erzeugen kann.
 * @author philipp
 *
 */
@Stateless
public class DtoAssembler {
	
	/**
	 * Mit dieser Methode kann man aus einem User-Objekt ein UserTO-Objekt erzeugen. 
	 * @param user
	 * @return
	 */
	public UserTO makeDTO(User user) {
		UserTO dto = new UserTO(user.getId(), 
				user.getName(), 
				user.getPassword(), 
				user.getEmail(),
				this.makeLocationsDTO(user.getLocations()), 
				this.makeRatingsDTO(user.getRatings()), 
				this.makeCommentsDTO(user.getComments()));
		return dto;
	}
	
	/**
	 * 
	 * @param location
	 * @return
	 */
	public LocationTO makeDTO(Location location) {
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
				location.getOwner().getEmail());
		return dto;
	}

	/**
	 * 
	 * @param rating
	 * @return
	 */
	public RatingTO makeDTO(Rating rating) {
		RatingTO dto = new RatingTO(rating.getId(),
				rating.getValue(),
				rating.getLocation().getId(),
				rating.getOwner().getEmail());
		return dto;
	}
	
	/**
	 * 
	 * @param comment
	 * @return
	 */
	public CommentTO makeDTO(Comment comment) {
		CommentTO dto = new CommentTO(comment.getId(), 
				comment.getText(), 
				this.makeCommentsDTO(comment.getComments()),
				this.makeDTO(comment.getLocation()), 
				comment.getOwner().getEmail());
		return dto;
	}

	/**
	 * @param users
	 * @return
	 */
	public List<UserTO> makeUsersDTO(List<User> users) {
		List<UserTO> toList = new ArrayList<UserTO>();
		for(User user: users) {
			toList.add(makeDTO(user));
		}
		return toList;		  
	}

	/**
	 * @param locations
	 * @return
	 */
	public List<LocationTO> makeLocationsDTO(List<Location> locations) {
		List<LocationTO> toList = new ArrayList<LocationTO>();
		for(Location location: locations) {
			toList.add(makeDTO(location));
		}
		return toList;		  
	}

	/**
	 * @param ratings
	 * @return
	 */
	public List<RatingTO> makeRatingsDTO(List<Rating> ratings) {
		List<RatingTO> toList = new ArrayList<RatingTO>();
		for(Rating rating: ratings) {
			toList.add(makeDTO(rating));
		}	
		return toList;	
	}

	/**
	 * @param comments
	 * @return
	 */
	private List<CommentTO> makeCommentsDTO(List<Comment> comments) {
		List<CommentTO> toList = new ArrayList<CommentTO>();
		for(Comment comment: comments) {
			toList.add(makeDTO(comment));
		}	
		return toList;
	}

}
