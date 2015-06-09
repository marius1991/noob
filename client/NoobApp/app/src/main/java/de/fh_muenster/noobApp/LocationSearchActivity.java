package de.fh_muenster.noobApp;

import android.content.Intent;
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

import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noobApp.R;

public class LocationSearchActivity extends ActionBarActivity {

    private LocationTO selectedFromList = null;
    private static final String TAG = LocationSearchActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle("Suche nach '" + myApp.getSearch() + "' in " + myApp.getCity());

        //ListView Objekt mit Testdaten füllen
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, myApp.getLocationSearchResults());
        final ListView lv = (ListView)findViewById(R.id.listView2);
        lv.setAdapter(adapter);

        //Beim Selektieren eines Eintrags der Liste wird die LocationShowActivity der ausgewählten Location aufgerufen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                selectedFromList = (LocationTO) (lv.getItemAtPosition(myItemInt));
                //Toast.makeText(getApplicationContext(), selectedFromList + " ausgewählt", Toast.LENGTH_SHORT).show();
                NoobApplication myApp = (NoobApplication) getApplication();
                myApp.setLocation(selectedFromList);
                Log.d(TAG, myApp.getLocation().getName());
                Log.d(TAG, myApp.getLocation().getDescription());
                Intent i = new Intent(LocationSearchActivity.this, LocationShowActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_search, menu);
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
}
