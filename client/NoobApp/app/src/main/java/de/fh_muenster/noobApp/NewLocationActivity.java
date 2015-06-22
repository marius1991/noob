package de.fh_muenster.noobApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.ReturnCodeResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by marco
 * Diese Activity ist für die Registrieung eines Users zuständig
 * @author marco
 */
public class NewLocationActivity extends ActionBarActivity {
    private final int PICK_IMAGE_REQUEST = 1;
    private final int CAMERA_REQUEST = 2;
    private EditText locationame;
    private EditText beschreibung;
    private EditText strasse;
    private EditText nummer;
    private EditText plz;
    private EditText ort;
    private String locationameString;
    private String beschreibungString;
    private String strasseString;
    private String nummerString;
    private String plZSring;
    private String ortString;
    private Button neueLocation;
    private Bitmap bitmap;
    private Bitmap bitmapnew;
    private byte[] byteArray;
    List<String> categoryList;
    private String selectedSpinnerElementString;
    private static final String TAG = NewLocationActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location2);

        //Füllt die Variable "myApp" mit der globalen Applikation Klasse
        final NoobApplication myApp = (NoobApplication) getApplication();

        //Speichert die aktuellen Kategorien in die Variable "categoryList"
        categoryList = myApp.getCategories();

        //Füllt die Variablen mit den View-Elementen aus der NewLocationActivity
        locationame = (EditText) findViewById(R.id.editText9);
        beschreibung = (EditText) findViewById(R.id.editText10);
        strasse = (EditText) findViewById(R.id.editText18);
        nummer = (EditText) findViewById(R.id.editText21);
        plz = (EditText) findViewById(R.id.editText11);
        ort = (EditText) findViewById(R.id.editText);
        neueLocation = (Button) findViewById(R.id.button15);

        //Füllt den Spinner mit den Kategorien, wenn ein Element ausgewählt worden ist wird dieser in einem string gespeichert
        if (!categoryList.isEmpty()) {
            ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, categoryList);
            Spinner spinner = (Spinner) findViewById(R.id.spinner2);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                /**
                 * Überpürft ob ein Element ausgewhätl worden ist
                 * @param parent
                 * @param selectedItemView
                 * @param position
                 * @param id
                 */
                @Override
                public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                    selectedSpinnerElementString = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        }

        beschreibung.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode überprüft, ob beim Textfeld "beschreibung" der Focus verlassen worden ist && das Textfeld "beschreibung" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !beschreibung.getText().toString().isEmpty()) {
                    beschreibung.setError(null);
                }
            }
        });

        locationame.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode überprüft, ob beim Textfeld "locationame" der Focus verlassen worden ist && das Textfeld "locationame" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !locationame.getText().toString().isEmpty()) {
                    locationame.setError(null);
                }
            }
        });

        strasse.setOnFocusChangeListener(new View.OnFocusChangeListener(
                /**
                 * Diese Methode überprüft, ob beim Textfeld "strasse" der Focus verlassen worden ist && das Textfeld "strasse" nicht leer ist
                 * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
                 * @param v
                 * @param hasFocus
                 */
        ) {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !strasse.getText().toString().isEmpty()) {
                    strasse.setError(null);
                }
            }
        });

        nummer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode überprüft, ob beim Textfeld "nummer" der Focus verlassen worden ist && das Textfeld "nummer" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !nummer.getText().toString().isEmpty()) {
                    nummer.setError(null);
                }
            }
        });

        plz.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode überprüft, ob beim Textfeld "plz" der Focus verlassen worden ist && das Textfeld "plz" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !plz.getText().toString().isEmpty()) {
                    plz.setError(null);
                }
            }
        });

        ort.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode überprüft, ob beim Textfeld "ort" der Focus verlassen worden ist && das Textfeld "ort" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !ort.getText().toString().isEmpty()) {
                    ort.setError(null);
                }
            }
        });


        neueLocation.setOnClickListener(new View.OnClickListener() {
            /**
             * Diese Methode überprüft, ob die Felder anhand der Validierung und einfachen IF-Abfragen ob
             * die Daten richtig eingegeben worden sind.
             * Ist dies der Fall wird Asynctask "NewLocationTask" aufgerufen.
             * @param view
             */
            @Override
            public void onClick(View view) {
                boolean isCorrect = true;
                if (locationame.getText().toString().isEmpty()) {
                    locationame.setError("Bitte Locationame Eintragen");
                    locationame.requestFocus();
                    isCorrect = false;
                }
                if (beschreibung.getText().toString().isEmpty()) {
                    beschreibung.setError("Bitte Beschreibung eintragen");
                    beschreibung.requestFocus();
                    isCorrect = false;
                }
                if (strasse.getText().toString().isEmpty()) {
                    strasse.setError("Bitte Strasse eintragen");
                    strasse.requestFocus();
                    isCorrect = false;
                }
                if (nummer.getText().toString().isEmpty()) {
                    nummer.setError("Bitte Nummer eintragen");
                    nummer.requestFocus();
                }
                if (plz.getText().toString().isEmpty()) {
                    plz.setError("Bitte PLZ eintragen");
                    plz.requestFocus();
                    isCorrect = false;
                }
                if (plz.getText().toString().length() != 4) {
                    plz.setError("PLZ muss 5 Zeichen lang sein");
                    plz.requestFocus();
                    isCorrect = false;
                }
                if (ort.getText().toString().equals("")) {
                    ort.setError("Bitte Ort eintragen");
                    ort.requestFocus();
                    isCorrect = false;
                }
                if (isCorrect && !locationame.getText().toString().isEmpty() && !beschreibung.getText().toString().isEmpty() && !strasse.getText().toString().isEmpty() && !nummer.getText().toString().isEmpty() && !plz.getText().toString().isEmpty() && !ort.getText().toString().isEmpty()) locationameString = locationame.getText().toString();
                beschreibungString = beschreibung.getText().toString();
                strasseString = strasse.getText().toString();
                plZSring = plz.getText().toString();
                ortString = ort.getText().toString();
                nummerString = nummer.getText().toString();
                locationameString = locationame.getText().toString();
                bitmapToByte();
                NewLocationTask newLocation = new NewLocationTask();
                newLocation.execute(locationameString, selectedSpinnerElementString, beschreibungString, strasseString, nummerString, plZSring, ortString);
            }

        });
    }

    /**
     *Diese Methode startet die Kamera mithilfe des Intent
     * @param view
     */
    public void startKamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAMERA_REQUEST);
    }



    /**
     * Diese Methode überprüft, ob die die Variable bitmap leer ist. Ist dies der Fall, wird das neue Bild in die Bitmap
     * geschrieben und in der imageView gesetzt. Es wird unterschieden zwischen Bilder von der Kamera und von der Gallery, da
     * die bitmap Zuweisung jeweils anders verläuft.
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
               if (bitmap != null) {
                   bitmap.recycle();
                   bitmap = null;
                }
                bitmap = (Bitmap) data.getExtras().get("data");
                ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(bitmap);

        }

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            Uri uri = data.getData();

                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(bitmap);

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Diese Methode wandelt die bitmap in ein byteArray, um diese dann in der Datanbank abspeichern
     * zu können
     */
    public void bitmapToByte() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapnew=Bitmap.createScaledBitmap(bitmap,400,400,true);
        bitmapnew.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
    }

    /**
     * Diese Methode startet die Gallery mihilfe eines Intent
     * @param view
     */
    public void pickPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_location, menu);
        return true;

    }

    /**
     * Auswahl ActionBar GoogleMaps, OpenInternet
     * @param item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_maps:
                openGoogleMaps();
                return true;
            case R.id.action_internet:
                openInternet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Diese Methode löscht das bild aus der ImageView und aus der Bitmap
     * @param view
     */
    public void deletePicture(View view){
        if(((ImageView) findViewById(R.id.imageView4)).getDrawable()!=null)
        ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(null);
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }
    //Diese Methode öffnet GoogleMAps mithilfe einens Intent. Latitude und Longitude sind jeweils 0
    public void openGoogleMaps() {
        String geoUriString = getResources().getString(R.string.map_location);
        Uri geoUri = Uri.parse(geoUriString);
        Intent mapCall = new Intent(Intent.ACTION_VIEW, geoUri);
        startActivity(mapCall);
    }
    //Diese Methode öffnet den Browser mithilfe eines Intent. Startwert ist google
    public void openInternet(){
        String url = "http://www.google.de";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }



    //Asynctask für Register
    class NewLocationTask extends AsyncTask<String, String, ReturnCodeResponse> {
        int sessionID = 0;
        private ProgressDialog Dialog = new ProgressDialog(NewLocationActivity.this);


        //Diese Methode zeigt während des NeueLocation anlegen Vorgangs ein Dialog an
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Location wird angelegt...");
            Dialog.show();
        }

        /**
         * Diese Methode, führt einen Thread schickt die Werte von der neu erstellen Location zum Server
         * @param params
         */
        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            NoobApplication myApp = (NoobApplication) getApplication();
            sessionID = myApp.getSessionId();
            int plz = Integer.parseInt(params[5]);
            ReturnCodeResponse response;
            if(byteArray==null){
                Log.d(TAG, "ByteArrayistLeer");
            }
            response = onlineService.createLocation(sessionID, params[0], params[1], params[2], params[3], params[4], plz, params[6],byteArray);
            return response;
        }

        /**
         * Diese Methode wird ausgeführt nachdem doInBackground durchgelaufen ist.Überprüft den returnCode vom Server,
         * ob alles geklappt hat
         * @param response
         */
        @Override
        protected void onPostExecute(ReturnCodeResponse response) {
            Dialog.dismiss();
            if (response.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(NewLocationActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}







