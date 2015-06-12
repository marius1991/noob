package de.fh_muenster.noobApp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.noob.CityListResponse;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * In dieser Activity wird die Stadt ausgewählt
 */
public class CitySelectionActivity extends ActionBarActivity {

    private String selected;
    private static final String TAG = CitySelectionActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
    }

    protected void onResume(){
        super.onResume();
        //Cities asynchron abrufen
        GetCitiesFromServer getCitiesFromServer = new GetCitiesFromServer();
        getCitiesFromServer.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_selection, menu);
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
     * Diese Methode wird ausgeführt wenn aus den Button "Übernehmen" geklickt wird.
     * Sie speichert die ausgewählte Stadt und öffnet die nächste Activity
     * @param view
     */
    public void clickFuncCitySelection(View view){
        Log.d(TAG, selected + " wurde ausgewählt und die nächste Activity wird gestartet");
        NoobApplication myApp = (NoobApplication) getApplication();
        myApp.setCity(selected);
        Intent i = new Intent(CitySelectionActivity.this, CategorySelectionActivity.class);
        startActivity(i);
    }

    public void clickFuncNewLocation(MenuItem item) {
        Log.d(TAG, "Menüeintrag 'Neue Location' ausgewählt");
        Intent i = new Intent(CitySelectionActivity.this, NewLocationActivity.class);
        startActivity(i);
    }

    /**
     * @author marius
     * In diesem AsyncTask wird die Liste der Städte vom Server abgerufen
     */
    class GetCitiesFromServer extends AsyncTask<String, String, CityListResponse> {
        private ProgressDialog Dialog = new ProgressDialog(CitySelectionActivity.this);

        /**
         * Während des Abrufs der Liste wird ein Dialog angezeigt.
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Städte abrufen...");
            Dialog.show();
        }

        /**
         * Startet einen neuen Thread, der die Städteliste abholen soll
         * @param params
         * @return
         */
        @Override
        protected CityListResponse doInBackground(String... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            CityListResponse response = onlineService.listCities();
            return response;
        }

        /**
         * Nimmt die Städtliste entgegen und füllt das Spinner Objekt
         * @param response
         */
        @Override
        protected void onPostExecute (CityListResponse response) {
            Dialog.dismiss();
            if (response != null) {
                List<String> valueList = new ArrayList<>();
                if (response.getCities() != null) {
                    valueList = response.getCities();
                } else {
                    Log.d(TAG, "keine Stadt vorhanden");
                    Toast.makeText(getApplicationContext(), "keine Stadt vorhanden", Toast.LENGTH_SHORT).show();
                }
                ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, valueList);
                Spinner sp = (Spinner) findViewById(R.id.spinner);
                sp.setAdapter(adapter);
                sp.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        selected = parentView.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // Nichts machen
                    }

                });
            }
            else {
                Log.d(TAG, "keine Verbindung zum Server");
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
