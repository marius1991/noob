package de.fh_muenster.noobApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.exceptions.BadConnectionException;
import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.ReturnCodeResponse;


public class SetLocationDetailsActivity extends ActionBarActivity {
    private EditText locationame;
    private EditText beschreibung;
    private EditText strasse;
    private EditText nummer;
    private EditText plz;
    private EditText ort;
    private Button editlocation;
    private Button locationLoeschen;
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
        //ButtonsFestlegen
        editlocation=(Button) findViewById(R.id.button9);
        locationame = (EditText) findViewById(R.id.editText28);
        beschreibung= (EditText) findViewById(R.id.editText27);
        strasse= (EditText) findViewById(R.id.editText12);
        nummer= (EditText) findViewById(R.id.editText19);
        plz= (EditText) findViewById(R.id.editText2);
        ort= (EditText) findViewById(R.id.editText20);
        //Hier eventuell noch überprüfen ob locationTO nicht leer ist
        locationame.setHint(locationTO.getName());
        beschreibung.setHint(locationTO.getDescription());
        strasse.setHint(locationTO.getStreet());
        nummer.setHint(locationTO.getNumber());
        plz.setHint(String.valueOf(locationTO.getPlz()));
        ort.setHint(locationTO.getCity());
        GetCategories getCategories = new GetCategories();
        getCategories.execute();
        //Befüllt Spinner mit dem Array from Kategories vom Server(categoryList)
        if(!categoryList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, categoryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) findViewById(R.id.spinner3);
            spinner.setAdapter(adapter);
            //krallt sich die SpinnerPosition von der ausgewählten Kategory
            int spinnerPostion = adapter.getPosition(locationTO.getCategory());
            //Setzt den Spinner auf die Kategorie
            spinner.setSelection(spinnerPostion);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

        //ClickListener für setLocationDetails
        editlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationame.getHint().equals("")||locationame.getText().equals("")){
                    locationame.setError("Bitte Locationame Eintragen");
                    locationame.requestFocus();
                }else if(beschreibung.getHint().equals("")||beschreibung.getText().equals("")){
                    beschreibung.setError("Bitte Beschreibung eintragen");
                    beschreibung.requestFocus();
                }else if(strasse.getHint().equals("")||strasse.getText().equals("")){
                    strasse.setError("Bitte Strasse eintragen");
                    strasse.requestFocus();
                }else if(nummer.getHint().equals("")||nummer.getText().equals("")) {
                    nummer.setError("Bitte Nummer eintragen");
                    nummer.requestFocus();
                }else if(plz.getHint().equals("")||plz.getText().equals("")) {
                    plz.setError("Bitte PLZ eintragen");
                    plz.requestFocus();
                }else if(ort.getHint().equals("")||ort.getText().equals("")) {
                    ort.setError("Bitte Ort eintragen");
                    ort.requestFocus();
                }else {
                    if(!locationame.getText().equals("")) {
                        locationTO.setName(locationame.getText().toString());
                    }else if(!beschreibung.getText().equals("")){
                        locationTO.setDescription(beschreibung.getText().toString());
                    }else if(!strasse.getText().equals("")){
                        locationTO.setStreet(strasse.getText().toString());
                    }
                    else if(!nummer.getText().equals("")){
                        locationTO.setNumber(nummer.getText().toString());
                    }else if(!plz.getText().equals("")){
                        locationTO.setPlz(Integer.parseInt(plz.getText().toString()));
                    }else if(!ort.getText().equals("")){
                        locationTO.setCity(ort.getText().toString());
                    }else {
                        myApp.setLocation(locationTO);
                        SetLocationDetails setLocation = new SetLocationDetails(view.getContext());
                        setLocation.execute();
                    }

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
    public class SetLocationDetails extends AsyncTask<String, String, ReturnCodeResponse> {
        private int returnCode;
        private int sessionId;
        private String message;
        private Context context;
        public SetLocationDetails(Context context){
            this.context=context;
        }

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobApplication myApp = (NoobApplication) getApplication();

            sessionId=myApp.getSessionId();
            ReturnCodeResponse setLocation = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            locationTO=myApp.getLocation();
            try {
                setLocation = onlineService.setLocationDetails(sessionId, locationTO);
                returnCode = setLocation.getReturnCode();
                message=setLocation.getMessage();
                return setLocation;
            } catch (Exception e) {

            }
            return null;
        }
        protected void onPostExecute(ReturnCodeResponse returnCodeResponse){
                Toast.makeText(context, returnCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();


        }

    }
    class GetCategories extends AsyncTask<String, String, CategoryListResponse> {

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
        protected void onPostExecute (CategoryListResponse response) {
                Integer returnCode = response.getReturnCode();

                categoryList = response.getCategories();

        }
    }
}
