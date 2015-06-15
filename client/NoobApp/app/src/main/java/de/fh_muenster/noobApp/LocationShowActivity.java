package de.fh_muenster.noobApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Diese Activity zeigt eine Location und deren Details
 */
public class LocationShowActivity extends ActionBarActivity {

    private static final String TAG = LocationShowActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_show);

        //Titel der Activity ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getLocation().getName() + " in " + myApp.getCity());

        //Adresse ersetzten
        TextView textViewAddress = (TextView)findViewById(R.id.textView14);
        textViewAddress.setText(myApp.getLocation().getStreet() + " " + myApp.getLocation().getNumber() + " " + myApp.getLocation().getPlz() + " " + myApp.getLocation().getCity());

        //Beschreibung ersetzen
        TextView textViewDescription = (TextView)findViewById(R.id.textView15);
        //textViewDescription.setText(myApp.getLocation().getDescription() + "\n" + "Inhaber: " + myApp.getLocation().getOwner().getName());

        //Rating ersetzen
        TextView textViewRating = (TextView)findViewById(R.id.textView12);
        textViewRating.append(" " + myApp.getLocation().getAverageRating() + "/5.0 Sterne");

        //RatingBar füllen, falls vorher bereits bewertet wurde
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Rating asynchron zum Server senden
                new SendRatingToServer().execute(Math.round(rating));
            }
        });
        if(myApp.getLocation().getRatings() != null) {
            for (int i = 0; i < myApp.getLocation().getRatings().size(); i++) {
                if (myApp.getLocation().getRatings().get(i).getOwnerId().equals(myApp.getUserId())) {
                    ratingBar.setRating(myApp.getLocation().getRatings().get(i).getValue());
                    //ratingBar.setRating(2.5f);
                    //TODO hier muss noch geprüft werden ob bereits bewertet wurde
                }
            }
        }
        else {
            Log.d(TAG, "Die Location wurde noch nicht bewertet");
            //ratingBar.setRating(2.5f);
        }

        //Kommentarliste füllen
        List <CommentTO> comments;
        List <String> valueList = new ArrayList<>();
        comments = myApp.getLocation().getComments();
        if (comments != null) {
            for (int i=0; i<comments.size(); i++) {
                valueList.add(comments.get(i).getText());
            }
            ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.comment_item, valueList);
            final ListView lv = (ListView)findViewById(R.id.listView3);
            lv.setAdapter(adapter);
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
     * Sie startet eine neue Activity
     * @param view
     */
    public void clickFuncComment(View view){
        Intent i = new Intent(LocationShowActivity.this, LocationCommentActivity.class);
        startActivity(i);
    }

    /**
     * @author marius
     * Dieser AsyncTask schickt das Rating zum Server
     */
    public class SendRatingToServer extends AsyncTask<Integer, String, ReturnCodeResponse> {
        private ProgressDialog Dialog = new ProgressDialog(LocationShowActivity.this);

        /**
         * Während des Abrufs der Location wird ein Dialog angezeigt.
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Locationinformationen abrufen...");
            Dialog.show();
        }

        /**
         * Es wird ein neuer Thread gestartet, in dem das Rating zum Server geschickt wird
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
         * Gibt bei Erfolg eine Meldung
         * @param response
         */
        @Override
        protected  void onPostExecute(ReturnCodeResponse response) {
            Dialog.dismiss();
            if (response != null) {
                Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                Log.d(TAG, "keine Verbindung zum Server");
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
