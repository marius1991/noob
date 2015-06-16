//package de.fh_muenster.noobApp;
//
//import java.security.acl.Owner;
//import java.util.ArrayList;
//import java.util.List;
//
//import de.fh_muenster.exceptions.BadConnectionException;
//import de.fh_muenster.noob.CategoryListResponse;
//import de.fh_muenster.noob.CityListResponse;
//import de.fh_muenster.noob.CommentTO;
//import de.fh_muenster.noob.LocationListResponse;
//import de.fh_muenster.noob.LocationTO;
//import de.fh_muenster.noob.NoobOnlineService;
//import de.fh_muenster.noob.RatingTO;
//import de.fh_muenster.noob.ReturnCodeResponse;
//import de.fh_muenster.noob.UserLoginResponse;
//import de.fh_muenster.noob.UserTO;
//
///**
// * Created by marius on 07.06.15.
// */
//public class NoobOnlineServiceMock implements NoobOnlineService {
//
//    private List<String> categoryList = new ArrayList<>();
//    private List<String> cityList = new ArrayList<>();
//
//    public NoobOnlineServiceMock () {
//        categoryList.add("Kneipe");
//        categoryList.add("Arzt");
//        categoryList.add("Supermarkt");
//        categoryList.add("Bar");
//        categoryList.add("Fastfood");
//        categoryList.add("Tankstelle");
//        cityList.add("Münster");
//        cityList.add("Osnabrück");
//        cityList.add("Bielefeld");
//        cityList.add("Dortmund");
//        cityList.add("Kassel");
//        cityList.add("Berlin");
//        cityList.add("Hamburg");
//    }
//
//    @Override
//    public ReturnCodeResponse register(String username, String email, String password, String passwordConfirmation) {
//        return null;
//    }
//
//    @Override
//    public UserLoginResponse login(String email, String password) {
//        return null;
//    }
//
//    @Override
//    public ReturnCodeResponse logout(int sessionId) {
//        return null;
//    }
//
//    @Override
//    public CategoryListResponse listCategories() throws BadConnectionException{
//        CategoryListResponse response = new CategoryListResponse();
//        response.setCategories(categoryList);
//        response.setReturnCode(100);
//        return response;
//    }
//
//    @Override
//    public CityListResponse listCities() {
//        CityListResponse response = new CityListResponse();
//        response.setCities(cityList);
//        response.setReturnCode(100);
//        return response;
//    }
//
//    @Override
//    public LocationListResponse listLocationsWithCategory(String category, String city) {
//        LocationListResponse response = new LocationListResponse();
//        LocationTO lo = new LocationTO();
//        LocationTO lo1 = new LocationTO();
//        LocationTO lo2 = new LocationTO();
//        UserTO owner = new UserTO();
//        List<LocationTO> locationTOList = new ArrayList<>();
//        if(category.equals("Bar") && city.equals("Münster")) {
//            lo.setName("Gorilla Bar");
//            lo1.setName("Rote Liebe");
//            lo2.setName("Blaues Haus");
//            lo.setDescription("Tolle Bar in der Altstadt von MS!");
//            lo1.setDescription("Noch ein tolle Bar in der Altstadt von MS!");
//            lo2.setDescription("und noch eine tolle Bar in der Altstadt von MS!");
//            lo.setCity("Münster");
//            lo.setPlz(48145);
//            lo.setStreet("Jüdefelder Str.");
//            lo.setNumber("15");
//            lo.setCategory("Bar");
//            owner.setName("Peter Lustig");
//            lo.setOwner(owner);
//            lo.setAverageRating(4.5);
//            lo.setId(1);
//            locationTOList.add(lo);
//            locationTOList.add(lo1);
//            locationTOList.add(lo2);
//            response.setReturnCode(100);
//            response.setLocations(locationTOList);
//            return response;
//        }
//        else {
//            return null;
//        }
//    }
//
//    @Override
//    public LocationListResponse listLocationsWithName(String name, String city) {
//        LocationListResponse response = new LocationListResponse();
//        LocationTO lo = new LocationTO();
//        LocationTO lo1 = new LocationTO();
//        LocationTO lo2 = new LocationTO();
//        UserTO owner = new UserTO();
//        List<LocationTO> locationTOList = new ArrayList<>();
//        if(city.equals("Münster") && name.equals("bar")) {
//            lo.setName("Gorilla Bar");
//            lo1.setName("Atellier Bar");
//            lo2.setName("Hafenbar");
//            lo.setDescription("Tolle Bar in der Altstadt von MS!");
//            lo1.setDescription("Noch ein tolle Bar in der Altstadt von MS!");
//            lo2.setDescription("und noch eine tolle Bar in der Altstadt von MS!");
//            lo.setCity("Münster");
//            lo.setPlz(48145);
//            lo.setStreet("Jüdefelder Str.");
//            lo.setNumber("15");
//            lo.setCategory("Bar");
//            owner.setName("Peter Lustig");
//            lo.setOwner(owner);
//            lo.setAverageRating(4.5);
//            lo.setId(1);
//            CommentTO comment = new CommentTO(1, "test", null, lo, owner);
//            List <CommentTO> list = new ArrayList<>();
//            list.add(comment);
//            lo.setComments(list);
//
//
//            locationTOList.add(lo);
//            locationTOList.add(lo1);
//            locationTOList.add(lo2);
//            response.setReturnCode(100);
//            response.setLocations(locationTOList);
//            return response;
//        }
//        else {
//            return null;
//        }
//    }
//
//    @Override
//    public LocationListResponse listAllLocations(String city) {
//        return null;
//    }
//
//    @Override
//    public ReturnCodeResponse giveRating(int sessionId, int locationId, int value) {
//        return null;
//    }
//
//    @Override
//    public ReturnCodeResponse commentOnLocation(int sessionId, int locationId, String text) {
//        return null;
//    }
//
//    @Override
//    public ReturnCodeResponse commentOnComment(int sessionId, int commentId, String text) {
//        return null;
//    }
//
//    @Override
//    public ReturnCodeResponse createLocation(int sessionId, String name, String category, String description, String street, String number, int plz, String city) {
//        return null;
//    }
//
//    @Override
//    public ReturnCodeResponse setLocationDetails(int sessionId, LocationTO newLocationDetails) {
//        return null;
//    }
//
//    @Override
//    public ReturnCodeResponse setUserDetails(int sessionId, UserTO newUser) {
//        return null;
//    }
//
//    @Override
//    public UserTO getUserDetails(int sessionId) {
//        return null;
//    }
//
//    @Override
//    public ReturnCodeResponse deleteUser(int sessionId) {
//        return null;
//    }
//}