package de.fh_muenster.noobApp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.exceptions.BadConnectionException;
import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.LocationTO;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * In dieser Activity werden die Locationkategorien der ausgewählten Stadt angezeigt.
 * Außerdem kann nach Locations in der Stadt gesucht werden
 */
public class CategorySelectionActivity extends ActionBarActivity {

    private static final String TAG = CategorySelectionActivity.class.getName();
    private String selected;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);
        editText = (EditText)findViewById(R.id.editText3);
        NoobApplication myApp = (NoobApplication) getApplication();

        //Titel der Activity durch den Namen der ausgewählten Stadt ersetzen
        setTitle(myApp.getCity());

        //Kategorien der Stadt asynchron abrufen
        new getCategoriesFromServer().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_selection, menu);
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
     * Die Funktion wird aufgerufen, wenn der Button "Auswählen" gedrückt wird.
     * Die ausgewählte Kategorie wird gespeichert und die nächste Activitx aufgerufen
     * @param view
     */
    public void clickFuncSelectCategory(View view) {
        NoobApplication myApp = (NoobApplication) getApplication();
        myApp.setCategory(selected);
        Intent i = new Intent(CategorySelectionActivity.this, LocationListActivity.class);
        startActivity(i);
    }

    /**
     * Die Funktion wird aufgerufen, wenn der Button "Suchen" gedrückt wird.
     * Die Suche wird asynchron an den Server geschickt
     * @param view
     */
    public void clickFuncSearchLocation(View view) {
        NoobApplication myApp = (NoobApplication) getApplication();
        myApp.setSearch(editText.getText().toString());
        //Die Suche erfolgt asynchron
        new searchLocationOnServer().execute(editText.getText().toString());
        Intent i = new Intent(CategorySelectionActivity.this, LocationSearchActivity.class);
        startActivity(i);
    }

    /**
     * @author marius
     * In diesem AsycTask werden die Kategorien einer Stadt vom Server abgerufen
     */
    class getCategoriesFromServer extends AsyncTask<String, String, CategoryListResponse> {

        /**
         * Startet einen neuen Thread, der die Kategorienliste abholen soll
         * @param params
         * @return
         */
        @Override
        protected CategoryListResponse doInBackground(String... params) {
            NoobOnlineServiceMock onlineService = new NoobOnlineServiceMock();
            CategoryListResponse response = null;
            try {
                response = onlineService.listCategories();
            } catch (BadConnectionException e) {
                Toast.makeText(CategorySelectionActivity.this, new Integer(response.getReturnCode()).toString(), Toast.LENGTH_LONG ).show();
            }
            return response;
        }

        /**
         * Nimmt die Kategorienliste entgegen und befüllt damit das Spinner Objekt
         * @param response
         */
        @Override
        protected void onPostExecute (CategoryListResponse response) {
            Integer returnCode = response.getReturnCode();
            List<String> valueList;
            //Toast.makeText(CategorySelectionActivity.this, returnCode.toString(), Toast.LENGTH_LONG ).show();
            valueList = response.getCategories();
            ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, valueList);
            Spinner sp = (Spinner)findViewById(R.id.spinner);
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
    }

    /**
     * @author marius
     * In diesem Asynchronen Task wird nach einer Location gesucht
     */
    class searchLocationOnServer extends AsyncTask<String, String, LocationListResponse> {

        /**
         * Startet einen neuen Thread für die Abfrage der Locationliste vom Server
         * @param params
         * @return
         */
        @Override
        protected LocationListResponse doInBackground(String... params) {
            NoobOnlineServiceMock onlineService = new NoobOnlineServiceMock();
            LocationListResponse response;
            NoobApplication myApp = (NoobApplication) getApplication();
            response = onlineService.listLocationsWithName(params[0], myApp.getCity());
            return response;
        }

        /**
         * Nimmt die Locationliste entgegen und speichert sie
         * @param response
         */
        @Override
        protected void onPostExecute (LocationListResponse response){
            NoobApplication myApp = (NoobApplication) getApplication();
            List<LocationTO> locationNames = new ArrayList<>();
            for(int i=0; i<response.getLocations().size(); i++) {
                locationNames.add(response.getLocations().get(i));
            }
            myApp.setLocationSearchResults(locationNames);
        }
    }

}
