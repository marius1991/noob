package de.noob.noobservice;

import de.noob.dao.NoobDAOLocal;
import de.noob.dto.CategoryListResponse;
import de.noob.dto.LocationListResponse;
import de.noob.dto.LocationTO;
import de.noob.dto.LoginResponse;
import de.noob.dto.ReturncodeResponse;
import de.noob.dto.UserTO;
import de.noob.entities.Comment;
import de.noob.entities.Location;
import de.noob.entities.User;
import de.noob.util.DtoAssembler;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.jboss.ws.api.annotation.WebContext;

/**
 * Session Bean implementation class NoobOnlineServiceBean
 * @author philipp
 * 
 */
@WebService
@WebContext(contextRoot="/noob")
@Stateless
public class NoobOnlineServiceBean implements NoobOnlineService {
	
	/**
	 * Über diese EJB werden alle Abfragen auf die Datenbank abgesetzt.
	 */
	@EJB(beanName = "NoobDAO", beanInterface = NoobDAOLocal.class)
    private NoobDAOLocal dao;
	
	/**
	 * Mit dieser EJB werden Transfer Objects erzeugt.
	 */
	@EJB
	private DtoAssembler dtoAssembler;
	
	/**
	 * @see NoobOnlineService#register(String, String, String, String)
	 */
	public ReturncodeResponse register(String username, String email, String password, String passwordConfirmation) {
	    // TODO Auto-generated method stub
			return null;
	}

	/**
	 * @see NoobOnlineService#login(String, String)
	 */
	public LoginResponse login(String email, String password) {
	    // TODO Auto-generated method stub
			return null;
	}

	/**
     * @see NoobOnlineService#logout()
     */
    public ReturncodeResponse logout() {
        // TODO Auto-generated method stub
			return null;
    }

	/**
	 * @see NoobOnlineService#listCategories()
	 */
	public CategoryListResponse listCategories() {
	    // TODO Auto-generated method stub
			return null;
	}

	/**
	 * @see NoobOnlineService#listLocationWithCategory(String, String)
	 */
	public LocationListResponse listLocationWithCategory(String category, String city) {
	    // TODO Auto-generated method stub
			return null;
	}

	/**
     * @see NoobOnlineService#listLocationsWithName(String, String)
     */
    public LocationListResponse listLocationsWithName(String name, String city) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
	 * @see NoobOnlineService#listAllLocations(String)
	 */
	public LocationListResponse listAllLocations(String city) {
	    // TODO Auto-generated method stub
			return null;
	}

	/**
     * @see NoobOnlineService#giveRating(User, Location, int)
     */
    public ReturncodeResponse giveRating(User user, Location location, int value) {
        // TODO Auto-generated method stub
    	return null;
    }

	/**
     * @see NoobOnlineService#commentOnLocation(User, Location, String)
     */
    public ReturncodeResponse commentOnLocation(User user, Location location, String text) {
        // TODO Auto-generated method stub
    	return null;
    }

	/**
     * @see NoobOnlineService#commentOnComment(User, Comment, String)
     */
    public ReturncodeResponse commentOnComment(User user, Comment comment, String text) {
        // TODO Auto-generated method stub
    	return null;
    }

	/**
	 * @see NoobOnlineService#createLocation(String, String, String, String, String, String, String, String, User)
	 */
	public ReturncodeResponse createLocation(String name, String category, String description, String street, String number, String plz, String city, String coordinates, User owner) {
	    // TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see NoobOnlineService#getLocationDetails(Location)
	 */
	public LocationTO getLocationDetails(Location location) {
	    // TODO Auto-generated method stub
			return null;
	}

	/**
	 * @see NoobOnlineService#setLocationDetails(Location)
	 */
	public ReturncodeResponse setLocationDetails(Location location) {
	    // TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see NoobOnlineService#setUserDetails(User)
	 */
	public ReturncodeResponse setUserDetails(User user) {
	    // TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see NoobOnlineService#getUserDetails(User)
	 */
	public UserTO getUserDetails(User user) {
	    // TODO Auto-generated method stub
			return null;
	}

	/**
	 * @see NoobOnlineService#deleteUser(User)
	 */
	public ReturncodeResponse deleteUser(User user) {
	    // TODO Auto-generated method stub
		return null;
	}

}
