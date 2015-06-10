package de.fh_muenster.noobApp;

import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.ReturncodeResponse;
import de.fh_muenster.noob.UserLoginResponse;
import de.fh_muenster.noob.UserTO;

/**
 * Created by marius on 07.06.15.
 */
public class NoobOnlineServiceMock implements NoobOnlineService {

    @Override
    public void register(String username, String email, String password, String passwordConfirmation) {

    }

    @Override
    public UserLoginResponse login(String email, String password) {
        return null;
    }

    @Override
    public ReturncodeResponse logout() {
        return null;
    }

    @Override
    public ReturncodeResponse createLocation(String name, String category, String description, String street, String number, String plz, String city, String coordinates, UserTO owner) {
        return null;
    }

    @Override
    public CategoryListResponse listCategories() {
        return null;
    }

    @Override
    public LocationListResponse listLocationWithCategory(String category, String city) {
        return null;
    }

    @Override
    public LocationListResponse listAllLocations(String city) {
        return null;
    }

    @Override
    public LocationListResponse listLocationsWithName(String name, String city) {
        return null;
    }

    @Override
    public ReturncodeResponse commentOnLocation(UserTO user, LocationTO location, String text) {
        return null;
    }

    @Override
    public ReturncodeResponse commentOnComment(UserTO user, CommentTO comment, String text) {
        return null;
    }

    @Override
    public ReturncodeResponse giveRating(UserTO user, LocationTO location, int value) {
        return null;
    }

    @Override
    public LocationTO getLocationDetails(LocationTO location) {
        return null;
    }

    @Override
    public ReturncodeResponse setLocationDetails(LocationTO location) {
        return null;
    }

    @Override
    public UserTO getUserDetails(UserTO user) {
        return null;
    }

    @Override
    public ReturncodeResponse setUserDetails(UserTO user) {
        return null;
    }

    @Override
    public ReturncodeResponse deleteUser(UserTO user) {
        return null;
    }
}
