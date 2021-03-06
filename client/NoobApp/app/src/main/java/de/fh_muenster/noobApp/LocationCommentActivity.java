package de.fh_muenster.noobApp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * In dieser Activity kann ein Kommentar zu einer Location geschrieben werden.
 * @author marius
 */
public class LocationCommentActivity extends ActionBarActivity {
    private EditText editText;
    private static final String TAG = LocationCommentActivity.class.getName();

    /**
     * Diese Methode wird beim Start der Activity aufgerufen. Sie ändert den Titel der Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_comment);

        //Titel der Activity ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getLocation().getName() + " kommentieren");
        editText = (EditText)findViewById(R.id.editText16);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Diese Methode wird aufgerufen, wenn auf den Button "Kommentieren" geklickt wird.
     * Sie startet den AsyncTask zum Kommentieren einer Location.
     * @param view
     */
    public void clickFuncCommentOnLocation(View view) {
        if(!editText.getText().toString().equals("")) {
            new CommentOnLocation().execute(editText.getText().toString());
        }
        else {
            Toast.makeText(LocationCommentActivity.this, R.string.activity_location_comment_leer, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Diese Methode wird aufgerufen, wenn über das Menü der Eintrag 'Konto verwalten' geklickt wird.
     * Es wir die Activity für die Kontoverwaltung gestartet.
     * @param item
     */
    public void clickFuncUserDetails(MenuItem item) {
        Log.d(TAG, "Menüeintrag 'Benutzer bearbeiten' ausgewählt");
        Intent i = new Intent(LocationCommentActivity.this, UserManagementAcitivtiy.class);
        startActivity(i);
    }

    /**
     * Diese Methode wird aufgerufen, wenn über das Menü der Eintrag 'Logout' gewählt wird.
     * Es erscheint eine Dialog, auf dem die Eingabe bestätigt werden muss.
     * Dann wird ein LogoutTask gestartet.
     * @param item
     */
    public void clickFuncLogout(MenuItem item) {
        Log.d(TAG, "Menüeintrag 'Logout' ausgewählt");
        new AlertDialog.Builder(this)
                .setMessage(R.string.menu_ausloggen_frage)
                .setCancelable(false)
                .setPositiveButton(R.string.menu_ausloggen_ja, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NoobApplication myApp = (NoobApplication) getApplication();
                        new LogoutTask(getApplicationContext(), myApp).execute(myApp.getSessionId());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.menu_ausloggen_nein, null)
                .show();
    }

    /**
     * Dieser AsyncTask realisiert die Kommentarfunktion.
     * @author marius
     */
    class CommentOnLocation extends AsyncTask<String, String, ReturnCodeResponse> {
        private ProgressDialog Dialog = new ProgressDialog(LocationCommentActivity.this);

        /**
         * Während des Kommentierens wird ein Dialog angezeigt.
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Kommentieren...");
            Dialog.show();
        }

        /**
         * Startet einen neuen Thread, in dem der Kommentar zum Server geschickt wird.
         * @param params
         * @return ReturnCodeRespone (Enthält Fehler- bzw. Erfolgmeldungen)
         */
        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineService onlineService;
            if(myApp.isTestmode()) {
                onlineService = new NoobOnlineServiceMock();
            }
            else {
                onlineService  = new NoobOnlineServiceImpl();
            }
            ReturnCodeResponse returnCodeResponse;
            returnCodeResponse = onlineService.commentOnLocation(myApp.getSessionId(), myApp.getLocation().getId(), params[0]);
            return returnCodeResponse;
        }

        /**
         * Der Returncode des Servers wird entgegengenommen
         * @param response ReturnCodeRespone (Enthält Fehler- bzw. Erfolgmeldungen)
         */
        @Override
        protected void onPostExecute (ReturnCodeResponse response) {
            Dialog.dismiss();
            if (response.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), R.string.keine_verbindung, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(LocationCommentActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                LocationCommentActivity.this.finish();
            }
        }
    }

}
