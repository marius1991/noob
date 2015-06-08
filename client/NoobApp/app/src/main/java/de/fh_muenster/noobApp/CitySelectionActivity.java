package de.fh_muenster.noobApp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.CityListResponse;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Activity zum Auswählen der Stadt
 */
public class CitySelectionActivity extends Activity {

    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        //Cities asynchron abrufen
        new getCitiesFromServer().execute();
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

    //Bei Klick auf Login Button wird die nächste Activity aufgerufen und die ausgewählte Stadt zentral gespeichert
    public void clickFunc(View view){
        NoobApplication myApp = (NoobApplication) getApplication();
        myApp.setCity(selected);
        Intent i = new Intent(CitySelectionActivity.this, CategorySelectionActivity.class);
        startActivity(i);
    }

    class getCitiesFromServer extends AsyncTask<String, String, CityListResponse> {

        @Override
        protected CityListResponse doInBackground(String... params) {
            NoobOnlineServiceMock onlineService = new NoobOnlineServiceMock();
            CityListResponse response = onlineService.listCities();
            return response;
        }

        @Override
        protected void onPostExecute (CityListResponse response) {
            Integer returnCode = response.getReturnCode();
            List<String> valueList;
            //Toast.makeText(CitySelectionActivity.this, returnCode.toString(), Toast.LENGTH_LONG).show();
            valueList = response.getCities();
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
}
