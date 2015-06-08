package de.fh_muenster.noobApp;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.CityListResponse;
import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.ReturnCodeResponse;
import de.fh_muenster.noob.UserLoginResponse;
import de.fh_muenster.noob.UserTO;

/**
 * Created by marius on 07.06.15.
 */
public class NoobOnlineServiceMock implements NoobOnlineService {

    private List<String> categoryList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();

    public NoobOnlineServiceMock () {
        categoryList.add("Kneipe");
        categoryList.add("Arzt");
        categoryList.add("Supermarkt");
        categoryList.add("Bar");
        categoryList.add("Fastfood");
        categoryList.add("Tankstelle");
        cityList.add("Münster");
        cityList.add("Osnabrück");
        cityList.add("Bielefeld");
        cityList.add("Dortmund");
        cityList.add("Kassel");
        cityList.add("Berlin");
        cityList.add("Hamburg");
    }

    @Override
    public ReturnCodeResponse register(String username, String email, String password, String passwordConfirmation) {
        return null;
    }

    @Override
    public UserLoginResponse login(String email, String password) {
        return null;
    }

    @Override
    public ReturnCodeResponse logout(int sessionId) {
        return null;
    }

    @Override
    public CategoryListResponse listCategories() {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categoryList);
        response.setReturnCode(100);
        return response;
    }

    @Override
    public CityListResponse listCities() {
        CityListResponse response = new CityListResponse();
        response.setCities(cityList);
        response.setReturnCode(100);
        return response;
    }

    @Override
    public LocationListResponse listLocationsWithCategory(String category, String city) {
        LocationListResponse response = new LocationListResponse();
        LocationTO lo = new LocationTO();
        LocationTO lo1 = new LocationTO();
        LocationTO lo2 = new LocationTO();
        List<LocationTO> locationTOList = new ArrayList<>();
        if(category.equals("Bar") && city.equals("Münster")) {
            lo.setName("Gorilla Bar");
            lo1.setName("Rote Liebe");
            lo2.setName("Blaues Haus");
            locationTOList.add(lo);
            locationTOList.add(lo1);
            locationTOList.add(lo2);
            response.setReturnCode(100);
            response.setLocations(locationTOList);
            return response;
        }
        else {
            return null;
        }
    }

    @Override
    public LocationListResponse listLocationsWithName(String name, String city) {
        return null;
    }

    @Override
    public LocationListResponse listAllLocations(String city) {
        return null;
    }

    @Override
    public ReturnCodeResponse giveRating(int sessionId, int locationId, int value) {
        return null;
    }

    @Override
    public ReturnCodeResponse commentOnLocation(int sessionId, int locationId, String text) {
        return null;
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
    public ReturnCodeResponse setUserDetails(int sessionId, UserTO newUser) {
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