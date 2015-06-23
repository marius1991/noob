package de.fh_muenster.noobApp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by marco
 * Die Klasse dient zur Validierung und Hashing des Passwortes
 * @author marco
 */
public class Password {
    /**
     * Diese Methode hasht den Passwort-String mit SHA-256
     * @param password
     */
    public String hashPasswort(String password) {
        try {
            String passwordnew="";
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            passwordnew= new BigInteger(1,messageDigest.digest()).toString(16);
            return passwordnew;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Diese Methode �berpr�ft ob die Passw�rter �bereinstimmen
     * @param password
     * @param passwordwdh
     */
    public boolean validPasswordWdh(String password, String passwordwdh){
        if(password.equals(passwordwdh)){
            return true;
        }
        return false;
    }

    /**
     * Diese Methode �berpr�ft ob das Passwort lang genug ist
     * @param password
     */
    public boolean validPassword(String password){
        if(password!=null&&password.length()>=8){
            return true;
        }
        return false;
    }
}
