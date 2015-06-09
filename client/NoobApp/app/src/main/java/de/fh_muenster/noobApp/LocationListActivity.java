package de.fh_muenster.noobApp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.LocationTO;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Die Activity zeigt die Locations der ausgewählten Kategorie einer Stadt
 */
public class LocationListActivity extends ActionBarActivity {

    private String selectedFromList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        //Titel der Activity ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getCity() + ": " + myApp.getCategory());



        //Activities asynchron abrufen
        new getLocationsFromServer().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_list, menu);
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

    //Bei Klick auf Filter Button wird die Filter Activity aufgerufen
    public void clickFunc(View view){
        Intent i = new Intent(LocationListActivity.this, LocationSortActivity.class);
        startActivity(i);
    }

    class getLocationsFromServer extends AsyncTask<String, String, LocationListResponse> {

        @Override
        protected LocationListResponse doInBackground(String... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineServiceMock onlineService = new NoobOnlineServiceMock();
            LocationListResponse response = onlineService.listLocationsWithCategory(myApp.getCategory(), myApp.getCity());
            return response;
        }

        @Override
        protected void onPostExecute (LocationListResponse response) {
            Integer returnCode = response.getReturnCode();
            final List<LocationTO> valueListLocation;
            valueListLocation = response.getLocations();
            final List<String> valueList = new ArrayList<>();
            //Toast.makeText(LocationListActivity.this, returnCode.toString(), Toast.LENGTH_LONG).show();
            for(int i=0; i<valueListLocation.size(); i++) {
                valueList.add(valueListLocation.get(i).getName());
            }
            NoobApplication myApp = (NoobApplication) getApplication();
            if (myApp.getSortBy() != null) {
                if (myApp.getSortBy().equals(getString(R.string.activity_location_sort_nachName1))) {
                    Collections.sort(valueList);
                }
                if (myApp.getSortBy().equals(getString(R.string.activity_location_sort_nachName2))) {
                    Collections.sort(valueList, Collections.reverseOrder());
                }
            }
            //ListView Objekt mit Testdaten füllen
            ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, valueList);
            final ListView lv = (ListView)findViewById(R.id.listView);
            lv.setAdapter(adapter);

            //Beim Selektieren eines Eintrags der Liste wird die LocationShowActivity der ausgewählten Location aufgerufen
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                    selectedFromList = (String) (lv.getItemAtPosition(myItemInt));
                    //Toast.makeText(getApplicationContext(), selectedFromList + " ausgewählt", Toast.LENGTH_SHORT).show();
                    NoobApplication myApp = (NoobApplication) getApplication();
                    LocationTO selected = null;
                    for(int i=0; i<valueListLocation.size(); i++) {
                        if(valueListLocation.get(i).getName().equals(selectedFromList)) {
                            selected = valueListLocation.get(i);
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
