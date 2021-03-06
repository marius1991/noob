package de.fh_muenster.noobApp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.kobjects.base64.Base64;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Created by marius on 02.06.15.
 * Diese Activity zeigt eine Location und deren Details.
 * @author marius
 */
public class LocationShowActivity extends ActionBarActivity {

    private static final String TAG = LocationShowActivity.class.getName();
    private int newRating = 0;
    private int currentimageindex = 0;
    private ImageView slidingimage;
    private List<byte[]> imagesbytes;

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
        if(myApp.getLocation() != null) {
            //Titel der Activity ersetzen
            setTitle(myApp.getLocation().getName() + " in " + myApp.getCity());

            //Adresse ersetzten
            TextView textViewAddress = (TextView) findViewById(R.id.textView14);
            textViewAddress.setText(myApp.getLocation().getStreet() + " " + myApp.getLocation().getNumber() + " " + myApp.getLocation().getPlz() + " " + myApp.getLocation().getCity());

            //Beschreibung ersetzen
            TextView textViewDescription = (TextView) findViewById(R.id.textView15);
            textViewDescription.setText(myApp.getLocation().getDescription() + "\n" + "Erstellt von: " + myApp.getLocation().getOwnerName());

            //Rating ersetzen
            TextView textViewRating = (TextView) findViewById(R.id.textView12);
            textViewRating.setText("Bewertung | Durchschnitt: " + myApp.getLocation().getAverageRating() + "/5.0 Sterne");
            new GetLocationDetailsFromServer().execute(myApp.getLocation().getId());
        }
        else {
            LocationShowActivity.this.finish();
        }
    }

    /**
     * Diese Methode wird aufgerufen, wenn man die Activity verlässt.
     * Das Rating wird mit einem AsyncTask zum Server gesendet.
     */
    @Override
    protected  void onPause() {
        super.onPause();
        NoobApplication myApp = (NoobApplication) getApplication();
        if(myApp.getLocation() != null) {
            if (newRating != 0) {
                new SendRatingToServer().execute(Math.round(newRating));
            }
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
     * Diese Methode wird aufgerufen, wenn auf den Button "Kommentieren" gedrückt wird
     * Sie startet eine neue Activity.
     * @param view
     */
    public void clickFuncComment(View view) {
        Intent i = new Intent(LocationShowActivity.this, LocationCommentActivity.class);
        startActivity(i);
    }

    /**
     * Diese Methode wird aufgerufen, wenn auf den Button "Bearbeiten" gedrückt wird.
     * Dieser wird nur für den ersteller der Location angezeigt
     * @param view
     */
    public void clickFuncChange(View view) {
        Intent i = new Intent(LocationShowActivity.this, SetLocationDetailsActivity.class);
        startActivity(i);
    }

    /**
     * Diese Methode wird aufgerufen, wenn über das Menü der Eintrag 'Konto verwalten' geklickt wird.
     * Es wir die Activity für die Kontoverwaltung gestartet.
     * @param item
     */
    public void clickFuncUserDetails(MenuItem item) {
        Log.d(TAG, "Menüeintrag 'Benutzer bearbeiten' ausgewählt");
        Intent i = new Intent(LocationShowActivity.this, UserManagementAcitivtiy.class);
        startActivity(i);
    }

    /**
     * Diese Methode wird aufgerufen, wenn auf den Link "Maps" gedrückt wird.
     * Sie öffnet Google Maps und sucht nach der Adresse der Location
     * @param view
     */
    public void clickFuncOpenMaps(View view) {
        NoobApplication myApp = (NoobApplication) getApplication();
        String address = myApp.getLocation().getStreet() + " " + myApp.getLocation().getNumber() + " " + myApp.getLocation().getPlz() + " " + myApp.getLocation().getCity();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/?q=" + address));
        startActivity(intent);
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

    public void clickFuncUpload(View view) {
        Intent i = new Intent(LocationShowActivity.this, LocationAddImageActivity.class);
        startActivity(i);
    }

    /**
     * Helpermethode für die Bilderslideshow
     */
    private void AnimateandSlideShow() {
        slidingimage = (ImageView) findViewById(R.id.imageView3);
        List<Bitmap> imageBitmaps = new ArrayList<>();
        for (int i = 0; i < imagesbytes.size(); i++) {
            imageBitmaps.add(BitmapFactory.decodeByteArray(imagesbytes.get(i), 0, imagesbytes.get(i).length));
        }
        slidingimage.setImageBitmap(imageBitmaps.get(currentimageindex));
        if(currentimageindex < imagesbytes.size()-1) {
            currentimageindex++;
        }
        else {
            currentimageindex = 0;
        }
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        slidingimage.startAnimation(rotateimage);
    }

    /**
     * Diese Methode dient zum Anzeigen der Slideshow
     */
    public void showImages() {
        //Bild anzeigen
        final Handler mHandler = new Handler();
        NoobApplication myApp = (NoobApplication) getApplication();
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        if (myApp.getLocation().getImages() != null) {
            if (!myApp.getLocation().getImages().isEmpty()) {
                Log.d(TAG, Base64.encode(myApp.getLocation().getImages().get(0)));
                imagesbytes = myApp.getLocation().getImages();
                for (int i = 0; i < imagesbytes.size(); i++) {
                    Log.d(TAG, "IMAGE: " + Base64.encode(imagesbytes.get(i)));
                }
                if (imagesbytes.size() == 1) {
                    Bitmap image = BitmapFactory.decodeByteArray(imagesbytes.get(0), 0, imagesbytes.get(0).length);
                    imageView.setImageBitmap(image);
                } else {
                    final Runnable mUpdateResults = new Runnable() {
                        public void run() {
                            AnimateandSlideShow();
                        }
                    };
                    int delay = 1;
                    int period = 5000;
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            mHandler.post(mUpdateResults);
                        }
                    }, delay, period);
                }
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
            }
        } else {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
        }
    }


    /**
     * Dieser AsyncTask schickt das Rating zum Server.
     * @author marius
     */
    public class SendRatingToServer extends AsyncTask<Integer, String, ReturnCodeResponse> {

        /**
         * Es wird ein neuer Thread gestartet, in dem das Rating zum Server geschickt wird.
         * @param params
         * @return ReturnCodeRespone (Enthält Fehler- bzw. Erfolgmeldungen)
         */
        @Override
        protected ReturnCodeResponse doInBackground(Integer... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineService onlineService;
            if(myApp.isTestmode()) {
                onlineService = new NoobOnlineServiceMock();
            }
            else {
                onlineService  = new NoobOnlineServiceImpl();
            }
            Log.d(TAG, "ÜBERGABE: " + String.valueOf(myApp.getSessionId()));
            ReturnCodeResponse response = onlineService.giveRating(myApp.getSessionId(), myApp.getLocation().getId(), params[0]);
            return response;
        }

        /**
         * Nimmt den Returncode des Servers entgegen.
         * @param response ReturnCodeRespone (Enthält Fehler- bzw. Erfolgmeldungen)
         */
        @Override
        protected  void onPostExecute(ReturnCodeResponse response) {
            if (response.getReturnCode() == 10) {
                Log.d(TAG, "Keine Verbidung zum Server");
                Toast.makeText(getApplicationContext(), R.string.keine_verbindung, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Dieser AsyncTask ruft die Locationinformation der angezeigten Location vom Server ab.
     * @author marius
     */
    public class GetLocationDetailsFromServer extends AsyncTask<Integer, String, LocationTO> {
        private ProgressDialog Dialog = new ProgressDialog(LocationShowActivity.this);

        /**
         * Während des Abrufs der Location wird ein Dialog angezeigt.
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Locationdetails abrufen...");
            Dialog.show();
        }

        /**
         * Es wird ein Thread gestartet, in dem die Location vom Server abgerufen werden.
         * @param params
         * @return LocationTO (Enthält Locationinformationen)
         */
        @Override
        protected LocationTO doInBackground(Integer... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineService onlineService;
            if(myApp.isTestmode()) {
                onlineService = new NoobOnlineServiceMock();
            }
            else {
                onlineService  = new NoobOnlineServiceImpl();
            }
            LocationTO locationTO = onlineService.getLocationDetails(params[0]);
            return locationTO;
        }

        /**
         * Die Location wird vom Server entgegengenommen und bei Erfolg werden die aktuellen
         * Ratings und Kommentare angezeigt.
         * @param locationTO LocationTO (Enthält Locationinformationen)
         */
        @Override
        protected void onPostExecute (LocationTO locationTO) {
            Dialog.dismiss();
            if (locationTO.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), R.string.keine_verbindung, Toast.LENGTH_SHORT).show();
            }
            else {
                NoobApplication myApp = (NoobApplication) getApplication();
                myApp.setLocation(locationTO);
                Log.d(TAG, "Locationdetails abgerufen");
                //Falls der aktuelle User der Ersteller einer Location ist, wird der Button sichtbar
                View b = findViewById(R.id.buttonBearb);
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
                        authorLine.setText(comment.getOwnerName() + " (" + comment.getDate() +")");
                        authorLine.setTypeface(null, Typeface.BOLD);
                        authorLine.setTextColor(getResources().getColor(R.color.noob_blue));
                        TextView commentLine = new TextView(LocationShowActivity.this);
                        commentLine.setText(comment.getText());
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
                //Images laden
                showImages();
            }
        }
    }
}
