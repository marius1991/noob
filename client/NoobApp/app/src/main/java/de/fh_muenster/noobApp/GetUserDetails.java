package de.fh_muenster.noobApp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.UserTO;

/**
 * Created by StillforYou on 15.06.2015.
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

        @Override
        protected void onPostExecute(UserTO userTO) {
            if (userTO.getReturnCode() == 0) {
                myApp.setUser(userTO);
            }
        }
    }

