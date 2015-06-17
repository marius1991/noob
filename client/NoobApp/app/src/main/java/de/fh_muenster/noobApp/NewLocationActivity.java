package de.fh_muenster.noobApp;

import android.content.Intent;
import android.graphics.Bitmap;
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

import de.fh_muenster.exceptions.BadConnectionException;
import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.ReturnCodeResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class NewLocationActivity extends ActionBarActivity {
    //Standardwert der übergeben wird
    private int PICK_IMAGE_REQUEST = 1;
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
    private byte[] byteArray;
    List<String> categoryList;
    private String selectedSpinnerElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location2);
        GetCategoriesServer getCategories = new GetCategoriesServer();
        getCategories.execute();
        locationame = (EditText) findViewById(R.id.editText9);
        beschreibung = (EditText) findViewById(R.id.editText10);
        strasse = (EditText) findViewById(R.id.editText18);
        nummer = (EditText) findViewById(R.id.editText21);
        plz = (EditText) findViewById(R.id.editText11);
        ort = (EditText) findViewById(R.id.editText);
        if (!categoryList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, categoryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) findViewById(R.id.spinner3);
            spinner.setAdapter(adapter);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                    selectedSpinnerElement = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        }
        neueLocation = (Button) findViewById(R.id.button15);
        neueLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationame.getText().equals("")) {
                    locationame.setError("Bitte Locationame Eintragen");
                    locationame.requestFocus();
                } else if (beschreibung.getText().equals("")) {
                    beschreibung.setError("Bitte Beschreibung eintragen");
                    beschreibung.requestFocus();
                } else if (strasse.getText().equals("")) {
                    strasse.setError("Bitte Strasse eintragen");
                    strasse.requestFocus();
                } else if (nummer.getText().equals("")) {
                    nummer.setError("Bitte Nummer eintragen");
                    nummer.requestFocus();
                } else if (plz.getText().equals("")) {
                    plz.setError("Bitte PLZ eintragen");
                    plz.requestFocus();
                } else if (ort.getText().equals("")) {
                    ort.setError("Bitte Ort eintragen");
                    ort.requestFocus();
                } else {
                    locationameString = locationame.getText().toString();
                    beschreibungString = beschreibung.getText().toString();
                    strasseString = strasse.getText().toString();
                    plZSring = plz.getText().toString();
                    ortString = ort.getText().toString();
                    nummerString = nummer.getText().toString();
                    NewLocationTask newLocation = new NewLocationTask();
                    newLocation.execute(locationameString, selectedSpinnerElement, beschreibungString, strasseString, nummerString, plZSring, ortString);
                }

            }


        });
    }

    public void startKamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 0);
    }


    //Empfängt das Bild und prüft ihm resultcode ob ein bild gemacht worden ist

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                //bitmap Factory muss 0 sein. Wenn man erst ein Bild von Kamera und dann ein Bild
                //von der Gallery in die view lädt muss die bitmapfactory 0 sein!!
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //Wandelt die BitMap in ein ByteArray für die Datenbank
    public void bitmapToByte() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
    }

    public void pickPhoto(View view) {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_location, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_maps:
                openGoogleMaps();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openGoogleMaps() {
        String geoUriString = getResources().getString(R.string.map_location);
        Uri geoUri = Uri.parse(geoUriString);
        Intent mapCall = new Intent(Intent.ACTION_VIEW, geoUri);
        startActivity(mapCall);
    }

    //public void newLocation(View view) {
    //NoobApplication myApp = (NoobApplication) getApplication();
    //EditText locName = (EditText)findViewById(R.id.editText9);
    //EditText locCity = (EditText)findViewById(R.id.editText11);
    //Test
    //new NewLocationTask().execute(Integer.toString(myApp.getSessionId()), locName.getText().toString(), "Kneipe", "Tolle Kneipe", "Jüdefelder Str.", "15", Integer.toString(48150), locCity.getText().toString());


    //Asynchron senden (Innere Klasse)
    class NewLocationTask extends AsyncTask<String, String, ReturnCodeResponse> {
        int sessionID = 0;

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            NoobApplication myApp = (NoobApplication) getApplication();
            sessionID = myApp.getSessionId();
            int plz = Integer.parseInt(params[5]);
            ReturnCodeResponse response;
            response = onlineService.createLocation(sessionID, params[0], params[1], params[2], params[3], params[4], plz, params[6]);
            return response;
        }

        @Override
        protected void onPostExecute(ReturnCodeResponse response) {

        }
    }

    class GetCategoriesServer extends AsyncTask<String, String, CategoryListResponse> {

        @Override
        protected CategoryListResponse doInBackground(String... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            CategoryListResponse response = null;
            try {
                response = onlineService.listCategories();
            } catch (BadConnectionException e) {

            }
            return response;
        }


        @Override
        protected void onPostExecute(CategoryListResponse response) {
            Integer returnCode = response.getReturnCode();

            categoryList = response.getCategories();

        }
    }

}





