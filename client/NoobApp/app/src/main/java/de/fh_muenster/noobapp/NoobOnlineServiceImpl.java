package de.fh_muenster.noobapp;

import android.preference.PreferenceActivity;
import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

import dto.CategoryListResponse;
import dto.CommentTO;
import dto.LocationListResponse;
import dto.LocationTO;
import dto.ReturncodeResponse;
import dto.UserLoginResponse;
import dto.UserTO;

/**
 * Created by philipp on 02.06.15.
 */
public class NoobOnlineServiceImpl implements NoobOnlineService {
    private static final String NAMESPACE = "http://noobservice.noob.de/";

    private static final String URL = "http://10.0.2.2:8080/noob/NoobOnlineServiceBean";

    private static final String TAG = NoobOnlineServiceImpl.class.getName();

    private int sessionId;


    @Override
    public void register(String username, String email, String password, String passwordConfirmation) {
        String METHOD_NAME = "register";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, username, email, password, passwordConfirmation);
            Log.d(TAG, response.getProperty("message").toString());
            if(Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode")) != 0) {
                //TODO InvalidRegisterException werfen!!!
            }

        } catch (SoapFault e) {

        }
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

    private SoapObject executeSoapAction(String methodName, Object... args) throws SoapFault {
        Object result = null;

        SoapObject request = new SoapObject(NAMESPACE, methodName);

        for (int i = 0; i<args.length; i++) {
            request.addProperty("arg" + i, args[i]);
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            List<HeaderProperty> reqHeaders = null;

            List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);

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

}
