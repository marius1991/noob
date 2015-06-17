package de.fh_muenster.noobApp;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.exceptions.BadConnectionException;
import de.fh_muenster.exceptions.InvalidRegisterException;
import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.CityListResponse;
import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.RatingTO;
import de.fh_muenster.noob.ReturnCodeResponse;
import de.fh_muenster.noob.UserLoginResponse;
import de.fh_muenster.noob.UserTO;

/**
 * Created by marius on 07.06.15.
 */
public class NoobOnlineServiceMock implements NoobOnlineService {
    private List<LocationTO> locations = new ArrayList<>();

    public NoobOnlineServiceMock() {
        if(locations.isEmpty()) {
            LocationTO locationTO = new LocationTO();
            locationTO.setName("Blaues Haus");
            locationTO.setCategory("Kneipe");
            locationTO.setAverageRating(3.0);
            locationTO.setCity("Münster");
            locationTO.setPlz(48143);
            locationTO.setStreet("Kreuzstraße");
            locationTO.setNumber("13");
            locationTO.setOwnerId("test@test.de");
            locationTO.setDescription("Die verwinkelte Kult-Kneipe mit Kunsttapeten, Live-Bühne und westfälischer Kost war einst ein Hippie-Treff.");
            locationTO.setId(1);
            CommentTO commentTO = new CommentTO();
            List<CommentTO> comments = new ArrayList<>();
            commentTO.setDate("2015-01-01 00:00");
            commentTO.setId(1);
            commentTO.setOwnerId("test@test.de");
            commentTO.setLocationId(1);
            commentTO.setText("Tolle Kneipe");
            comments.add(commentTO);
            locationTO.setComments(comments);
            RatingTO ratingTO = new RatingTO();
            List<RatingTO> ratings = new ArrayList<>();
            ratingTO.setOwnerId("test@test.de");
            ratingTO.setId(1);
            ratingTO.setValue(3);
            ratingTO.setLocationId(1);
            ratings.add(ratingTO);
            locationTO.setRatings(ratings);
            locations.add(locationTO);
        }
    }

    @Override
    public ReturnCodeResponse register(String username, String email, String password, String passwordConfirmation) throws InvalidRegisterException {
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        if(password.equals(passwordConfirmation)) {
            returnCodeResponse.setReturnCode(0);
            returnCodeResponse.setMessage("Erfolgreich registriert");
            return returnCodeResponse;
        }
        else {
            returnCodeResponse.setReturnCode(1);
            returnCodeResponse.setMessage("Registrierung fehlgeschlagen");
            return returnCodeResponse;
        }
    }

    @Override
    public UserLoginResponse login(String email, String password) {
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        if(email.equals("test@test.de") && password.equals("test")) {
            userLoginResponse.setReturnCode(0);
            userLoginResponse.setMessage("Erfolgreich angemeldet");
            userLoginResponse.setSessionId(1);
            userLoginResponse.setUserId(1);
            return userLoginResponse;
        }
        else {
            userLoginResponse.setReturnCode(1);
            userLoginResponse.setMessage("Anmeldung fehlgeschlagen");
            return userLoginResponse;
        }
    }

    @Override
    public ReturnCodeResponse logout(int sessionId) {
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        returnCodeResponse.setReturnCode(0);
        returnCodeResponse.setMessage("Erfolgreich ausgeloggt");
        return returnCodeResponse;
    }

    @Override
    public CategoryListResponse listCategories() throws BadConnectionException {
        CategoryListResponse categoryListResponse = new CategoryListResponse();
        categoryListResponse.setReturnCode(0);
        categoryListResponse.setMessage("Kategorien erfolgreich abgerufen");
        List<String> categories = new ArrayList<>();
        categories.add("Kneipe");
        categoryListResponse.setCategories(categories);
        return categoryListResponse;
    }

    @Override
    public CityListResponse listCities() {
        CityListResponse cityListResponse = new CityListResponse();
        cityListResponse.setReturnCode(0);
        cityListResponse.setMessage("Städte erfolgreich abgerufen");
        List<String> cities = new ArrayList<>();
        cities.add("Münster");
        cityListResponse.setCities(cities);
        return cityListResponse;
    }

    @Override
    public LocationListResponse listLocationsWithCategory(String category, String city) {
        LocationListResponse locationListResponse = new LocationListResponse();
        locationListResponse.setReturnCode(0);
        locationListResponse.setMessage("Locations erfolgreich abgerufen");
        if(city.equals("Münster") && category.equals("Kneipe")) {
            locationListResponse.setLocations(locations);
            return locationListResponse;
        }
        else {
            return locationListResponse;
        }
    }

    @Override
    public LocationListResponse listLocationsWithName(String name, String city) {
        LocationListResponse locationListResponse = new LocationListResponse();
        locationListResponse.setReturnCode(0);
        locationListResponse.setMessage("Locations erfolgreich abgerufen");
        if(city.equals("Münster") && name.equals("Blaues Haus")) {
            locationListResponse.setLocations(locations);
            return locationListResponse;
        }
        else {
            return locationListResponse;
        }
    }

    @Override
    public LocationListResponse listAllLocations(String city) {
        return null;
    }

    @Override
    public ReturnCodeResponse giveRating(int sessionId, int locationId, int value) {
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        RatingTO ratingTO = new RatingTO();
        ratingTO.setOwnerId("test@test.de");
        ratingTO.setId(2);
        ratingTO.setValue(value);
        ratingTO.setLocationId(locationId);
        List<LocationTO> locationTOs = locations;
        LocationTO locationTO = locationTOs.get(0);
        List<RatingTO> ratings = locationTO.getRatings();
        ratings.add(ratingTO);
        locationTO.setRatings(ratings);
        locationTOs.add(locationTO);
        locations = locationTOs;
        returnCodeResponse.setReturnCode(0);
        returnCodeResponse.setMessage("Erfolgreich bewertet");
        return returnCodeResponse;
    }

    @Override
    public ReturnCodeResponse commentOnLocation(int sessionId, int locationId, String text) {
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        CommentTO commentTO = new CommentTO();
        commentTO.setDate("2015-01-01 00:00");
        commentTO.setId(2);
        commentTO.setOwnerId("test@test.de");
        commentTO.setLocationId(locationId);
        commentTO.setText(text);
        locations.get(0).getComments().add(commentTO);
        returnCodeResponse.setReturnCode(0);
        returnCodeResponse.setMessage("Erfolgreich kommentiert");
        return returnCodeResponse;
    }

    @Override
    public ReturnCodeResponse commentOnComment(int sessionId, int commentId, String text) {
        return null;
    }

    @Override
    public ReturnCodeResponse createLocation(int sessionId, String name, String category, String description, String street, String number, int plz, String city) {
        return null;
    }

    @Override
    public ReturnCodeResponse setLocationDetails(int sessionId, LocationTO newLocationDetails) {
        return null;
    }

    @Override
    public LocationTO getLocationDetails(int locationId) {
        LocationTO locationTO = locations.get(0);
        locationTO.setReturnCode(0);
        locationTO.setMessage("Erfolgreich abgerufen");
        return locationTO;
    }

    @Override
    public ReturnCodeResponse setUserDetails(int sessionId, String name, String email, String password) {
        return null;
    }

    @Override
    public UserTO getUserDetails(int sessionId) {
        return null;
    }

    @Override
    public ReturnCodeResponse deleteUser(int sessionId) {
        return null;
    }
}
