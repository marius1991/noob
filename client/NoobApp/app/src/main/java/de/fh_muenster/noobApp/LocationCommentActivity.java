package de.fh_muenster.noobApp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * @author marius
 * In dieser Activity kann ein Kommentar zu einer Location geschrieben werden
 */
public class LocationCommentActivity extends ActionBarActivity {
    private EditText editText;
    private static final String TAG = LocationCommentActivity.class.getName();

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

    public void clickFuncCommentOnLocation(View view) {
        NoobApplication myApp = (NoobApplication) getApplication();
        new CommentOnLocation().execute(editText.getText().toString());
    }

    public void clickFuncLogout(MenuItem item) {
        Log.d(TAG, "Menüeintrag 'Logout' ausgewählt");
        new AlertDialog.Builder(this)
                .setMessage("Wollen Sie sich wirklich abmelden?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NoobApplication myApp = (NoobApplication) getApplication();
                        new LogoutTask(getApplicationContext()).execute(myApp.getSessionId());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nein", null)
                .show();
    }

    class CommentOnLocation extends AsyncTask<String, String, ReturnCodeResponse> {
        private ProgressDialog Dialog = new ProgressDialog(LocationCommentActivity.this);

        /**
         * Während des Kommentierens wird ein Dialog angezeigt
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Kommentieren...");
            Dialog.show();
        }

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            NoobApplication myApp = (NoobApplication) getApplication();
            ReturnCodeResponse returnCodeResponse;
            returnCodeResponse = onlineService.commentOnLocation(myApp.getSessionId(), myApp.getLocation().getId(), params[0]);
            return returnCodeResponse;
        }

        @Override
        protected void onPostExecute (ReturnCodeResponse response) {
            Dialog.dismiss();
            if (response.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(LocationCommentActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                LocationCommentActivity.this.finish();
            }
        }
    }

}
