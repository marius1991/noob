package de.fh_muenster.noobApp;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.LocationTO;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Diese Activity zeigt die Locations einer zuvor ausgewählten Kategorie in eine zuvor ausgewählten Stadt
 */
public class LocationListActivity extends ActionBarActivity {

    private String selectedFromList = null;
    private static final String TAG = LocationListActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        //Titel der Activity ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getCity() + ": " + myApp.getCategory());
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Activities asynchron abrufen
        new GetLocationsFromServer().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_list, menu);
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
     * Diese Methode wird beim Drücken auf den "Filter" Button aufgerufen.
     * Sie startet eine neue Activity in der die Filter gesetzt werden können
     * @param view
     */
    public void clickFuncFilter(View view){
        Intent i = new Intent(LocationListActivity.this, LocationSortActivity.class);
        startActivity(i);
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

    /**
     * @author marius
     * In diesem AsycTask werden die Locations einer Stadt und Kategorie vom Server abgerufen
     */
    class GetLocationsFromServer extends AsyncTask<String, String, LocationListResponse> {
        private ProgressDialog Dialog = new ProgressDialog(LocationListActivity.this);

        /**
         * Während des Abrufs der Liste wird ein Dialog angezeigt.
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Locations abrufen...");
            Dialog.show();
        }

        /**
         * Startet den Thread zum Abrufen der Locationliste vom Server
         * @param params
         * @return
         */
        @Override
        protected LocationListResponse doInBackground(String... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            LocationListResponse response = onlineService.listLocationsWithCategory(myApp.getCategory(), myApp.getCity());
            return response;
        }

        /**
         * Nimmt die Locationliste entgegen und füllt damit die Liste
         * @param response
         */
        @Override
        protected void onPostExecute (LocationListResponse response) {
            Dialog.dismiss();
            final List<LocationTO> valueListLocation;
            if(response.getReturnCode() == 10) {
                Log.d(TAG, "keine Verbindung zum Server");
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
            else {
                valueListLocation = response.getLocations();
                final List<String> valueList = new ArrayList<>();
                for (int i = 0; i < valueListLocation.size(); i++) {
                    valueList.add(valueListLocation.get(i).getName() + " - " + valueListLocation.get(i).getAverageRating() + "/5.0 Sterne");
                }
                //Den eingestellten Filter anwenden
                NoobApplication myApp = (NoobApplication) getApplication();
                if (myApp.getSortBy() != null) {
                    if (myApp.getSortBy().equals(getString(R.string.activity_location_sort_nachName1))) {
                        Collections.sort(valueList);
                    }
                    if (myApp.getSortBy().equals(getString(R.string.activity_location_sort_nachName2))) {
                        Collections.sort(valueList, Collections.reverseOrder());
                    }
                    List<String> valueListSubstring = new ArrayList<>();
                    for(int i=0; i<valueList.size(); i++) {
                        valueListSubstring.add(valueList.get(i).substring(selectedFromList.length() - 17, selectedFromList.length()));
                    }
                    if (myApp.getSortBy().equals(getString(R.string.activity_location_sort_nachRating1))) {
                        Collections.sort(valueListSubstring);
                    }
                    if (myApp.getSortBy().equals(getString(R.string.activity_location_sort_nachRating2))) {
                        Collections.sort(valueListSubstring, Collections.reverseOrder());
                    }
                }


                //ListView Objekt mit Daten füllen
                ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, valueList);
                final ListView lv = (ListView) findViewById(R.id.listView);
                lv.setAdapter(adapter);


                //Beim Selektieren eines Eintrags der Liste wird die LocationShowActivity der ausgewählten Location aufgerufen
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                        selectedFromList = (String) (lv.getItemAtPosition(myItemInt));
                        NoobApplication myApp = (NoobApplication) getApplication();
                        LocationTO selected = null;
                        for (int i = 0; i < valueListLocation.size(); i++) {
                            if (valueListLocation.get(i).getName().equals(selectedFromList.substring(0, selectedFromList.length() - 17))) {
                                selected = valueListLocation.get(i);
                                Log.d(TAG, String.valueOf(selectedFromList.substring(0, selectedFromList.length())));
                                Log.d(TAG, String.valueOf(selectedFromList));
                            }
                        }
                        myApp.setLocation(selected);
                        Intent i = new Intent(LocationListActivity.this, LocationShowActivity.class);
                        startActivity(i);
                    }

                });
            }
        }
    }

}
