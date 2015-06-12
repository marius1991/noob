package de.fh_muenster.noobApp;

import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.exceptions.BadConnectionException;
import de.fh_muenster.exceptions.InvalidRegisterException;
import de.fh_muenster.noob.*;

/**
 * @author marius,philipp
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


    @Override
    public ReturnCodeResponse register(String username, String email, String password, String passwordConfirmation) throws InvalidRegisterException{
        String METHOD_NAME = "register";
        SoapObject response = null;
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
        return returnCodeResponse;
    }


    @Override
    public UserLoginResponse login(String email, String password) {
        String METHOD_NAME ="login";
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        Log.d(TAG, "login:");

        SoapObject response = null;
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

        }
        return userLoginResponse;
    }

    @Override
    public ReturnCodeResponse logout(int sessionId) {
        return null;
    }

    @Override
    public CategoryListResponse listCategories() throws BadConnectionException {
        String METHOD_NAME = "listCategories";
        CategoryListResponse categoryListResponse = new CategoryListResponse();
        Log.d(TAG, "List Locations:");

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
        return categoryListResponse;
    }

    @Override
    public CityListResponse listCities() {
        String METHOD_NAME = "listCities";
        CityListResponse cityListResponse = new CityListResponse();
        Log.d(TAG, "List Cities");

        SoapObject response = null;
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
        return cityListResponse;

    }

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
                    if(soapObject.hasProperty("city")) {
                        Log.d(TAG, "Has Cities");
                    }
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
                    if(soapObject.hasProperty("comments")) {
                        Log.d(TAG, "Has Comments");
                    }
                    locationTO.setRatings(ratings);
//                    Log.d(TAG, "NACHHER: " + ratings.get(0).getOwnerId());
//                    Log.d(TAG, "NACHHER: " + ratings.get(1).getOwnerId());
                    locations.add(locationTO);
                }
                locationListResponse.setLocations(locations);
                locationListResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
                locationListResponse.setMessage(response.getProperty("message").toString());
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }

        return locationListResponse;
    }

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
                    if (soapObject.hasProperty("city")) {
                        Log.d(TAG, "Has Cities");
                    }
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
                    locationTO.setRatings(ratings);
//                    Log.d(TAG, "NACHHER: " + ratings.get(0).getOwnerId());
//                    Log.d(TAG, "NACHHER: " + ratings.get(1).getOwnerId());
                    locations.add(locationTO);
                }
                locationListResponse.setLocations(locations);
                locationListResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
                locationListResponse.setMessage(response.getProperty("message").toString());
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }

        return locationListResponse;
    }

    @Override
    public LocationListResponse listAllLocations(String city) {
        return null;
    }

    @Override
    public ReturnCodeResponse giveRating(int sessionId, int locationId, int value) {
        String METHOD_NAME = "giveRating";
        SoapObject response = null;
        Log.d(TAG, "Give Rating:");

        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, locationId, value);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        Log.d(TAG, response.getProperty("message").toString());
        returnCodeResponse.setMessage(response.getProperty("message").toString());
        returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode")));
        return  returnCodeResponse;
    }

    @Override
    public ReturnCodeResponse commentOnLocation(int sessionId, int locationId, String text) {
        String METHOD_NAME = "commentOnLocation";
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        Log.d(TAG, "commentOnLocation:");

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
        return returnCodeResponse;
    }

    @Override
    public ReturnCodeResponse commentOnComment(int sessionId, int commentId, String text) {
        return null;
    }

    @Override
    public ReturnCodeResponse createLocation(int sessionId, String name, String category, String description, String street, String number, int plz, String city) {
        String METHOD_NAME = "createLocation";
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        Log.d(TAG, "List Location with Name:");

        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, name, category, description, street, number, plz, city);
            Log.d(TAG, response.getProperty("message").toString());
            Log.d(TAG, response.getProperty("returnCode").toString());
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        returnCodeResponse.setReturnCode(Integer.parseInt(response.getPrimitivePropertyAsString("returnCode")));
        returnCodeResponse.setMessage(response.getProperty("message").toString());
        return returnCodeResponse;
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
        String METHOD_NAME = "getUserDetails";
        ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
        Log.d(TAG, "Get Userdetails:");

        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        Log.d(TAG, response.getProperty("message").toString());
        Log.d(TAG, response.getProperty("returnCode").toString());
        return null;
    }

    @Override
    public ReturnCodeResponse deleteUser(int sessionId) {
        return null;
    }
}