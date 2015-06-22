package de.fh_muenster.noobApp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Dieser AsyncTask realisiert das Abmelden.
 * Er wurde in eine eigene Klasse ausgelagert, damit er von allen Activites aus aufgerufen werden kann.
 * @author marius
 */
public class LogoutTask extends AsyncTask <Integer, String, ReturnCodeResponse> {
    private static final String TAG = LogoutTask.class.getName();
    private Context context;
    private NoobApplication myApp;

    public LogoutTask (Context context, NoobApplication myApp) {
        this.context = context;
        this.myApp = myApp;
    }

    /**
     * Es wird ein Thread gestartet, in dem der Logout-Befehl an den Server gesendet wird.
     * @param params
     * @return ReturnCodeRespone (Enthält Fehler- bzw. Erfolgmeldungen)
     */
    @Override
    protected ReturnCodeResponse doInBackground(Integer... params) {
        NoobOnlineService onlineService;
        if(myApp.isTestmode()) {
            onlineService = new NoobOnlineServiceMock();
        }
        else {
            onlineService  = new NoobOnlineServiceImpl();
        }
        ReturnCodeResponse response = onlineService.logout(params[0]);
        return response;
    }

    /**
     * Der Returncode vom Server wird entgegen genommen.
     * @param response ReturnCodeRespone (Enthält Fehler- bzw. Erfolgmeldungen)
     */
    @Override
    protected void onPostExecute (ReturnCodeResponse response) {
        if (response != null) {
            Log.d(TAG, "Returncode: " + response.getReturnCode());
            Toast.makeText(context, R.string.erfolg, Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(TAG, "keine Verbindung zum Server");
            Toast.makeText(context, R.string.keine_verbindung, Toast.LENGTH_SHORT).show();
        }
    }
}
