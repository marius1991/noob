package de.fh_muenster.noobApp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Created by marius on 15.06.15.
 */
public class LogoutTask extends AsyncTask <Integer, String, ReturnCodeResponse> {
    private static final String TAG = LogoutTask.class.getName();
    private Context context;

    public LogoutTask (Context context) {
        this.context = context;
    }

    @Override
    protected ReturnCodeResponse doInBackground(Integer... params) {
        NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
        ReturnCodeResponse response = onlineService.logout(params[0]);
        return response;
    }

    @Override
    protected void onPostExecute (ReturnCodeResponse response) {
        if (response != null) {
            Log.d(TAG, response.getMessage());
            Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(TAG, "keine Verbindung zum Server");
            Toast.makeText(context, "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
        }
    }
}
