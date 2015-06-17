package de.fh_muenster.noobApp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Diese Activity zeigt eine Location und deren Details.
 */
public class LocationShowActivity extends ActionBarActivity {

    private static final String TAG = LocationShowActivity.class.getName();
    private int newRating = 0;

    /**
     * Diese Methode wird beim Starten der Activity aufgerufen.
     * Der Titel der Activity wird gesetzt.
     * Die Details der Location werden angezeigt.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_show);

        //Titel der Activity ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getLocation().getName() + " in " + myApp.getCity());

        //Bild anzeigen
        ImageView imageView = (ImageView)findViewById(R.id.imageView3);
        //TODO: Bild laden

        //Adresse ersetzten
        TextView textViewAddress = (TextView)findViewById(R.id.textView14);
        textViewAddress.setText(myApp.getLocation().getStreet() + " " + myApp.getLocation().getNumber() + " " + myApp.getLocation().getPlz() + " " + myApp.getLocation().getCity());

        //Beschreibung ersetzen
        TextView textViewDescription = (TextView)findViewById(R.id.textView15);
        textViewDescription.setText(myApp.getLocation().getDescription() + "\n" + "Erstellt von: " + myApp.getLocation().getOwnerId());

        //Rating ersetzen
        TextView textViewRating = (TextView)findViewById(R.id.textView12);
        textViewRating.append(" | Durchschnitt: " + myApp.getLocation().getAverageRating() + "/5.0 Sterne");

    }

    /**
     * Diese Methode wird aufgerufen, wenn auf die Activity gewechselt wird. Auch wenn sie vorher
     * nur pausiert wurde. Die aktualisierten Locationinformationen werden mit einem AsyncTask abgerufen.
     */
    @Override
    protected void onResume() {
        super.onResume();
        NoobApplication myApp = (NoobApplication) getApplication();
        //Aktualisierte Location abrufen
        new GetLocationDetailsFromServer().execute(myApp.getLocation().getId());
    }

    /**
     * Diese Methode wird aufgerufen, wenn man die Activity verlässt.
     * Das Rating wird mit einem AsyncTask zum Server gesendet.
     */
    @Override
    protected  void onPause() {
        super.onPause();
        if(newRating != 0) {
            new SendRatingToServer().execute(Math.round(newRating));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_show, menu);
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
     * Diese Funktion wird aufgerufen, wenn auf den Button "Kommentieren" gedrückt wird
     * Sie startet eine neue Activity.
     * @param view
     */
    public void clickFuncComment(View view) {
        Intent i = new Intent(LocationShowActivity.this, LocationCommentActivity.class);
        startActivity(i);
    }

    /**
     * Diese Funktion wird aufgerufen, wenn auf den Button "Bearbeiten" gedrückt wird.
     * Dieser wird nur für den ersteller der Location angezeigt
     * @param view
     */
    public void clickFuncChange(View view) {
        Intent i = new Intent(LocationShowActivity.this, SetLocationDetailsActivity.class);
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

    /**
     * @author marius
     * Dieser AsyncTask schickt das Rating zum Server.
     */
    public class SendRatingToServer extends AsyncTask<Integer, String, ReturnCodeResponse> {
        private ProgressDialog Dialog = new ProgressDialog(LocationShowActivity.this);

        /**
         * Während des Abrufs der Location wird ein Dialog angezeigt.
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Rating an den Server senden...");
            Dialog.show();
        }

        /**
         * Es wird ein neuer Thread gestartet, in dem das Rating zum Server geschickt wird.
         * @param params
         * @return
         */
        @Override
        protected ReturnCodeResponse doInBackground(Integer... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            Log.d(TAG, "ÜBERGABE: " + String.valueOf(myApp.getSessionId()));
            ReturnCodeResponse response = onlineService.giveRating(myApp.getSessionId(), myApp.getLocation().getId(), params[0]);
            return response;
        }

        /**
         * Nimmt den Returncode des Servers entgegen.
         * @param response
         */
        @Override
        protected  void onPostExecute(ReturnCodeResponse response) {
            Dialog.dismiss();
            if (response.getReturnCode() == 10) {
                Log.d(TAG, "keine Verbindung zum Server");
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * @author marius
     * Dieser AsyncTask ruft die Locationinformation der angezeigten Location vom Server ab.
     */
    public class GetLocationDetailsFromServer extends AsyncTask<Integer, String, LocationTO> {
        private ProgressDialog Dialog = new ProgressDialog(LocationShowActivity.this);

        /**
         * Während des Abrufs der Location wird ein Dialog angezeigt.
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Rating an den Server senden...");
            Dialog.show();
        }

        /**
         * Es wird ein Thread gestartet, in dem die Location vom Server abgerufen werden.
         * @param params
         * @return
         */
        @Override
        protected LocationTO doInBackground(Integer... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            LocationTO locationTO = onlineService.getLocationDetails(params[0]);
            return locationTO;
        }

        /**
         * Die Location wird vom Server entgegengenommen und bei Erfolg werden die aktuellen
         * Ratings und Kommentare angezeigt.
         * @param locationTO
         */
        @Override
        protected void onPostExecute (LocationTO locationTO) {
            Dialog.dismiss();
            if (locationTO.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
            else {
                NoobApplication myApp = (NoobApplication) getApplication();
                myApp.setLocation(locationTO);
                Log.d(TAG, "Locationdetails abgerufen");
                //Falls der aktuelle User der Ersteller einer Location ist, wird der Button sichtbar
                View b = findViewById(R.id.button);
                if(myApp.getLocation().getOwnerId().equals(myApp.getUserId())) {
                    b.setVisibility(View.VISIBLE);
                }
                else {
                    b.setVisibility(View.INVISIBLE);
                }
                //Kommentarliste füllen
                List<CommentTO> comments = myApp.getLocation().getComments();
                LinearLayout list = (LinearLayout)findViewById(R.id.comments);
                list.removeAllViews();
                if (comments != null) {
                    for (int i=0; i<comments.size(); i++) {
                        CommentTO comment = comments.get(i);
                        TextView authorLine = new TextView(LocationShowActivity.this);
                        authorLine.setText(comment.getOwnerId() + " (" + comment.getDate() +")");
                        authorLine.setTypeface(null, Typeface.BOLD);
                        TextView commentLine = new TextView(LocationShowActivity.this);
                        commentLine.setText(" " + comment.getText());
                        list.addView(authorLine);
                        list.addView(commentLine);
                    }
                }
                //RatingBar füllen, falls vorher bereits bewertet wurde
                RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        newRating = Math.round(rating);
                    }
                });
                if(myApp.getLocation().getRatings() != null) {
                    for (int i = 0; i < myApp.getLocation().getRatings().size(); i++) {
                        if (myApp.getLocation().getRatings().get(i).getOwnerId().equals(myApp.getUserId())) {
                            Log.d(TAG, "Gespeichertes Rating: " + myApp.getLocation().getRatings().get(i).getValue());
                            ratingBar.setRating(myApp.getLocation().getRatings().get(i).getValue());
                        }
                    }
                }
                else {
                    Log.d(TAG, "Die Location wurde noch nicht bewertet");
                }
            }
        }
    }
}
