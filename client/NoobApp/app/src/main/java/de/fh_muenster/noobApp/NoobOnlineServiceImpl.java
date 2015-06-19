package de.fh_muenster.noobApp;

import android.util.Log;

import org.kobjects.base64.Base64;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import de.fh_muenster.noob.*;

/**
 * @author marius,philipp,marco
 */
public class NoobOnlineServiceImpl implements NoobOnlineService {
    private static final String NAMESPACE = "http://noobservice.noob.de/";

    //private static final String URL = "http://10.0.2.2:8080/noob/NoobOnlineServiceBean";
    private static final String URL = "http://10.70.16.58:8080/noob/NoobOnlineServiceBean";

    private static final String TAG = NoobOnlineServiceImpl.class.getName();

    private int sessionId;






    /**
     * Diese Methode delegiert einen Methodenaufruf an den hinterlegten WebService.
     * @param methodName
     * @return
     */
    private SoapObject executeSoapAction(String methodName, Object... args) throws SoapFault {

        Object result = null;

	    /* Create a org.ksoap2.serialization.SoapObject object to build a SOAP request. Specify the namespace of the SOAP object and method
	     * name to be invoked in the SoapObject constructor.
	     */
        SoapObject request = new SoapObject(NAMESPACE, methodName);

	    /* The array of arguments is copied into properties of the SOAP request using the addProperty method. */
        for (int i=0; i<args.length; i++) {
            request.addProperty("arg" + i, args[i]);
        }

	    /* Next create a SOAP envelop. Use the SoapSerializationEnvelope class, which extends the SoapEnvelop class, with support for SOAP
	     * Serialization format, which represents the structure of a SOAP serialized message. The main advantage of SOAP serialization is portability.
	     * The constant SoapEnvelope.VER11 indicates SOAP Version 1.1, which is default for a JAX-WS webservice endpoint under JBoss.
	     */
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

	    /* Assign the SoapObject request object to the envelop as the outbound message for the SOAP method call. */
        envelope.setOutputSoapObject(request);

	    /* Create a org.ksoap2.transport.HttpTransportSE object that represents a J2SE based HttpTransport layer. HttpTransportSE extends
	     * the org.ksoap2.transport.Transport class, which encapsulates the serialization and deserialization of SOAP messages.
	     */
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
	        /* Make the soap call using the SOAP_ACTION and the soap envelop. */
            List<HeaderProperty> reqHeaders = null;

            @SuppressWarnings({"unused", "unchecked"})
            //List<HeaderProperty> respHeaders = androidHttpTransport.call(NAMESPACE + methodName, envelope, reqHeaders);
                    //fuehrt zu CXF-Fehler! neue Version ohne SOAP-Action funktioniert:
                    List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);

	        /* Get the web service response using the getResponse method of the SoapSerializationEnvelope object.
	         * The result has to be cast to SoapPrimitive, the class used to encapsulate primitive types, or to SoapObject.
	         */
            result = envelope.getResponse();

            if (result instanceof SoapFault) {
                throw (SoapFault) result;
            }
        }
        catch (SoapFault e) {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return (SoapObject) result;
    }

    /**
     * Diese Methode realistiert die Registrierung eines neuen Benutzers. Es wird eine SoapAction ausgeführt
     * und der Returncode zurückgegeben.
     * @param username
     * @param email
     * @param password
     * @param passwordConfirmation
     * @return
     */
    @Override
    public ReturnCodeResponse register(String username, String email, String password, String passwordConfirmation) {
        String METHOD_NAME = "register";
        SoapObject response;
        Log.d(TAG, "register:");
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        try {
            response = executeSoapAction(METHOD_NAME, username, email, password, passwordConfirmation);
            Log.d(TAG, response.getProperty("message").toString());
                returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
                returnCodeResponse.setMessage(response.getProperty("message").toString());
        }
        catch (SoapFault e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
        }
        return returnCodeResponse;
    }

    /**
     * Diese Methode realisiert das Anmelden eines Benutzers. Es wird eine SoapAction ausgeführt und der
     * Returncode + SessionId zurückgegeben.
     * @param email
     * @param password
     * @return
     */
    @Override
    public UserLoginResponse login(String email, String password) {
        String METHOD_NAME ="login";
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        Log.d(TAG, "login:");

        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, email, password);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
            userLoginResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode")));
            userLoginResponse.setMessage(response.getProperty("message").toString());
            userLoginResponse.setSessionId(Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId")));
            Log.d(TAG, "id: " + response.getPrimitivePropertySafelyAsString("sessionId"));

        }
        catch (SoapFault e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            userLoginResponse.setReturnCode(10);
        }
        return userLoginResponse;
    }

    /**
     * Diese Methode realisiert das Ausloggen eines Benutzers. Es wird eine SoapAction ausgeführt und
     * der Returncode zurückgegeben.
     * @param sessionId
     * @return
     */
    @Override
    public ReturnCodeResponse logout(int sessionId) {
        String METHOD_NAME = "logout";
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        Log.d(TAG, "Logout:");
        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
        }
        return  returnCodeResponse;
    }


    /**
     * Diese Methode realisiert das Abrufen der Kategorienliste. Es wird eine SoapAction ausgeführt und
     * die Kategorienliste zurückgegeben.
     * @return
     */
    @Override
    public CategoryListResponse listCategories() {
        String METHOD_NAME = "listCategories";
        CategoryListResponse categoryListResponse = new CategoryListResponse();
        Log.d(TAG, "List Categories:");

        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());

            List<String> categories = new ArrayList<>();
            for (int i=0; i<response.getPropertyCount(); i++) {
                PropertyInfo info = new PropertyInfo();
                response.getPropertyInfo(i, info);
                Object obj =info.getValue();
                if(obj!= null && info.name.equals("categories")) {
                    Log.d(TAG, response.getProperty(i).toString());
                    categories.add(response.getProperty(i).toString());
                }
            }
            categoryListResponse.setMessage(response.getPropertyAsString("message"));
            categoryListResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            categoryListResponse.setCategories(categories);
        }
        catch (SoapFault e) {
        }
        catch (NullPointerException e) {
            categoryListResponse.setReturnCode(10);
        }
        return categoryListResponse;
    }

    /**
     * Mit diese Methode können alle Städte vom Server abgerufen werden. Es wird eine SoapAction ausgeführt
     * und die Städteliste zrückgegeben.
     * @return
     */
    @Override
    public CityListResponse listCities() {
        String METHOD_NAME = "listCities";
        CityListResponse cityListResponse = new CityListResponse();
        Log.d(TAG, "List Cities");

        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());


            List<String> cities = new ArrayList<>();
            for (int i=0; i<response.getPropertyCount(); i++) {
                PropertyInfo info = new PropertyInfo();
                response.getPropertyInfo(i, info);
                Object obj = info.getValue();
                if(obj != null && info.name.equals("cities")) {
                    Log.d(TAG, response.getProperty(i).toString());
                    cities.add(response.getProperty(i).toString());
                }
            }
            cityListResponse.setMessage(response.getPropertyAsString("message"));
            cityListResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            cityListResponse.setCities(cities);

        }
        catch (SoapFault e) {
        }
        catch (NullPointerException e) {
            cityListResponse.setReturnCode(10);
        }
        return cityListResponse;

    }

    /**
     * Diese Methode realisiert den Abruf der Locations einer Kategorie. Es wird eine SoapAction gestartet
     * und die Locationliste zurückgegeben.
     * @param categoryparam
     * @param city
     * @return
     */
    @Override
    public LocationListResponse listLocationsWithCategory(String categoryparam, String city) {
        String METHOD_NAME = "listLocationsWithCategory";
        LocationListResponse locationListResponse = new LocationListResponse();
        Log.d(TAG, "List Location with Category:");

        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, categoryparam, city);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());


            List<LocationTO> locations = new ArrayList<>();
            for(int i=0; i<response.getPropertyCount(); i++) {
                PropertyInfo info = new PropertyInfo();
                response.getPropertyInfo(i, info);
                Object obj = info.getValue();
                if(obj != null && info.name.equals("locations")) {

                    SoapObject soapObject = (SoapObject) obj;
                    LocationTO locationTO = new LocationTO();
                    Log.d(TAG, "LOCATION: " + soapObject.toString());
                    locationTO.setName(soapObject.getProperty("name").toString());
                    locationTO.setAverageRating(Double.parseDouble(soapObject.getProperty("averageRating").toString()));
                    locationTO.setCategory(soapObject.getProperty("category").toString());
                    locationTO.setDescription(soapObject.getProperty("description").toString());
                    locationTO.setId(Integer.parseInt(soapObject.getProperty("id").toString()));
                    locationTO.setNumber(soapObject.getProperty("number").toString());
                    locationTO.setStreet(soapObject.getProperty("street").toString());
                    locationTO.setPlz(Integer.parseInt(soapObject.getProperty("plz").toString()));
                    locationTO.setCity(soapObject.getProperty("city").toString());
                    locationTO.setOwnerId(soapObject.getProperty("ownerId").toString());
                    locationTO.setOwnerName(soapObject.getProperty("ownerName").toString());
                    List<RatingTO> ratings = new ArrayList<>();
                    if (soapObject.hasProperty("ratings")) {
                        Log.d(TAG, "Has Ratings");
                        int propertyCount = soapObject.getPropertyCount();
                        for (int j=0; j<propertyCount; j++) {
                            PropertyInfo info1 = new PropertyInfo();
                            soapObject.getPropertyInfo(j, info1);
                            Object obj1 = info1.getValue();
                            if(obj1 != null && info1.name.equals("ratings")) {
                                SoapObject ratingsObject = (SoapObject) obj1;
                                Log.d(TAG, "RATINGS: " + ratingsObject.toString());
                                RatingTO ratingTO = new RatingTO();
                                ratingTO.setId(Integer.parseInt(ratingsObject.getProperty("id").toString()));
                                ratingTO.setLocationId(Integer.parseInt(ratingsObject.getProperty("locationId").toString()));
                                ratingTO.setOwnerId(ratingsObject.getProperty("ownerId").toString());
                                ratingTO.setValue(Integer.parseInt(ratingsObject.getProperty("value").toString()));
                                ratings.add(ratingTO);
                            }
                        }
                    }
                    List<CommentTO> comments = new ArrayList<>();
                    if(soapObject.hasProperty("comments")) {
                        Log.d(TAG, "Has Comments");
                        int propertyCount = soapObject.getPropertyCount();
                        for (int j=0; j<propertyCount; j++) {
                            PropertyInfo info1 = new PropertyInfo();
                            soapObject.getPropertyInfo(j, info1);
                            Object obj1 = info1.getValue();
                            if(obj1 != null && info1.name.equals("comments")) {
                                SoapObject commentsObject = (SoapObject) obj1;
                                Log.d(TAG, "COMMENTS: " + commentsObject.toString());
                                CommentTO commentTO = new CommentTO();
                                commentTO.setId(Integer.parseInt(commentsObject.getProperty("id").toString()));
                                commentTO.setOwnerId(commentsObject.getProperty("ownerId").toString());
                                commentTO.setLocationId(Integer.parseInt(commentsObject.getProperty("locationId").toString()));
                                commentTO.setText(commentsObject.getProperty("text").toString());
                                commentTO.setDate(commentsObject.getProperty("date").toString());
                                commentTO.setOwnerName(commentsObject.getProperty("ownerName").toString());
                                comments.add(commentTO);
                            }
                        }
                    }
                    locationTO.setRatings(ratings);
                    locationTO.setComments(comments);
                    locations.add(locationTO);
                }
                locationListResponse.setLocations(locations);
                locationListResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
                locationListResponse.setMessage(response.getProperty("message").toString());
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            locationListResponse.setReturnCode(10);
        }

        return locationListResponse;
    }

    /**
     * Diese Mehtode realisiert die Suche nach Locations. Es verden vom Server die Locations abgerufen,
     * welche dem angegeben Namen ähneln.
     * Es wir eine SoapAction ausgeführt und die Locationliste zurückgegeben.
     * @param name
     * @param city
     * @return
     */
    @Override
    public LocationListResponse listLocationsWithName(String name, String city) {
        String METHOD_NAME = "listLocationsWithName";
        LocationListResponse locationListResponse = new LocationListResponse();
        Log.d(TAG, "List Location with Name:");

        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, name, city);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
            List<LocationTO> locations = new ArrayList<>();
            for(int i=0; i<response.getPropertyCount(); i++) {
                PropertyInfo info = new PropertyInfo();
                response.getPropertyInfo(i, info);
                Object obj = info.getValue();
                if(obj != null && info.name.equals("locations")) {
                    SoapObject soapObject = (SoapObject) obj;
                    LocationTO locationTO = new LocationTO();
                    Log.d(TAG, "LOCATION: " + soapObject.toString());
                    locationTO.setName(soapObject.getProperty("name").toString());
                    locationTO.setAverageRating(Double.parseDouble(soapObject.getProperty("averageRating").toString()));
                    locationTO.setCategory(soapObject.getProperty("category").toString());
                    locationTO.setDescription(soapObject.getProperty("description").toString());
                    locationTO.setId(Integer.parseInt(soapObject.getProperty("id").toString()));
                    locationTO.setNumber(soapObject.getProperty("number").toString());
                    locationTO.setStreet(soapObject.getProperty("street").toString());
                    locationTO.setPlz(Integer.parseInt(soapObject.getProperty("plz").toString()));
                    locationTO.setCity(soapObject.getProperty("city").toString());
                    locationTO.setOwnerId(soapObject.getProperty("ownerId").toString());
                    locationTO.setOwnerName(soapObject.getProperty("ownerName").toString());
                    List<RatingTO> ratings = new ArrayList<>();
                    if (soapObject.hasProperty("ratings")) {
                        Log.d(TAG, "Has Ratings");
                        int propertyCount = soapObject.getPropertyCount();
                        for (int j=0; j<propertyCount; j++) {
                            PropertyInfo info1 = new PropertyInfo();
                            soapObject.getPropertyInfo(j, info1);
                            Object obj1 = info1.getValue();
                            if(obj1 != null && info1.name.equals("ratings")) {
                                SoapObject ratingsObject = (SoapObject) obj1;
                                Log.d(TAG, "RATINGS: " + ratingsObject.toString());
                                RatingTO ratingTO = new RatingTO();
                                ratingTO.setId(Integer.parseInt(ratingsObject.getProperty("id").toString()));
                                ratingTO.setLocationId(Integer.parseInt(ratingsObject.getProperty("locationId").toString()));
                                ratingTO.setOwnerId(ratingsObject.getProperty("ownerId").toString());
                                ratingTO.setValue(Integer.parseInt(ratingsObject.getProperty("value").toString()));
                                ratings.add(ratingTO);
                            }
                        }
                    }
                    List<CommentTO> comments = new ArrayList<>();
                    if(soapObject.hasProperty("comments")) {
                        Log.d(TAG, "Has Comments");
                        int propertyCount = soapObject.getPropertyCount();
                        for (int j=0; j<propertyCount; j++) {
                            PropertyInfo info1 = new PropertyInfo();
                            soapObject.getPropertyInfo(j, info1);
                            Object obj1 = info1.getValue();
                            if(obj1 != null && info1.name.equals("comments")) {
                                SoapObject commentsObject = (SoapObject) obj1;
                                Log.d(TAG, "COMMENTS: " + commentsObject.toString());
                                CommentTO commentTO = new CommentTO();
                                commentTO.setId(Integer.parseInt(commentsObject.getProperty("id").toString()));
                                commentTO.setOwnerId(commentsObject.getProperty("ownerId").toString());
                                commentTO.setLocationId(Integer.parseInt(commentsObject.getProperty("locationId").toString()));
                                commentTO.setText(commentsObject.getProperty("text").toString());
                                commentTO.setDate(commentsObject.getProperty("date").toString());
                                commentTO.setOwnerName(commentsObject.getProperty("ownerName").toString());
                                comments.add(commentTO);
                            }
                        }
                    }
                    locationTO.setComments(comments);
                    locationTO.setRatings(ratings);
                    locations.add(locationTO);
                }
                locationListResponse.setLocations(locations);
                locationListResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
                locationListResponse.setMessage(response.getProperty("message").toString());
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            locationListResponse.setReturnCode(10);
        }
        return locationListResponse;
    }

    /**
     * Diese Mehtode realisiert das Bewerten einer Location. Es wird eine SoapAction ausgeführt und
     * der Returncode zurückgeben.
     * @param sessionId
     * @param locationId
     * @param value
     * @return
     */
    @Override
    public ReturnCodeResponse giveRating(int sessionId, int locationId, int value) {
        String METHOD_NAME = "giveRating";
        SoapObject response;
        Log.d(TAG, "Give Rating:");

        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, locationId, value);
            Log.d(TAG, response.getProperty("message").toString());
            returnCodeResponse.setMessage(response.getProperty("message").toString());
            returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode")));
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
        }
        return  returnCodeResponse;
    }

    /**
     * Diese Methode realisiert das Kommentieren einer Location. Es wird eine SoapAction ausgeführt und
     * ein Returncode zurückgegeben.
     * @param sessionId
     * @param locationId
     * @param text
     * @return
     */
    @Override
    public ReturnCodeResponse commentOnLocation(int sessionId, int locationId, String text) {
        String METHOD_NAME = "commentOnLocation";
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        Log.d(TAG, "Comment on Location:");
        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, locationId, text);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
            returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            returnCodeResponse.setMessage(response.getProperty("message").toString());
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
        }
        return returnCodeResponse;
    }

    /**
     * Diese Methode realisiert das erstellen einer Location. Es wird eine SoapAction ausgeführt und
     * der Returncode zrückgegeben.
     * @param sessionId
     * @param name
     * @param category
     * @param description
     * @param street
     * @param number
     * @param plz
     * @param city
     * @param image
     * @return
     */
    @Override
    public ReturnCodeResponse createLocation(int sessionId, String name, String category, String description, String street, String number, int plz, String city, byte[] image) {
        String METHOD_NAME = "createLocation";
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        Log.d(TAG, "Create Location:");
        String bytestring = Base64.encode(image);
        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, name, category, description, street, number, plz, city, bytestring);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
            returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            returnCodeResponse.setMessage(response.getProperty("message").toString());
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
        }
        return returnCodeResponse;
    }

    /**
     * Diese Mehtode ralisiert das Abrufen einer Location mit Angabe einer LocationId. Es wird eine
     * SoapAction ausgeführt und die Location zurückgegeben.
     * @param locationId
     * @return
     */
    @Override
    public LocationTO getLocationDetails(int locationId) {
        String METHOD_NAME = "getLocationDetails";
        LocationTO locationTO = new LocationTO();
        Log.d(TAG, "Get Locationdetails:");
        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, locationId);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
            Log.d(TAG, "LOCATION: " + response.toString());
            locationTO.setName(response.getProperty("name").toString());
            locationTO.setAverageRating(Double.parseDouble(response.getProperty("averageRating").toString()));
            locationTO.setCategory(response.getProperty("category").toString());
            locationTO.setDescription(response.getProperty("description").toString());
            locationTO.setId(Integer.parseInt(response.getProperty("id").toString()));
            locationTO.setNumber(response.getProperty("number").toString());
            locationTO.setStreet(response.getProperty("street").toString());
            locationTO.setPlz(Integer.parseInt(response.getProperty("plz").toString()));
            locationTO.setCity(response.getProperty("city").toString());
            locationTO.setOwnerId(response.getProperty("ownerId").toString());
            locationTO.setOwnerName(response.getProperty("ownerName").toString());
            List<byte[]> images = new ArrayList<>();
            try {
                if (response.hasProperty("images")) {
                    Log.d(TAG, "HAS IMAGES");
                    int propertyCount = response.getPropertyCount();
                    for (int j = 0; j < propertyCount; j++) {
                        PropertyInfo info1 = new PropertyInfo();
                        response.getPropertyInfo(j, info1);
                        Object obj1 = info1.getValue();
                        if (obj1 != null && info1.name.equals("images")) {
                            SoapPrimitive imageObject = (SoapPrimitive) obj1;
                            Log.d(TAG, "IMAGES: " + imageObject.toString());
                            byte[] imagebytes = Base64.decode(imageObject.toString());
                            images.add(imagebytes);
                        }
                    }
                }
            }
            catch (java.lang.RuntimeException e) {
                Log.d(TAG, e.getMessage());
                if (e.getMessage().equals("illegal property: image")) {
                    images = null;
                }
                else {
                    throw new java.lang.RuntimeException();
                }
            }
            List<RatingTO> ratings = new ArrayList<>();
            if (response.hasProperty("ratings")) {
                Log.d(TAG, "Has Ratings");
                int propertyCount = response.getPropertyCount();
                for (int j=0; j<propertyCount; j++) {
                    PropertyInfo info1 = new PropertyInfo();
                    response.getPropertyInfo(j, info1);
                    Object obj1 = info1.getValue();
                    if(obj1 != null && info1.name.equals("ratings")) {
                        SoapObject ratingsObject = (SoapObject) obj1;
                        Log.d(TAG, "RATINGS: " + ratingsObject.toString());
                        RatingTO ratingTO = new RatingTO();
                        ratingTO.setId(Integer.parseInt(ratingsObject.getProperty("id").toString()));
                        ratingTO.setLocationId(Integer.parseInt(ratingsObject.getProperty("locationId").toString()));
                        ratingTO.setOwnerId(ratingsObject.getProperty("ownerId").toString());
                        ratingTO.setValue(Integer.parseInt(ratingsObject.getProperty("value").toString()));
                        ratings.add(ratingTO);
                    }
                }
            }
            List<CommentTO> comments = new ArrayList<>();
            if(response.hasProperty("comments")) {
                Log.d(TAG, "Has Comments");
                int propertyCount = response.getPropertyCount();
                for (int j=0; j<propertyCount; j++) {
                    PropertyInfo info1 = new PropertyInfo();
                    response.getPropertyInfo(j, info1);
                    Object obj1 = info1.getValue();
                    if(obj1 != null && info1.name.equals("comments")) {
                        SoapObject commentsObject = (SoapObject) obj1;
                        Log.d(TAG, "COMMENTS: " + commentsObject.toString());
                        CommentTO commentTO = new CommentTO();
                        commentTO.setId(Integer.parseInt(commentsObject.getProperty("id").toString()));
                        commentTO.setOwnerId(commentsObject.getProperty("ownerId").toString());
                        commentTO.setLocationId(Integer.parseInt(commentsObject.getProperty("locationId").toString()));
                        commentTO.setText(commentsObject.getProperty("text").toString());
                        commentTO.setDate(commentsObject.getProperty("date").toString());
                        commentTO.setOwnerName(commentsObject.getProperty("ownerName").toString());
                        comments.add(commentTO);
                    }
                }
            }
            locationTO.setImages(images);
            locationTO.setComments(comments);
            locationTO.setRatings(ratings);
            locationTO.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            locationTO.setMessage(response.getProperty("message").toString());
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            locationTO.setReturnCode(10);
        }
        return locationTO;
    }

    /**
     * Diese Methode realisiert das Ändern einer Location mit Angabe einer LocationId.
     * Es wird eine SoapAction ausgeführt und ein Returncode zurückgegeben.
     * @param sessionId
     * @param locationId
     * @param name
     * @param category
     * @param description
     * @param street
     * @param number
     * @param plz
     * @param city
     * @param image
     * @return
     */
    @Override
    public ReturnCodeResponse setLocationDetails(int sessionId, int locationId, String name, String category, String description, String street, String number, int plz, String city, byte[] image) {
        String METHOD_NAME = "setLocationDetails";
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        Log.d(TAG, "Set Locationdetails:");
        String bytestring = Base64.encode(image);
        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, locationId, name, category, description, street, number, plz, city, bytestring);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
            returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            returnCodeResponse.setMessage(response.getProperty("message").toString());
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
            returnCodeResponse.setMessage("Fehler");
        }
        return returnCodeResponse;
    }

    /**
     *
     * @param sessionId
     * @param name
     * @param password
     * @param passwordWdh
     * @return
     */
    @Override
    public ReturnCodeResponse setUserDetails(int sessionId, String name, String password, String passwordWdh) {
        String METHOD_NAME = "setUserDetails";
        Log.d(TAG, "Set Userdetails:");
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        SoapObject response;
        try{
            response = executeSoapAction(METHOD_NAME, sessionId, name,password, passwordWdh);
            returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            returnCodeResponse.setMessage(response.getProperty("message").toString());
        }
        catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
        }
        return returnCodeResponse;
    }

    /**
     * Diese Methode realisert das Verändern von Benutzernamen mit Angabe der SessionId.
     * Es wir eine SoapAction ausgeführt und der Benutzer zurückgegeben.
     * @param sessionId
     * @return
     */
    @Override
    public UserTO getUserDetails(int sessionId) {
        String METHOD_NAME = "getUserDetails";
        UserTO userTO = new UserTO();
        Log.d(TAG, "Get Userdetails:");
        SoapObject response;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            userTO.setName(response.getProperty("name").toString());
            Log.d(TAG, response.getProperty("name").toString() );
            userTO.setEmail(response.getProperty("email").toString());
            userTO.setPassword(response.getProperty("password").toString());
            userTO.setReturnCode(Integer.parseInt(response.getProperty("returnCode").toString()));
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            userTO.setReturnCode(10);
        }
        return userTO;
    }

    /**
     * Diese Methode realisiert das Löschen eines Beutzer mit Angabe der SessionId.
     * Es wird eine SoapAction ausgeführt und der Returncode zurückgegeben.
     * @param sessionId
     * @param password
     * @return
     */
    @Override
    public ReturnCodeResponse deleteUser(int sessionId, String password) {
        String METHOD_NAME = "deleteUser";
        Log.d(TAG, "Delete User:");
        SoapObject response;
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        try{
            response = executeSoapAction(METHOD_NAME, sessionId, password);
            returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            returnCodeResponse.setMessage(response.getProperty("message").toString());
        }
        catch(SoapFault soapFault){
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
        }
        return returnCodeResponse;

    }

    @Override
    public ReturnCodeResponse addImageToLocation(int sessionId, int locationId, byte[] image) {
        String METHOD_NAME = "addImageToLocation";
        Log.d(TAG, "Add Image to Location:");
        SoapObject response;
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        String bytestring = Base64.encode(image);
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, locationId, bytestring);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
            returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
            returnCodeResponse.setMessage(response.getProperty("message").toString());
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        catch (NullPointerException e) {
            returnCodeResponse.setReturnCode(10);
        }
        return returnCodeResponse;
    }
}