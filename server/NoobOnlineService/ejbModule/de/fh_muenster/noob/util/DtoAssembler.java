package de.fh_muenster.noob.util;

import javax.ejb.Stateless;

import de.fh_muenster.noob.dto.LocationTO;
import de.fh_muenster.noob.dto.UserTO;
import de.fh_muenster.noob.entities.Location;
import de.fh_muenster.noob.entities.User;


/**
 * 
 * @author philipp
 *
 */
@Stateless
public class DtoAssembler {

  public UserTO makeDTO(User user) {
	  UserTO dto = new UserTO(user.getId(), user.getName(), user.getPassword(), user.getEmail(),
			  	user.getLocations(), user.getRatings(), user.getComments());
	  return dto;
  }
	
  public LocationTO makeDTO(Location location) {
	  LocationTO dto = new LocationTO(location.getId(), location.getName(), location.getCategory(),
			  location.getDescription(), location.getStreet(), location.getNumber(), 
			  location.getPlz(), location.getCity(), location.getCoordinates(), 
			  location.getAverageRating(), location.getRatings(), location.getComments(), 
			  location.getOwner());
	  return dto;
  }

}
