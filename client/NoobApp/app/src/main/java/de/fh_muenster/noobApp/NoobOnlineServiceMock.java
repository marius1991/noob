package de.fh_muenster.noobApp;

import android.util.Log;

import org.kobjects.base64.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
    TestDB testDB = TestDB.getInstance();
    private static final String TAG = NoobOnlineServiceMock.class.getName();

    @Override
    public ReturnCodeResponse register(String username, String email, String password, String passwordConfirmation) {
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
        MessageDigest passwordHash = null;
        try {
            passwordHash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        passwordHash.update("test".getBytes());
        String passwordHex= new BigInteger(1,passwordHash.digest()).toString(16);
        if(email.equals("test@test.de") && password.equals(passwordHex)) {
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
    public CategoryListResponse listCategories() {
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
            List<LocationTO> locations = testDB.getLocations();
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
            List<LocationTO> locations = testDB.getLocations();
            locationListResponse.setLocations(locations);
            return locationListResponse;
        }
        else {
            return locationListResponse;
        }
    }

    @Override
    public ReturnCodeResponse giveRating(int sessionId, int locationId, int value) {
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        RatingTO ratingTO = new RatingTO();
        ratingTO.setOwnerId("test@test.de");
        ratingTO.setId(2);
        ratingTO.setValue(value);
        ratingTO.setLocationId(locationId);
        List<LocationTO> locations = testDB.getLocations();
        List<LocationTO> locationTOs = locations.subList(0, 1);
        LocationTO locationTO = locationTOs.get(0);
        List<RatingTO> ratings = locationTO.getRatings();
        ratings.add(ratingTO);
        locationTO.setRatings(ratings);
        locations.set(0, locationTO);
        testDB.setLocations(locations);
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
        List<LocationTO> locations = testDB.getLocations();
        List<LocationTO> locationTOs = locations.subList(0, 1);
        LocationTO locationTO = locationTOs.get(0);
        List<CommentTO> commentTOs = locationTO.getComments();
        commentTOs.add(commentTO);
        locationTO.setComments(commentTOs);
        locations.set(0, locationTO);
        testDB.setLocations(locations);
        returnCodeResponse.setReturnCode(0);
        returnCodeResponse.setMessage("Erfolgreich kommentiert");
        return returnCodeResponse;
    }

    @Override
    public ReturnCodeResponse createLocation(int sessionId, String name, String category, String description, String street, String number, int plz, String city, byte[] image) {
        return null;
    }


    @Override
    public ReturnCodeResponse setLocationDetails(int sessionId, int locationId, String name, String category, String description, String street, String number, int plz, String city) {
        return null;
    }

    @Override
    public LocationTO getLocationDetails(int locationId) {
        List<LocationTO> locations = testDB.getLocations();
        LocationTO locationTO = locations.get(0);
        locationTO.setReturnCode(0);
        locationTO.setMessage("Erfolgreich abgerufen");
        if(locationTO.getImages() == null) {
            List<byte[]> images = new ArrayList<>();
            locationTO.setImages(images);
        }
        return locationTO;
    }

    @Override
    public ReturnCodeResponse setUserDetails(int sessionId, String name, String email, String password) {
        return null;
    }

    @Override
    public UserTO getUserDetails(int sessionId) {
        UserTO userTO = new UserTO();
        userTO.setName("Tester");
        userTO.setMessage("Erfolgreich abgerufen");
        userTO.setReturnCode(0);
        userTO.setEmail("test@test.de");
        userTO.setId(1);
        MessageDigest passwordHash = null;
        try {
            passwordHash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        passwordHash.update("test".getBytes());
        String passwordHex= new BigInteger(1,passwordHash.digest()).toString(16);
        userTO.setPassword(passwordHex);
        List<LocationTO> locations = testDB.getLocations();
        List<CommentTO> comments = testDB.getComments();
        List<RatingTO> ratings = testDB.getRatings();
        userTO.setLocations(locations);
        userTO.setComments(comments);
        userTO.setRatings(ratings);
        return userTO;
    }

    @Override
    public ReturnCodeResponse deleteUser(int sessionId, String password) {
        return null;
    }

    @Override
    public ReturnCodeResponse addImageToLocation(int sessionId, int locationId, byte[] image) {
        List<LocationTO> locations = testDB.getLocations();
        LocationTO locationTO = locations.get(0);
        List<byte[]> images = new ArrayList<>();
        if(locationTO.getImages() != null) {
            images = locationTO.getImages();
        }
        images.add(image);
        locationTO.setImages(images);
        locations.set(0, locationTO);
        testDB.setLocations(locations);
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        returnCodeResponse.setMessage("Upload erfolgreich");
        returnCodeResponse.setReturnCode(0);
        Log.d(TAG, Base64.encode(image));
        return returnCodeResponse;
    }

    @Override
    public ReturnCodeResponse deleteLocation(int sessionId, int locationId) {
        return null;
    }

}
