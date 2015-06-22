package de.fh_muenster.noobApp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Created by marco
 * Diese Activity ist für die Bearbeitung einer Location zuständig
 * @author marco
 */
public class SetLocationDetailsActivity extends ActionBarActivity {
    private EditText locationame;
    private EditText beschreibung;
    private EditText strasse;
    private EditText nummer;
    private EditText plz;
    private EditText ort;
    private Button editlocation;
    private LocationTO locationTO;
    private NoobApplication myApp;
    List<String> categoryList;
    private String selectedSpinnerElement;
    private static final String TAG = SetLocationDetailsActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location_details);

        //Akutelle Location holen von der Applikation Klasse
        myApp = (NoobApplication) getApplication();
        locationTO=myApp.getLocation();

        //Speichert die aktuellen Kategorien in die Variable "categoryList"
        categoryList=myApp.getCategories();

        //Füllt die Variablen mit den View-Elementen aus der SetLocationDetailsActitvity
        editlocation=(Button) findViewById(R.id.button15);
        locationame = (EditText) findViewById(R.id.editText9);
        beschreibung= (EditText) findViewById(R.id.editText10);
        strasse= (EditText) findViewById(R.id.editText18);
        nummer= (EditText) findViewById(R.id.editText21);
        plz= (EditText) findViewById(R.id.editText11);
        ort= (EditText) findViewById(R.id.editText);

        //Setze Hint Elemente mit den akutelen Weten aus der locationTO
        locationame.setHint(locationTO.getName());
        beschreibung.setHint(locationTO.getDescription());
        strasse.setHint(locationTO.getStreet());
        nummer.setHint(locationTO.getNumber());
        plz.setHint(String.valueOf(locationTO.getPlz()));
        ort.setHint(locationTO.getCity());


        //Übergebe dem Spinner die Kategorien
        if(!categoryList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, categoryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) findViewById(R.id.spinner2);
            spinner.setAdapter(adapter);


            //Setzt den Spinner auf den Wert der bei ShowLocation angezeigt wird
            int spinnerPosition = adapter.getPosition(locationTO.getCategory());
            spinner.setSelection(spinnerPosition);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                /**
                 * Speichert das SpinnerElemnt in einem String
                 * @param parent
                 * @param selectedItemView
                 * @param position
                 * @param id
                 */
                @Override
                public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                    selectedSpinnerElement = parent.getItemAtPosition(position).toString();
                    Log.d(TAG, selectedSpinnerElement);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        }

        editlocation.setOnClickListener(new View.OnClickListener() {
            /**
             * Diese Methode überprüft, ob die Felder geändert worden sind
             * Ist dies der Fall wird Asynctask "SetLocationDetails" aufgerufen.
             * @param view
             */
            @Override
            public void onClick(View view) {
                if(!locationame.getText().toString().equals("")) {
                    locationTO.setName(locationame.getText().toString());
                }
                if(!beschreibung.getText().toString().equals("")){
                    locationTO.setDescription(beschreibung.getText().toString());
                }
                if(!strasse.getText().toString().equals("")) {
                    locationTO.setStreet(strasse.getText().toString());
                }
                if(!nummer.getText().toString().equals("")) {
                    locationTO.setNumber(nummer.getText().toString());
                }
                if(!plz.getText().toString().equals("")) {
                    locationTO.setPlz(Integer.parseInt(plz.getText().toString()));
                }
                if(!ort.getText().toString().equals("")) {
                    locationTO.setCity(ort.getText().toString());
                }

                if(locationame.getText().toString().isEmpty()&&beschreibung.getText().toString().isEmpty()&&strasse.getText().toString().isEmpty()&&nummer.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Es wurde keine Werte geändert", Toast.LENGTH_SHORT).show();
                }

                if(!locationame.getText().toString().isEmpty()||!beschreibung.getText().toString().isEmpty()||!strasse.getText().toString().isEmpty()||!nummer.getText().toString().isEmpty()||!plz.getText().toString().isEmpty()||!ort.getText().toString().isEmpty()){
                    myApp.setLocation(locationTO);
                    SetLocationDetails setLocation = new SetLocationDetails(view.getContext());
                    setLocation.execute();
                }

        }


        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_location_details, menu);
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

    //Asynctask um die geänderte Werte zu speichern
    public class SetLocationDetails extends AsyncTask<String, String, ReturnCodeResponse> {
        private int sessionId;
        private String message;
        private Context context;
        private ProgressDialog Dialog = new ProgressDialog(SetLocationDetailsActivity.this);

        public SetLocationDetails(Context context){
            this.context=context;
        }

        //Diese Methode zeigt während des NeueLocation anlegen Vorgangs ein Dialog an
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Geänderte Daten werde gespeichert...");
            Dialog.show();
        }

        /**
         * Diese Methode, führt einen Thread schickt die Werte von den geänderten Werten zum Server
         * @param params
         */
        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            sessionId = myApp.getSessionId();
            ReturnCodeResponse setLocation = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            locationTO = myApp.getLocation();
            setLocation = onlineService.setLocationDetails(sessionId,locationTO.getId(),locationTO.getName(),locationTO.getCategory(),locationTO.getDescription(),locationTO.getStreet(),locationTO.getNumber(),locationTO.getPlz(),locationTO.getCity());
            message = setLocation.getMessage();
            return setLocation;

        }

        /**
         * Diese Methode wird ausgeführt nachdem doInBackground durchgelaufen ist.Überprüft den returnCode vom Server,
         * ob alles geklappt hat
         * @param returnCodeResponse
         */
        protected void onPostExecute(ReturnCodeResponse returnCodeResponse){
            Dialog.dismiss();
            if (returnCodeResponse.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


        }

    }

}
