package de.fh_muenster.noobApp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.UserTO;

/**
 * Created by marco
 * Dieser AsyncTask holt die akutellen "User-Werte" vom Server
 * und speichert diese in die Applikation Klasse
 * @author marco
 */
public class GetUserDetails extends AsyncTask<String, String, UserTO> {
        private Context context;
        private String message;
        private NoobApplication myApp;
        private int sessionId;

        public GetUserDetails(Context context,NoobApplication myApp) {
            this.myApp=myApp;
            this.context = context;
        }

        /**
         * Diese Methode, f�hrt einen Thread holt sich  ein UserTO Objekt
         * @param params
         */
        @Override
        protected UserTO doInBackground(String... params) {
            NoobOnlineService onlineService;
            if(myApp.isTestmode()) {
                onlineService = new NoobOnlineServiceMock();
            }
            else {
                onlineService  = new NoobOnlineServiceImpl();
            }
            UserTO userTO;
            sessionId = myApp.getSessionId();
            userTO = onlineService.getUserDetails(sessionId);
            return userTO;
        }
        /**
         * Diese Methode wird ausgef�hrt nachdem doInBackground durchgelaufen ist.�berpr�ft den returnCode vom Server,
         * ob alles geklappt hat
         * @param userTO
         */
        @Override
        protected void onPostExecute(UserTO userTO) {
            if (userTO.getReturnCode() == 0) {
                myApp.setUser(userTO);
            }
        }
    }

